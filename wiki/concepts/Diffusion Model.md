---
type: concept
domain: 深度学习
---

# Diffusion Model

## 定义

扩散模型（Diffusion Model）是一类生成模型，通过模拟**前向扩散**和**反向去噪**过程来学习数据分布。

## 核心过程
1. **前向扩散**：逐步向图像添加高斯噪声，直到图像变为纯噪声
2. **反向去噪**：神经网络学习从噪声中逐步恢复原始图像

## 关键论文
- **DDPM** (2020)：Denoising Diffusion Probabilistic Models，奠定了现代扩散模型的基础
- **Stable Diffusion** (2022)：Latent Diffusion Models，在潜在空间进行扩散，大幅降低计算成本

## 优势
- 生成质量和多样性超越 GAN
- 训练更稳定
- 支持条件生成（文本到图像）

## 相关链接
- [[concepts/GAN|GAN]]
- [[sources/CNN模型进化之路摘要|CNN模型进化之路]]
- [[sources/图像领域深度学习模型进阶之路摘要|图像领域深度学习模型进阶之路]]
