---
type: source
source: raw/01_学习与思考/DL 学习/CNN 模型进化之路.md
date: 2026-04-12
domain: 计算机视觉
---

# CNN 模型进化之路摘要

> 原始文件：[[../../raw/01_学习与思考/DL 学习/CNN 模型进化之路.md|CNN 模型进化之路.md]]

## 核心内容

本文档以 mermaid 流程图形式总结了计算机视觉四大任务的模型架构演进。

### 1. 图像分类
**AlexNet** → **VGG** → **ResNet** → **ViT**
- 趋势：提升深度，转向 transformer

### 2. 目标检测
**R-CNN** → **Faster R-CNN** → **YOLO**
- 趋势：多阶段候选框 → 端到端单阶段检测 → 锚框优化

### 3. 图像生成
**GAN** → **StyleGAN** → **Diffusion** → **Stable Diffusion**
- 趋势：判别对抗 → 风格解耦 → 扩散建模 → 潜在空间扩散

### 4. 语义分割
**FCN** → **U-Net**
- 趋势：全连接层 → 全卷积网络 → 跳跃连接融合多尺度特征

## 技术演进表格

| 维度 | 演进 | 代表 |
|------|------|------|
| 数据增强 | 旋转裁剪 → 弹性变形 → 批归一化 → 生成模型合成 | AlexNet → U-Net → ResNet → StyleGAN |
| 激活/正则化 | ReLU、Dropout、AdaIN | AlexNet、StyleGAN |
| 损失函数 | 平方误差 → IoU → 加权损失 → Wasserstein → 噪声预测 MSE | YOLO、U-Net、GAN、DDPM |

## 相关链接
- [[concepts/卷积神经网络 CNN|卷积神经网络 CNN]]
- [[concepts/ResNet|ResNet]]
- [[concepts/YOLO|YOLO]]
- [[concepts/GAN|GAN]]
- [[concepts/Diffusion Model|Diffusion Model]]
- [[concepts/ViT|ViT]]
- [[synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
