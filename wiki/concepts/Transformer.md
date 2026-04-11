---
type: concept
domain: 深度学习
---

# Transformer

## 定义

Transformer 是一种基于**自注意力机制（Self-Attention）**的深度学习架构，由 Vaswani 等人在 2017 年的论文《Attention Is All You Need》中提出。

## 核心创新
- **Self-Attention**：允许模型在处理序列时同时关注所有位置，捕获长距离依赖
- **Multi-Head Attention**：并行计算多组注意力，学习不同子空间表示
- **Position Encoding**：为序列中的每个位置添加位置信息
- **Feed-Forward Network**：逐位置前馈网络
- **Layer Normalization + Residual Connection**：稳定深层网络训练

## 影响
Transformer 已成为现代 NLP 和大语言模型的基础架构，并通过 [[concepts/ViT|ViT]] 等变体成功应用于计算机视觉领域。

## 相关链接
- [[sources/大模型底层原理学习路径摘要|大模型底层原理学习路径]]
- [[sources/CNN模型进化之路摘要|CNN模型进化之路]]
- [[entities/Andrej Karpathy|Andrej Karpathy]]
- [[entities/Hugging Face|Hugging Face]]
