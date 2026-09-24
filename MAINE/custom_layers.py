import os
os.environ["KERAS_BACKEND"] = "torch"
os.environ["PYTORCH_CUDA_ALLOC_CONF"] = "expandable_segments:True"

import torch
import keras
from keras.layers import Layer, Embedding, MultiHeadAttention, LayerNormalization, Dense
from keras.losses import SparseCategoricalCrossentropy
import torch.utils.checkpoint as checkpoint



keras.config.set_dtype_policy("mixed_bfloat16")



# Custom layer for token (T) and position (P) embedding (Embedding)
class TPEmbedding(Layer):
    def __init__(self, vocab_size, max_len, embedding_dim, **kwargs):
        super().__init__(**kwargs)
        self.token_embedding = Embedding(input_dim=vocab_size, output_dim=embedding_dim)
        self.position_embedding = Embedding(input_dim=max_len, output_dim=embedding_dim)

    def get_config(self):
        config = super().get_config()
        config.update({
            "vocab_size": self.token_embedding.input_dim,
            "max_len": self.position_embedding.input_dim,
            "embedding_dim": self.token_embedding.output_dim,
        })
        return config

    def call(self, x):
        max_len = keras.ops.shape(x)[-1]

        # Basically just creates an array
        # starting with value 0 all the way
        # up to max_len - 1
        positions = keras.ops.arange(0, max_len, 1)

        tokens = self.token_embedding(x)
        positions = self.position_embedding(positions)

        # So tokens is [batch_size, max_len, embedding_dim]
        # but positions is [max_len, embedding_dim]
        # TensorFlow automatically adds positions to
        # every batch of tokens
        return tokens + positions



# Custom layer for the model's transformer architecture
class TransformerBlock(Layer):
    def __init__(self, num_heads, embedding_dim, dropout = 0.0, lora_ranks = None, **kwargs):
        super().__init__(**kwargs)
        self.lora_ranks = lora_ranks

        self.mha = MultiHeadAttention(num_heads = num_heads, key_dim = embedding_dim // num_heads)
        self.norm1 = LayerNormalization()

        self.first_layer = Dense(4 * embedding_dim, activation='gelu')
        self.second_layer = Dense(embedding_dim)
        self.norm2 = LayerNormalization()

        self.q_lora, self.v_lora = None, None
        if lora_ranks:
            self.q_lora = LoraAdapter(embedding_dim, lora_ranks)
            self.v_lora = LoraAdapter(embedding_dim, lora_ranks)

    def get_config(self):
        config = super().get_config()
        config.update({
            "num_heads": self.mha.num_heads,
            "embedding_dim": self.second_layer.units,
            "dropout": 0.0,
            "lora_ranks": self.lora_ranks
        })
        return config

    # OLD call() METHOD BELOW THIS DOES NOT USE GRADIENT CHECKPOINTING
    # AND IS MORE MEMORY EXPENSIVE
    def call(self, x):
        def block_fn(x):
            normed = self.norm1(x)
            attn_out = self.mha(query=normed, key=normed, value=normed, use_causal_mask=True)
            if self.q_lora is not None:
                attn_out = attn_out + self.q_lora(normed) + self.v_lora(normed)
            x = x + attn_out
            x = x + self.second_layer(self.first_layer(self.norm2(x)))
            return x

        if self.trainable and torch.is_grad_enabled():
            return checkpoint.checkpoint(block_fn, x, use_reentrant=False)
        else:
            return block_fn(x)

"""
    def call(self, x):
        normed = self.norm1(x)
        #print(f"[DEBUG] before mha: allocated={torch.cuda.memory_allocated() / 1e9:.2f}GB reserved={torch.cuda.memory_reserved() / 1e9:.2f}GB")
        attn_out = self.mha(query=normed, key=normed, value=normed, use_causal_mask=True)


        if self.q_lora is not None:
            attn_out = attn_out + self.q_lora(normed) + self.v_lora(normed)


        x = x + attn_out
        x = x + self.second_layer(self.first_layer(self.norm2(x)))
        return x
"""



# Regular SparseCategoricalCrossentropy calculates ALL loss (including padding)
# This specific class ensures that the padding is ignored and the model only
# focuses on the real tokens to calculate loss
class MaskedModel(keras.Sequential):
    def compute_loss(self, x=None, y=None, y_pred=None, sample_weight=None, training=True):
        base_loss = SparseCategoricalCrossentropy(from_logits=True, reduction="none")
        per_token_loss = base_loss(y, y_pred)
        if sample_weight is not None:
            sample_weight = keras.ops.cast(sample_weight, "float32")
            per_token_loss = per_token_loss * sample_weight
            return keras.ops.sum(per_token_loss) / keras.ops.maximum(keras.ops.sum(sample_weight), 1.0)
        return keras.ops.mean(per_token_loss)



# FOR FINE TUNE STAGE ONLY, CAN IGNORE FOR NOW
class LoraAdapter(Layer):
    """Standalone additive correction — approximates the q/v LoRA
    contribution without touching MultiHeadAttention's internals."""
    def __init__(self, embedding_dim, adapter_ranks, **kwargs):
        super().__init__(**kwargs)
        self.embedding_dim = embedding_dim
        self.adapter_ranks = adapter_ranks
        self.active_adapters = list(adapter_ranks.keys())
        self.adapters = {}


    def build(self, input_shape):
        in_dim = input_shape[-1]
        for name, rank in self.adapter_ranks.items():
            a = self.add_weight(shape=(in_dim, rank), initializer="random_normal",
                                trainable=True, name=f"lora_a_{name}")
            b = self.add_weight(shape=(rank, self.embedding_dim), initializer="zeros",
                                trainable=True, name=f"lora_b_{name}")
            self.adapters[name] = (a, b, rank)
        super().build(input_shape)


    def call(self, inputs):
        # ???
        out = keras.ops.zeros_like(inputs @ self.adapters[self.active_adapters[0]][0][:, :1] * 0) if False else 0
        total = None
        for name in self.active_adapters:
            a, b, rank = self.adapters[name]
            delta = keras.ops.matmul(keras.ops.matmul(inputs, a), b) * (1.0 / rank)
            total = delta if total is None else total + delta
        return total


    def get_config(self):
        config = super().get_config()
        config.update({"embedding_dim": self.embedding_dim, "adapter_ranks": self.adapter_ranks})
        return config


    def add_adapter(self, name, rank):
        # only works if the layer has already been built once
        in_dim = self.adapters[self.active_adapters[0]][0].shape[0] if self.adapters else self.embedding_dim
        a = self.add_weight(shape=(in_dim, rank), initializer="random_normal", trainable=True, name=f"lora_a_{name}")
        b = self.add_weight(shape=(rank, self.embedding_dim), initializer="zeros", trainable=True, name=f"lora_b_{name}")
        self.adapters[name] = (a, b, rank)
        self.adapter_ranks[name] = rank
        self.active_adapters.append(name)
