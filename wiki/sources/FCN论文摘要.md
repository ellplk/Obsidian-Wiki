# FCN 论文摘要

**原始文件**：`raw/assets/Fully Convolutional Networks for Semantic Segmentation.pdf`
**论文标题**：Fully Convolutional Networks for Semantic Segmentation
**作者**：Jonathan Long, Evan Shelhamer, Trevor Darrell（UC Berkeley）
**发表**：CVPR 2015

---

## 核心贡献

首次提出**全卷积网络（FCN）**框架用于语义分割：将分类网络（AlexNet/VGGNet/GoogLeNet）的全连接层替换为卷积层，输出密集预测图，并通过**跳跃架构**融合不同尺度的特征。

## 核心思想

### 1. 全连接层→卷积层

传统分类网络末尾的 FC 层限制输入分辨率；将其转为等效卷积后，网络可接受任意尺寸输入，输出对应尺寸的热力图（heatmap）。

### 2. 转置卷积上采样（反卷积）

将粗糙的特征图通过可学习的双线性插值上采样至原图大小：
- **FCN-32s**：直接 32× 上采样（粗糙）
- **FCN-16s**：融合 pool4 + conv7 预测，16× 上采样
- **FCN-8s**：进一步融合 pool3，8× 上采样（最细腻）

### 3. 跳跃连接（Skip Architecture）

```
最终预测 = 2× upsample(2× upsample(conv7) + pool4) + pool3
```

浅层特征（pool3/4）保留空间细节，深层特征（conv7）提供语义信息。

## 实验结果

| 模型 | PASCAL VOC 2012 mIU |
|------|---------------------|
| FCN-32s | 59.4% |
| FCN-16s | 62.4% |
| FCN-8s | **62.7%** |

超越当时最优方法（SDS: 52.6%），且速度更快（VGG-16 backbone 约 175ms/张）。

## 深远影响

FCN 为语义分割建立了标准框架，直接启发了 U-Net、SegNet、DeepLab 等工作。

## 关联页面

- [[../concepts/语义分割|语义分割]]
- [[../concepts/卷积神经网络 CNN|卷积神经网络 CNN]]
- [[U-Net论文摘要|U-Net]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
