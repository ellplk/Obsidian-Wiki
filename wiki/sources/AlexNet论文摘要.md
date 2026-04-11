# AlexNet 论文摘要

**原始文件**：`raw/assets/AlexNet.pdf`
**论文标题**：ImageNet Classification with Deep Convolutional Neural Networks
**作者**：Alex Krizhevsky, Ilya Sutskever, Geoffrey Hinton（多伦多大学）
**发表**：NIPS 2012

---

## 核心贡献

将深度卷积神经网络引入大规模图像分类，在 ILSVRC-2012 以 15.3% top-5 错误率夺冠，远超第二名（26.2%），开启深度学习在计算机视觉的时代。

## 架构

- **结构**：5 个卷积层 + 3 个全连接层，约 6000 万参数
- **输入**：224×224 RGB 图像
- **分布式训练**：两块 GTX 580 GPU 并行，模型拆分放置
- **首层卷积核**：11×11，步长 4；后续层逐步缩小

## 关键技术创新

| 技术 | 作用 |
|------|------|
| **ReLU 激活函数** | 替代 tanh/sigmoid，训练速度提升 6× |
| **Dropout**（p=0.5） | 前两个全连接层，防止过拟合 |
| **数据增强** | 随机裁剪、水平翻转、PCA 色彩抖动 |
| **局部响应归一化（LRN）** | 横向抑制，借鉴生物神经元机制 |
| **重叠最大池化** | 步长 < 核大小，减少过拟合 |

## 实验结果

- **ILSVRC-2012**：top-1 错误率 37.5%，top-5 错误率 15.3%（冠军）
- 单模型 top-5 18.2%；5 模型集成 16.4%
- 在 ILSVRC-2010（有测试标签）：top-5 错误率 16.4%，远优于当时最佳 26.2%

## 深远影响

AlexNet 证明深度 CNN + GPU + 大数据可以学习层级特征，直接推动后续 VGGNet、ResNet 等工作，成为深度学习革命的起点。

## 关联页面

- [[../entities/Geoffrey Hinton|Geoffrey Hinton]]
- [[../concepts/卷积神经网络 CNN|卷积神经网络 CNN]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
