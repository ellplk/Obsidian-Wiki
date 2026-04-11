# U-Net 论文摘要

**原始文件**：`raw/assets/U-Net- Convolutional Networks for Biomedical Image Segmentation.pdf`
**论文标题**：U-Net: Convolutional Networks for Biomedical Image Segmentation
**作者**：Olaf Ronneberger, Philipp Fischer, Thomas Brox（弗莱堡大学）
**发表**：MICCAI 2015

---

## 核心贡献

专为**生物医学图像分割**设计的端到端网络，在极少标注数据（数十张）下达到优秀性能。架构形似字母 U，由编码器（下采样）和解码器（上采样）+ **跳跃连接**构成。

## 架构设计

```
编码器（收缩路径）          解码器（扩展路径）
[3×3 Conv × 2 → MaxPool]  [上采样 → Concat(skip) → 3×3 Conv × 2]
×4 层                      ×4 层
```

- **编码器**：每阶段特征图尺寸减半，通道数翻倍（64→128→256→512→1024）
- **解码器**：每阶段上采样（转置卷积），拼接（concat）编码器对应层的特征图
- **跳跃连接**：直接 crop 并拼接，保留精细空间信息
- **输出**：1×1 卷积映射到 N 个类别

特点：**无填充（valid padding）**，输出图略小于输入；网络轻量（约 31M 参数）。

## 数据增强策略

针对标注数据极少的场景：
- 弹性形变（elastic deformation）：模拟生物组织变形
- 随机旋转、翻转
- 加权损失图（weight map）：强调相邻细胞间的分割边界

## 实验结果

| 数据集 | 指标 | 成绩 |
|--------|------|------|
| ISBI 2012 电镜神经元 | warping error | 0.0003（最优） |
| ISBI 2015 细胞追踪（PhC-U373） | IoU | 92%（第1） |
| ISBI 2015 细胞追踪（DIC-HeLa） | IoU | 77.5%（第1） |

在 NVidia Titan GPU 上单张图推理 < 1 秒。

## 深远影响

U-Net 架构被广泛用于医学影像分割，并被 Stable Diffusion / DDPM 等扩散模型借用作噪声预测网络的骨干（UNet denoiser）。

## 关联页面

- [[../concepts/语义分割|语义分割]]
- [[FCN论文摘要|FCN]]
- [[DDPM论文摘要|DDPM]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
