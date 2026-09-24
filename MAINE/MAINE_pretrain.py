import psutil
import torch.cuda

from torch.utils.data import Dataset, DataLoader

import keras
from keras.models import save_model
from keras.layers import Input
from keras.optimizers import AdamW, Adafactor
from custom_layers import *
import numpy as np
import hfdb_retrieve
from hfdb_retrieve import *
import keras_hub

import os
os.environ["KERAS_BACKEND"] = "torch"
os.environ["PYTORCH_CUDA_ALLOC_CONF"] = "expandable_segments:True"

#print(config.HF_DATASETS_CACHE)
#print(os.cpu_count())

keras.config.set_dtype_policy("mixed_bfloat16")



# I believe these were the params used for a 600M/1B+ param model
# Not used due to GPU limitations
"""
SEQUENCE_LENGTH = 2048
BATCH_SIZE = 512
EMBEDDING_DIM = 1536

vocab_size = 50257

NUM_HEADS = 24
NUM_BLOCKS = 20
"""

SEQUENCE_LENGTH = hfdb_retrieve.SEQUENCE_LENGTH
BATCH_SIZE = hfdb_retrieve.BATCH_SIZE
EMBEDDING_DIM = hfdb_retrieve.EMBEDDING_DIM

# Remember the tokenize thingy adds new tokens to tokenizer
vocab_size = hfdb_retrieve.vocab_size

# NOT FREQUENTLY EQUAL, JUST A CONVENIENCE
NUM_HEADS = hfdb_retrieve.NUM_HEADS
NUM_BLOCKS = hfdb_retrieve.NUM_BLOCKS



# Custom callback class for debugging purposes (used during training)
# Outputs allocated/reserved memory, currently for every 20 steps
class MemoryLogger(keras.callbacks.Callback):
    def on_train_batch_end(self, batch, logs=None):
        if batch % 20 == 0:
            print()
            print(f"step {batch}: allocated={torch.cuda.memory_allocated()/1e9:.2f}GB "
                    f"reserved={torch.cuda.memory_reserved()/1e9:.2f}GB")

# Tokenizes words (in basic terms, assigns syllables, common letter patterns, etc. with numbers)
# and (basically) segregates the inputs and their expected outputs
# Also accounts for padding (a.k.a. empty space so every batch of text is guaranteed to fill the
# entire size of SEQUENCE_LENGTH + 1, handled appropriately so the model doesn't calculate them
# as part of the loss)
def tokenize_fineweb_batch(examples, tokenizer):
    # Forces this method to use CPU RAM instead of GPU/VRAM
    with keras.device("cpu"):
        pad_id = tokenizer.pad_token_id if getattr(tokenizer, "pad_token_id", None) is not None else 50256
        token_ids = tokenizer(examples["text"])  # variable-length, no padding yet


        X, Y, W = [], [], []
        for seq in token_ids:
            seq = list(seq)[:SEQUENCE_LENGTH + 1]  # truncate only if too long
            x_seq = seq[:-1]
            y_seq = seq[1:]
            real_len = len(y_seq)


            pad_len = SEQUENCE_LENGTH - real_len
            x_seq = x_seq + [pad_id] * pad_len
            y_seq = y_seq + [pad_id] * pad_len
            w_seq = [1] * real_len + [0] * pad_len


            X.append(x_seq)
            Y.append(y_seq)
            W.append(w_seq)


        return {"input_ids": X, "labels": Y, "loss_mask": W}

# Streams the dataset instead of downloading it
# and shuffles it as well for the model
def stream_dataset():
    tokenizer = keras_hub.tokenizers.Tokenizer.from_preset("hf://gpt2")
    # NOTE: do NOT set tokenizer.sequence_length here — that silently pads everything


    dataset = load_dataset("HuggingFaceFW/fineweb-edu", name="sample-10BT", split="train", streaming=True)
    dataset = dataset.shuffle(seed=42, buffer_size=10000)


    return tokenizer, dataset

# Sample stream/token generator for debugging purposes
def fake_gen():
    while True:
        x = np.random.randint(0, vocab_size, size=(BATCH_SIZE, SEQUENCE_LENGTH)).astype(np.int32)
        y = np.random.randint(0, vocab_size, size=(BATCH_SIZE, SEQUENCE_LENGTH)).astype(np.int32)
        w = np.ones((BATCH_SIZE, SEQUENCE_LENGTH), dtype=np.float32)
        yield x, y, w



