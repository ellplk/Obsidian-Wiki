# ViT 论文摘要

**原始文件**：`raw/assets/AN IMAGE IS WORTH 16X16 WORDS- TRANSFORMERS FOR IMAGE RECOGNITION AT SCALE.pdf`
**论文标题**：An Image is Worth 16x16 Words: Transformers for Image Recognition at Scale
**作者**：Alexey Dosovitskiy, Lucas Beyer, Alexander Kolesnikov et al.（Google Brain）
**发表**：ICLR 2021

---

## 核心贡献

证明纯 Transformer 架构在图像分类上可以媲美甚至超越最优 CNN，但需要**大规模数据预训练**（JFT-300M）。提出将图像分块（patch）视为序列 token 的处理方式。

## 核心思想：图像作为 Patch 序列

1. 将图像（224×224）切分为固定大小的 patch（16×16）
2. 展平每个 patch 并线性投影为 D 维向量 → 196 个 token
3. 在序列头部加入可学习的 `[CLS]` token（用于分类）
4. 加入**位置编码**（1D 可学习）
5. 输入标准 Transformer Encoder（L 层 MHSA + MLP）
6. 取 `[CLS]` token 输出接 MLP Head 分类

## 架构变体

| 模型 | 层数 L | 隐层维度 D | 注意力头 | 参数量 |
|------|--------|-----------|---------|--------|
| ViT-Base | 12 | 768 | 12 | 86M |
| ViT-Large | 24 | 1024 | 16 | 307M |
| ViT-Huge | 32 | 1280 | 16 | 632M |

## 关键发现：数据规模决定成败

| 预训练数据 | ImageNet 准确率（ViT-L） |
|-----------|-------------------------|
| ImageNet（1.2M） | 77.9%（差于 BiT） |
| ImageNet-21K（14M） | 83.6%（接近 BiT） |
| JFT-300M（303M） | **87.7%**（超越 BiT/EfficientNet） |

- CNN 的**归纳偏置**（平移不变性、局部性）在数据少时有优势
- Transformer 无归纳偏置，需更多数据补偿

## 注意力可视化

ViT 学到了有意义的注意力模式：浅层关注局部，深层关注全局语义区域（甚至第 1 层也能跨越远距离）。

## 深远影响

ViT 开启了视觉 Transformer 时代：DeiT（数据高效）、Swin Transformer（层级结构）、MAE（掩码自编码）等，并与 CLIP、DALL-E 等多模态模型深度结合。

## 关联页面

- [[../concepts/ViT|ViT]]
- [[../concepts/Transformer|Transformer]]
- [[../entities/Andrej Karpathy|Andrej Karpathy]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
