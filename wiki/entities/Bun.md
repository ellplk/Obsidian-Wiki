# Bun

> 高性能 JavaScript/TypeScript 运行时，兼替 Node.js、npm、打包器

---

## 基本信息

- **类型**：JavaScript 运行时 + 工具链
- **语言**：Zig（底层）
- **特点**：启动速度极快，内置打包器、测试框架、包管理器

## 与 Node.js 的对比

| 特性 | Bun | Node.js |
|------|-----|---------|
| 启动速度 | 极快（< 10ms） | 较慢（50-200ms） |
| 内置打包 | 是（Bun bundler） | 否（需 webpack/esbuild） |
| TypeScript | 原生支持，无需 tsc | 需 ts-node 或编译 |
| 包管理 | 内置（bun install） | npm/yarn/pnpm |

## 在 Claude Code 中的应用

Claude Code 选择 Bun 而非 Node.js 的核心原因：**毫秒级 CLI 启动**。用户每次在终端执行 `claude` 命令都需要重新启动进程，启动时间直接影响体验。

Bun 的 **feature() 函数**还被用于编译期 DCE（Dead Code Elimination）：

```typescript
if (feature('pro')) {
  // 企业版功能，消费版构建时完全剔除
}
```

同一份 TypeScript 源码，通过 `process.env.USER_TYPE` + Bun bundler，构建出两个不同的产品（消费版 vs 企业版）。

## 关联

- [[entities/Claude Code|Claude Code]] — 主要使用方
- [[sources/Claude-Code-Source-Study摘要|Claude Code Source Study]] — 第 2、19 章分析启动优化与编译期特性
