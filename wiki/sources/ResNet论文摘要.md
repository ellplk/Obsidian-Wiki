# ResNet 论文摘要

**原始文件**：`raw/assets/Deep Residual Learning for Image Recognition.pdf`
**论文标题**：Deep Residual Learning for Image Recognition
**作者**：Kaiming He, Xiangyu Zhang, Shaoqing Ren, Jian Sun（微软研究院）
**发表**：CVPR 2016（ILSVRC 2015 冠军）

---

## 核心贡献

提出**残差学习**框架，解决深层网络训练退化问题（degradation），使网络深度突破 100 层成为可能。ILSVRC-2015 五项任务全部夺冠。

## 核心思想：残差连接

```
y = F(x, {Wᵢ}) + x
```

- 网络学习残差映射 F(x)，而非目标映射 H(x)
- 捷径连接（shortcut connection）直接相加，不引入额外参数
- 当维度不匹配时，用 1×1 卷积对齐

**为什么有效**：恒等映射是退化的极端情况，残差学习让网络更容易优化——最坏情况网络退化为恒等映射，不会更差。

## 架构

- **构建块（Building Block）**：两层 3×3 卷积 + 捷径
- **Bottleneck**：1×1（降维）→ 3×3 → 1×1（升维），用于深层网络（ResNet-50+）
- **批归一化（Batch Normalization）**：每层后接 BN
- 无 Dropout

| 模型 | 层数 | top-5 错误率 |
|------|------|-------------|
| ResNet-34 | 34 | — |
| ResNet-50 | 50 | — |
| ResNet-101 | 101 | — |
| ResNet-152 | 152 | 4.49%（单模型） |
| 集成模型 | — | **3.57%** |

## 实验结果

- **ILSVRC-2015**：3.57% top-5 错误率，超越人类（约 5%）
- **COCO 2015**：目标检测 + 实例分割双料冠军
- ResNet-152 比 VGG-19 更深但参数更少（60M vs 138M）

## 深远影响

残差连接成为后续几乎所有深度网络的标配（DenseNet、SENet、EfficientNet），并影响 Transformer 的设计（LayerNorm + 残差）。

## 关联页面

- [[../concepts/ResNet|ResNet]]
- [[../concepts/卷积神经网络 CNN|卷积神经网络 CNN]]
- [[VGGNet论文摘要|VGGNet]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