# Main program
def maine():
    pre_training, save_pre_train, pre_train_fit, summarize = True, True, True, True

    predicting = False

    #print_memory_stats("Before Model Creation")

    checkpoint_path = "MAINE_PTs/MAINEpoint.keras"

    model = None

    if pre_training:

        num_tokens = 0
        steps_per_epoch = 0
        resuming = os.path.exists(checkpoint_path)

        # Load model from a checkpoint
        if resuming:
            model = keras.models.load_model(checkpoint_path, custom_objects={
                "MaskedModel": MaskedModel,
                "TPEmbedding": TPEmbedding,
                "TransformerBlock": TransformerBlock,
                "LoraAdapter": LoraAdapter,
            })
            num_tokens = model.count_params() * 20
            steps_per_epoch = num_tokens // (BATCH_SIZE * SEQUENCE_LENGTH)
            print("Resumed from checkpoint")

        # Build model from scratch
        else:
            model = MaskedModel(name="MAINE")
            model.add(Input(shape=(SEQUENCE_LENGTH,)))
            model.add(TPEmbedding(vocab_size, SEQUENCE_LENGTH, EMBEDDING_DIM))


            for i in range(NUM_BLOCKS):
                model.add(TransformerBlock(num_heads=NUM_HEADS, embedding_dim=EMBEDDING_DIM))
            model.add(Dense(vocab_size, dtype="bfloat16"))


            num_tokens = model.count_params() * 20
            steps_per_epoch = num_tokens // (BATCH_SIZE * SEQUENCE_LENGTH)
            warmup_steps = max(int(steps_per_epoch * 0.01), 100)


            lr_schedule = keras.optimizers.schedules.CosineDecay(
                initial_learning_rate=1e-7,
                decay_steps=steps_per_epoch,
                alpha=0.1,
                warmup_target=3e-4,
                warmup_steps=warmup_steps
            )


            optimizer = keras.optimizers.AdamW(learning_rate=lr_schedule,
                                                beta_1=0.9,
                                                beta_2=0.999,
                                                weight_decay=0.01,
                                                clipnorm=1.0
                                                )


            #print("Keras backend:", keras.backend.backend())
            #print("Weight tensor type:", type(model.weights[0].value))


            model.compile(optimizer=optimizer)
            print("New start")

        #print_memory_stats("After Optimizer Initialization")

        # For checkpoint purposes only
        completed_steps = int(model.optimizer.iterations.numpy()) if resuming else 0
        remaining_steps = max(steps_per_epoch - completed_steps, 0)

        print(f"Total steps target: {steps_per_epoch} | Already done: {completed_steps} | Remaining: {remaining_steps}")

        checkpoint_callback = keras.callbacks.ModelCheckpoint(
            filepath="MAINE_PTs/MAINEpoint.keras",
            save_freq=1000, # Save every 1000 steps
            save_weights_only=False
        )

        if summarize: model.summary()

        # if statement below ensures the dataset being streamed doesn't start
        # from the very beginning (avoiding repetition of data)
        samples_consumed = completed_steps * BATCH_SIZE

        tokenizer, dataset = stream_dataset()
        if samples_consumed > 0:
            dataset = dataset.skip(samples_consumed)
            print(f"Skipped {samples_consumed} already-seen samples")
        tokenized_dataset = dataset.map(tokenize_fineweb_batch, batched=True, batch_size=1000, remove_columns=["text"],
                                        fn_kwargs={"tokenizer": tokenizer})

        if pre_train_fit:
            #torch.cuda.memory._record_memory_history(max_entries=100_000, stacks="all")

            try:
                if remaining_steps > 0:
                    model.fit(
                        keras_stream_generator(tokenized_dataset),
                        epochs=1,
                        steps_per_epoch=remaining_steps,
                        callbacks=[keras.callbacks.TerminateOnNaN(), checkpoint_callback] #MemoryLogger()
                    )
                #callbacks=[DebugCallback()]

            # FOR DEBUGGING PURPOSES ONLY (SPECIFICALLY FOR OUT OF MEMORY ERRORS)
            except torch.OutOfMemoryError as e:
                print()
                print("REAL allocated:", torch.cuda.memory_allocated() / 1e9, "GB")
                print("REAL reserved:", torch.cuda.memory_reserved() / 1e9, "GB")
                print("REAL max allocated:", torch.cuda.max_memory_allocated() / 1e9, "GB")
                print("Original message:", str(e))
                # after your OOM/dump point, or in a separate small script that loads the same snapshot:
                snapshot = torch.cuda.memory._snapshot()


                # Print the biggest live allocations with their call stacks
                segments = snapshot["segments"]
                blocks = [b for seg in segments for b in seg["blocks"] if b["state"] == "active_allocated"]
                blocks.sort(key=lambda b: b["size"], reverse=True)


                for b in blocks[:15]:
                    print(f"{b['size'] / 1e6:.1f} MB")
                    frames = b.get("frames", [])
                    for f in frames[:6]:
                        print("   ", f.get("filename", "?"), f.get("line", "?"), f.get("name", "?"))
                    print()
                raise

    # Version 1.0.0 has several issues, for example, the padding potentially
    # being token 0 (the "!" character) instead of 50256 (the REAL pad token)
    # and the padding being counted as a loss
    # Version 1.0.0 might still work as intended, but highly unlikely
    if save_pre_train: save_model(model, "MAINE_PTs/MPT_v_2_0_0.keras")



if __name__ == "__main__":
    maine()
