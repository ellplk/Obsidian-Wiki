---
type: concept
domain: 深度学习
---

# ViT

## 定义

ViT（Vision Transformer）由 Google Research 在 2020 年提出，首次证明纯 Transformer 架构可以在图像分类任务上取得最先进的结果。

## 核心思想
- 将图像分割成固定大小的**图像块（Patches）**（如 16x16）
- 将每个图像块线性投影为向量，类似于 NLP 中的"词嵌入"
- 加入**位置编码**，输入标准 Transformer 编码器
- 使用 **[CLS] token** 进行分类

## 关键条件
- 需要**大规模数据集**（如 JFT-300M）预训练，或在 ImageNet 上配合强正则化
- 在小数据集上表现不如同等规模的 CNN

## 后续发展
- **Swin Transformer**：引入层级结构和移位窗口注意力，更适合检测分割等密集预测任务

## 相关链接
- [[concepts/Transformer|Transformer]]
- [[sources/CNN模型进化之路摘要|CNN模型进化之路]]
- [[sources/图像领域深度学习模型进阶之路摘要|图像领域深度学习模型进阶之路]]
- [[synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
