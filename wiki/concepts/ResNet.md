---
type: concept
domain: 深度学习
---

# ResNet

## 定义

ResNet（Residual Network，残差网络）由何恺明等人在 2015 年提出，通过引入**残差连接（Skip Connection / Shortcut Connection）**解决了深层网络的梯度消失问题。

## 核心思想
- **残差块**：让网络学习输入与输出之间的**残差映射** $F(x) = H(x) - x$，而非直接学习 $H(x)$
- **恒等映射**：当最优解接近恒等映射时，残差块可以很容易地将其权重推近于零

## 影响
ResNet 使得训练数百层甚至上千层的网络成为可能，成为计算机视觉领域最具影响力的架构之一。

## 相关链接
- [[concepts/卷积神经网络 CNN|卷积神经网络 CNN]]
- [[sources/CNN模型进化之路摘要|CNN模型进化之路]]
- [[synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
