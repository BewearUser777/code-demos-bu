import numpy as np
import keras
import keras_hub
from datasets import load_dataset, interleave_datasets
import os


os.environ["KERAS_BACKEND"] = "torch"
os.environ["PYTORCH_CUDA_ALLOC_CONF"] = "expandable_segments:True"

# ACTUAL MAINE PARAMETERS
BATCH_SIZE = 4
SEQUENCE_LENGTH = 512
EMBEDDING_DIM = 1024
vocab_size = 50257
NUM_HEADS = 16
NUM_BLOCKS = 12

# Real row counts (verified via load_dataset_builder / manual inspection)
SUBSET_COUNTS = {
    "smol-magpie-ultra": 400000,
    "everyday-conversations": 2260,
    "systemchats-30k": 30000,
    "numina-cot-100k": 100000,
    "metamathqa-50k": 50000,
    "smol-rewrite": 50000,
    "smol-summarize": 100000,
    "smol-constraints": 34000,
}


# How many times more often to draw from everyday-conversations
# than its natural (row-count-proportional) frequency.
EVERYDAY_OVERSAMPLE_FACTOR = 5



# ******* START FINE TUNE METHODS, IGNORE FOR NOW *******
def compute_interleave_probabilities(counts: dict, oversample_key: str, factor: float) -> dict:
    weights = dict(counts)
    weights[oversample_key] *= factor
    total = sum(weights.values())
    return {k: v / total for k, v in weights.items()}

def stream_validation_dataset():
    # GPT-2 basically inherits from BPE
    tokenizer = keras_hub.tokenizers.Tokenizer.from_preset("hf://gpt2")


    # Connects to database WITHOUT downloading the data to your device (stream only)
    dataset = load_dataset("DKYoon/SlimPajama-6B", split="validation", streaming=True)
    dataset = dataset.shuffle(seed=42, buffer_size=10_000)




    return tokenizer, dataset

def stream_smoltalk_dataset():
    tokenizer = keras_hub.tokenizers.Tokenizer.from_preset("hf://gpt2")


    vocab = tokenizer.get_vocabulary()


    new_tokens = ["<|im_start|>", "<|im_end|>"]


    for token in new_tokens:
        if token not in vocab:
            vocab.append(token)


    global vocab_size
    vocab_size = len(vocab)


    tokenizer = keras_hub.tokenizers.BytePairTokenizer(
        vocabulary=vocab,
        merges=tokenizer.merges, # Keep existing merge rules
        unspecified_tokens=["<|im_start|>", "<|im_end|>"]
    )


    # 1. Load subsets (order here must match the order used for probabilities below)
    subset_names = [
        "smol-magpie-ultra",
        "everyday-conversations",
        "systemchats-30k",
        "numina-cot-100k",
        "metamathqa-50k",
        "smol-rewrite",
        "smol-summarize",
        "smol-constraints",
    ]


    datasets_list = [
        load_dataset("HuggingFaceTB/smoltalk", name, split="train", streaming=True)
        for name in subset_names
    ]


    # 2. Compute mixture probabilities from real row counts, with
    #    everyday-conversations deliberately oversampled
    probs_dict = compute_interleave_probabilities(
        SUBSET_COUNTS, "everyday-conversations", EVERYDAY_OVERSAMPLE_FACTOR
    )
    probabilities = [probs_dict[name] for name in subset_names]


    # 3. Interleave (row-level mixing, not concatenate-then-shuffle)
    combined_dataset = interleave_datasets(
        datasets_list,
        probabilities=probabilities,
        seed=42,
        stopping_strategy="all_exhausted",
    )


    # 4. Local shuffle on top, buffer size explicit
    final_dataset = combined_dataset.shuffle(seed=42, buffer_size=10_000)


    return tokenizer, final_dataset

def format_smoltalk_batch(examples):
    formatted_texts = []
    for conversation in examples["messages"]:
        formatted_text = ""
        for msg in conversation:
            role = msg["role"]
            content = msg["content"]
            formatted_text += f"<|im_start|>{role}\n{content}<|im_end|>\n"
        formatted_texts.append(formatted_text)
    return {"text": formatted_texts}


def tokenize_smoltalk_batch(examples, tokenizer):
    # Basically like tokenize_fineweb_batch but with the extra
    # format_smoltalk_row line
    examples = format_smoltalk_batch(examples)


    # GPT-2 pad token is <|endoftext|> (ID 50256)
    pad_id = tokenizer.pad_token_id if tokenizer.pad_token_id is not None else 50256


    X, y, mask = [], [], []


    # Tokenize the text batch directly
    # Note: Ensure you set padding/truncation properly
    tokenized = tokenizer(
        examples["text"],
        truncation=True,
        max_length=SEQUENCE_LENGTH + 1
    )


    for seq in tokenized["input_ids"]:
        # Ensure we have at least 2 tokens to make a valid (X, Y) pair
        if len(seq) < 2:
            continue


        # Shift targets
        X_seq = seq[:-1]
        y_seq = seq[1:]


        real_len = len(y_seq)
        pad_len = SEQUENCE_LENGTH - real_len


        # Pad X and Y with the TRUE pad token ID (50256), NOT 0
        X_seq = X_seq + [pad_id] * pad_len


        # For targets, use -100 if your framework supports ignore_index,
        # OR pad with pad_id and rely strictly on mask
        y_seq = y_seq + [pad_id] * pad_len


        # Mask: 1 for real content tokens, 0 for padding tokens
        m_seq = [1.0] * real_len + [0.0] * pad_len


        X.append(X_seq)
        y.append(y_seq)
        mask.append(m_seq)


    return {"input_ids": X, "labels": y, "loss_mask": mask}
# ******* END FINE TUNE METHODS *******



# This is the function that organizes the streamed data/tokens and feeds
# them into the fit() function for the model to train with them
def keras_stream_generator(iterable_ds, batch_size=BATCH_SIZE, max_tokens=5_080_000_000):
    total_tokens = 0
    batch_x, batch_y, batch_w = [], [], []
    for sample in iterable_ds:
        if total_tokens >= max_tokens:
            return  # stop once budget is hit

        x = np.array(sample["input_ids"], dtype=np.int32)
        total_tokens += int((x != 50256).sum())  # count only real (non-padding) tokens

        batch_x.append(x)
        batch_y.append(np.array(sample["labels"], dtype=np.int32))
        batch_w.append(np.array(sample["loss_mask"], dtype=np.float32))

        if len(batch_x) == batch_size:
            yield np.stack(batch_x), np.stack(batch_y), np.stack(batch_w)
            batch_x, batch_y, batch_w = [], [], []
