---
type: concept
domain: 深度学习
---

# GAN

## 定义

GAN（Generative Adversarial Networks，生成对抗网络）由 Ian Goodfellow 等人在 2014 年提出，是一种生成模型。

## 核心机制
- **生成器（Generator）**：尝试生成逼真的数据
- **判别器（Discriminator）**：尝试区分真实数据和生成数据
- **对抗训练**：两者在极小极大博弈中相互促进

## 演进
- **DCGAN** (2015)：深度卷积 GAN，稳定训练
- **StyleGAN** (2019)：引入风格解耦和 W 空间，生成照片级图像
- **StyleGAN2/3**：进一步改进细节和稳定性

## 挑战
- 模式崩溃（Mode Collapse）
- 训练不稳定
- 评估困难

## 相关链接
- [[concepts/Diffusion Model|Diffusion Model]]
- [[sources/CNN模型进化之路摘要|CNN模型进化之路]]
- [[sources/图像领域深度学习模型进阶之路摘要|图像领域深度学习模型进阶之路]]
