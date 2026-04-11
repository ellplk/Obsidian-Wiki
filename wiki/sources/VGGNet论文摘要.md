# VGGNet 论文摘要

**原始文件**：`raw/assets/VERY DEEP CONVOLUTIONAL NETWORKS FOR LARGE-SCALE IMAGE RECOGNITION.pdf`
**论文标题**：Very Deep Convolutional Networks for Large-Scale Image Recognition
**作者**：Karen Simonyan, Andrew Zisserman（牛津大学 VGG 组）
**发表**：ICLR 2015

---

## 核心贡献

系统性研究网络深度对识别精度的影响，证明**用统一的 3×3 小卷积核堆叠深层网络**可以达到更好效果。ILSVRC-2014 分类亚军、定位冠军。

## 架构设计原则

- **统一用 3×3 卷积核**：两个 3×3 等效于一个 5×5（感受野相同但参数更少），三个 3×3 等效一个 7×7
- **深度递进**：从 VGG-11（8 conv）到 VGG-19（16 conv），分别有 5 个 max pooling
- **全连接层**：末尾 3 个 FC 层（4096→4096→1000）
- **参数量**：VGG-16 约 1.38 亿，VGG-19 约 1.44 亿

## 主要架构变体

| 模型 | 层数 | 特点 |
|------|------|------|
| VGG-11 | 11 | 基础版 |
| VGG-13 | 13 | 增加浅层卷积 |
| VGG-16 | 16 | 加入 1×1 卷积（线性变换） |
| VGG-19 | 19 | 最深，最常用 |

## 实验结果

- **ILSVRC-2014**：单模型 top-5 7.3%，7 模型集成 6.8%（亚军）
- 在定位任务中：top-5 错误率 25.3%（冠军）
- 预训练特征可有效迁移到其他任务（特征泛化性好）

## 局限性

参数量庞大（138M），推理慢；深度仍不及后来的 ResNet（无 residual 连接导致训练困难）。

## 关联页面

- [[../concepts/卷积神经网络 CNN|卷积神经网络 CNN]]
- [[AlexNet论文摘要|AlexNet]]
- [[ResNet论文摘要|ResNet]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
