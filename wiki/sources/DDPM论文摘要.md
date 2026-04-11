# DDPM 论文摘要

**原始文件**：`raw/assets/Denoising Diffusion Probabilistic Models.pdf`
**论文标题**：Denoising Diffusion Probabilistic Models
**作者**：Jonathan Ho, Ajay Jain, Pieter Abbeel（UC Berkeley）
**发表**：NeurIPS 2020

---

## 核心贡献

将扩散概率模型与去噪分数匹配连接起来，提出 DDPM 训练目标，实现在 CIFAR-10 上 FID 3.17（当时最优），图像质量首次达到 GAN 水平。

## 核心框架：前向 + 逆向马尔可夫链

### 前向过程（加噪，固定）

```
q(xₜ|xₜ₋₁) = N(xₜ; √(1-βₜ)xₜ₋₁, βₜI)
```

- 逐步加入高斯噪声，T=1000 步后 x_T ≈ N(0,I)
- 闭合形式：q(xₜ|x₀) = N(xₜ; √ᾱₜ x₀, (1-ᾱₜ)I)，其中 ᾱₜ = ∏αₛ

### 逆向过程（去噪，学习）

```
p_θ(xₜ₋₁|xₜ) = N(xₜ₋₁; μ_θ(xₜ,t), Σ_θ(xₜ,t))
```

网络学习预测噪声 ε_θ(xₜ, t)，等价于学习逆向均值：

**简化损失**：
```
L_simple = 𝔼_{t,x₀,ε} [‖ε - ε_θ(√ᾱₜ x₀ + √(1-ᾱₜ)ε, t)‖²]
```

## 网络架构

- **骨干**：U-Net（编码器-解码器 + 跳跃连接）
- **时间嵌入**：正弦位置编码 → MLP，注入每个残差块
- **注意力机制**：16×16 分辨率处加入自注意力层
- **归一化**：Group Normalization

## 实验结果

| 数据集 | FID | IS |
|--------|-----|----|
| CIFAR-10 (无条件) | **3.17** | 9.46 |
| CelebA-HQ 256² | 5.11 | — |
| LSUN Bedroom | 4.90 | — |
| LSUN Church | 7.89 | — |

- CIFAR-10 FID 3.17 是当时最优（超越所有 GAN）
- 支持插值：在隐空间球面线性插值生成中间图像

## 与 GAN 的关键区别

| 维度 | GAN | DDPM |
|------|-----|------|
| 训练稳定性 | 难（模式崩塌） | 稳定 |
| 多样性 | 低 | 高 |
| 推理速度 | 快（1 次前向） | 慢（T=1000 步） |
| 图像质量 | 高 | 可比 |

## 深远影响

DDPM 奠定现代扩散模型基础，LDM（Stable Diffusion）、DALL-E 2、Imagen 等均建立在此之上。U-Net 架构也因此被广泛用于生成模型。

## 关联页面

- [[../concepts/Diffusion Model|Diffusion Model]]
- [[LDM论文摘要|LDM（Stable Diffusion）]]
- [[U-Net论文摘要|U-Net]]
- [[GAN原始论文摘要|GAN]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
