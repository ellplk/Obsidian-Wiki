# LDM 论文摘要（Stable Diffusion）

**原始文件**：`raw/assets/High-Resolution Image Synthesis with Latent Diffusion Models.pdf`
**论文标题**：High-Resolution Image Synthesis with Latent Diffusion Models
**作者**：Robin Rombach, Andreas Blattmann, Dominik Lorenz, Patrick Esser, Björn Ommer（慕尼黑大学 / Runway ML）
**发表**：CVPR 2022（arXiv 2021）

---

## 核心贡献

提出**潜在扩散模型（LDM）**：将扩散过程从像素空间迁移到预训练自编码器的**低维潜在空间**，显著降低计算量，同时通过**交叉注意力条件机制**支持文本、语义图、布局等多模态控制。即 **Stable Diffusion** 的理论基础。

## 两阶段框架

### 第一阶段：感知压缩（Perceptual Compression）

训练自编码器 E（编码器）和 D（解码器）：
- 将图像 x 压缩到潜在空间 z = E(x)，压缩因子 f ∈ {1,2,4,8,16,32}
- 两种正则化：KL-reg（接近正态分布）或 VQ-reg（向量量化）
- **最优**：f=4 或 f=8，在压缩率和重建质量间取得平衡

### 第二阶段：扩散生成（Latent Diffusion）

在压缩后的潜在空间 z 上训练 DDPM：

```
L_LDM = 𝔼_{ε(x),ε,t} [‖ε - ε_θ(zₜ, t)‖²]
```

- 噪声预测网络：时间条件化 U-Net
- 推理时 z_T ~ N(0,I) → 迭代去噪 → z₀ → D(z₀) → 高分辨率图像

## 条件机制：交叉注意力

将各类条件信号统一编码为序列，通过交叉注意力注入 U-Net 每层：

```
Attention(Q, K, V) = softmax(QKᵀ/√d)·V
Q = W_Q · φᵢ(zₜ),  K = W_K · τ_θ(y),  V = W_V · τ_θ(y)
```

- **文本**：BERT tokenizer → BERT encoder τ_θ → 映射到 U-Net
- **语义图**：下采样 + concat 到 U-Net 输入
- **其他**：超分辨率、修复（inpainting）等均可条件化

## 实验结果

### 无条件图像合成（CelebA-HQ 256²）

| 方法 | FID |
|------|-----|
| DDPM | 11.89 |
| LSGM | 7.22 |
| **LDM-4（KL）** | **5.11** |
| **LDM-8（KL）** | **5.40** |

### 文本到图像（MS-COCO）

| 方法 | FID | IS | 参数量 |
|------|-----|----|--------|
| DALL-E | 17.89 | 18.63 | 12B |
| **LDM-KL-8-G** | **12.63** | **30.29** | **1.45B** |

用不到 1/8 的参数达到更好的 FID。

### 其他任务

- **超分辨率**（4×，ImageNet）：超越 SR3
- **修复（Inpainting）**：超越 LaMa、CoModGAN
- **语义合成**（OpenImages）：布局到图像生成

## 计算效率

| 方法 | 训练 GPU 天 | 推理时间/样本 |
|------|------------|-------------|
| 像素 DDPM | ~150 V100天 | 慢 |
| **LDM-8** | **~10 A100天** | 快（单 A100） |

训练成本降低约 4-20×；推理时 `f=8` 时在潜在空间操作，分辨率仅 32²（对应 256² 图像）。

## 局限性

- f=4/1 的小压缩比时重建仍有细节损失（高频）
- 相比 GAN 推理仍较慢（多步去噪）

## 深远影响

LDM 是 Stable Diffusion 的直接前身，开源后极大降低了高质量图像生成的门槛，推动了 AI 绘画工具的爆发式增长。

## 关联页面

- [[../concepts/Diffusion Model|Diffusion Model]]
- [[DDPM论文摘要|DDPM]]
- [[ViT论文摘要|ViT]]（交叉注意力思想来源）
- [[../concepts/Transformer|Transformer]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
