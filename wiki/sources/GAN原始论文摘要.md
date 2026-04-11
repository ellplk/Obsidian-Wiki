# GAN 原始论文摘要

**原始文件**：`raw/assets/Generative Adversarial Nets.pdf`
**论文标题**：Generative Adversarial Nets
**作者**：Ian Goodfellow, Jean Pouget-Abadie, Mehdi Mirza, Bing Xu, David Warde-Farley, Sherjil Ozair, Aaron Courville, Yoshua Bengio（蒙特利尔大学）
**发表**：NIPS 2014

---

## 核心贡献

提出**生成对抗网络（GAN）**框架：通过生成器与判别器的对抗训练，无需马尔可夫链或近似推断，即可学习生成逼真样本。

## 核心框架

**博弈目标**：

```
min_G max_D V(D, G) = 𝔼_{x~p_data}[log D(x)] + 𝔼_{z~p_z}[log(1 - D(G(z)))]
```

- **判别器 D**：最大化区分真实样本与生成样本的能力（输出属于真实数据的概率）
- **生成器 G**：从噪声 z ~ p(z) 生成样本，最小化 D 的判别能力（最大化 log D(G(z))）
- 理论最优：D*(x) = p_data(x) / [p_data(x) + p_g(x)]，此时 p_g = p_data

## 训练算法

交替 k 步优化 D，1 步优化 G（实践中 k=1）：
- 实践中对 G 使用 `max log D(G(z))` 替代 `min log(1-D(G(z)))`，避免梯度消失
- 纯反向传播，无 MCMC、无近似推断

## 与其他生成模型的比较

| 模型 | 优点 | 缺点 |
|------|------|------|
| RBM | 理论完善 | 需马尔可夫链采样 |
| VAE | 有显式概率 | 图像模糊 |
| **GAN** | 图像清晰、灵活 | 训练不稳定、模式崩塌 |

## 实验结果（2014年水平）

在 MNIST、Toronto Face Database、CIFAR-10 上生成了有意义的样本，但 2014 年原始 GAN 图像质量尚粗糙（后续改进：DCGAN、ProGAN、StyleGAN 等）。

## 深远影响

GAN 开创了对抗训练范式，催生了 DCGAN、pix2pix、CycleGAN、StyleGAN、BigGAN 等大量工作，深刻影响图像生成领域。

## 关联页面

- [[../concepts/GAN|GAN]]
- [[../entities/Yann LeCun|Yann LeCun]]（评价 GAN 为"近十年机器学习最酷的发明"）
- [[StyleGAN论文摘要|StyleGAN]]
- [[../synthesis/深度学习图像模型演进史|深度学习图像模型演进史]]
