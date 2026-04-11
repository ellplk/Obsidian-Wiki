> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [mp.weixin.qq.com](https://mp.weixin.qq.com/s/zqHvwZImxoGDLGdGGi87vw) 谦行@阿里技术 2025年04月04日 08:31

![](https://mmbiz.qpic.cn/mmbiz_jpg/OmCbZ5JK30Gl0nErBFYSUXXRrEKZOHPliae8eziboq48v54E5TK8K4icZnu0Hf3MxKx6QK8yjaGuz0CnWHq657Ricw/640?wx_fmt=jpeg)

  

  

这是2025年的第29篇文章

（ 本文阅读时间：15分钟 ）

  

  

在 2024 年底我还觉得 AI 取代程序员是遥不可及的事情，随着在 AI Code 领域个人学习和团队高密度的讨论、实践，个人的一些观点发生了 180 度掉头，AI 取代初级程序员的编程任务近在眼前，本文来分享一下让我观点发生变化的 AI 能力和对未来 AI Code 的理解。

'从长远看，注意我说的“长远”可能也就是 18~24 个月，而不是五六年，也许在初级层面的编码工作上会出现“替代”现象，也可能比这更早。'Dario 在访谈中直言不讳地表示。这一时间线比大多数行业专家预期的要短得多，尤其是考虑到他所谓的“长远”仅仅是一年半到两年的时间。—— 2025.02 Anthropic 首席执行官 Dario Amodei 

  

  

**01**

  

  

**Agent VS Workflow**

Manus 的爆火让很多人忽然发现，已经有多个产品称自己是 AI Agent，就像市场上很多食品都标榜“纯天然”一样，Agent 这个词被过度使用。就像人有人质疑 Manus 一样，有些所谓 Agent 只是预编排的 Workflow，因为在 Workflow 内可以调用大模型，看起来都很 AI，导致人们难以区分 Worklow 和 Agent。

  

两者关键区别在于自主决策能力，简单说 Workflow 就像是固定的生产线，每个步骤都是预先设计好的；而 Agent 则像是有自主思考能力的助手，通过感知-决策-执行的路径，可以自己决定怎么做、做多久。

  

归根结底，真正的 Agent 有两个关键特点：

1.能自己做决定：不需要人类告诉它每一步该做什么？

2.会一直工作到完成任务：运行次数不是预先固定的，而是根据需要自动调整﻿。

想象一下厨房里的两种场景：

*   Workflow：按照菜谱一步步做菜，第一步切菜，第二步放油，第三步炒菜，每一步都是预先定好的，按部就班地执行，过程中如果出现没油了，可能炒菜就得中断了。
    
*   Agent：你告诉一个会做饭的人“做一道可口的晚餐”，他会根据冰箱里有什么材料，自己决定做什么菜，如何烹饪，需要走多少步骤才能完成，如果没油了，Agent 会自己知道要去买油。
    

  

  

**02**

  

  

**最适合 Agent 的任务特征**

在选择使用 Agent 的场景时，来自 Anthropic 的 Barry 提出了一个非常实用的标准：

我认为代理最适合的场景是那些既复杂又有价值的任务，但失败后的风险较低或监控成本不高的任务。这是代理应用的理想交叉点。

  

简单来说最适合 Agent 的任务应该是：

*   足够复杂：简单任务用代理可能是“杀鸡用牛刀”；
    
*   有一定价值：值得投入资源去自动化；
    
*   容错性高：即使代理偶尔出错，也不会造成严重后果，在人监督的情况下可以进一步降低风险。
    

  

举个生活中的例子，你可能会让 Agent 帮你筛选邮件或整理文档，因为即使它偶尔分类错误，后果也不严重。但你可能不会让 Agent 直接操作你的银行账户进行大额转账，因为错误成本太高。

  

以搜索为例，这是一个非常有价值的任务，进行深入的迭代搜索非常困难，但总是可以用一些精度换取召回率，然后获得比需要更多的文档或信息，然后过滤下来。这意味着 Agent 可以进行多轮、深入的信息检索，不断调整搜索策略，最终找到用户真正需要的信息，而不仅仅是关键词匹配的结果。

  

所以我们在 Agent 搜索方面看到了很多成功，比如 Perplexity 和 Alibaba.com 的 Accio，也许看到这里大家已经注意到了，除了搜索还有一个领域近乎完美契合这些特征 —— Coding！﻿

  

最近大家可能有些观察，有了 Agent 模式， Cursor、Windsurf 越来越像了：

*   Cursor 的 Yolo 模式能自主判断终端命令执行状态，在编译报错后自动修正并重新运行。
    
*   Windsurf 的 AI Flows 可分解复杂任务为多步骤工作流，实时监测文件变更并同步调整后续操作。
    

  

甚至字节 3 月份力推的 Trae 在人机交互体验上也如出一辙，重点从 MarsCode 时的 Tab 提示转向了 Agent 模式，IDE 的智能辅助已超越传统补全，演变为环境感知型协作者，环境感知+自主决策将成为下一代 IDE 标配。

  

  

**03**

  

  

**潮涌护城河**

程序员的 Coding 时间甚至占不到 50%，所有 AI 编程工具暂时还取代不了人类， 先来捋一下程序员是怎么写代码的

  

1.参与需求评审、设计评审，明确编程目标。

2.利用自身知识做技术方案设计。

3.使用企业、社区方案完成代码框架。

4.完成代码实现，对程序进行完善。

a.通过报错信息对程序进行修改

b.上网查询相关信息

c.向身边专家求助

5.对代码进行单元测试。

6.集成测试、冒烟、项目验收。

  

有了对 Agent 了解之后，发现貌似程序员的不可取代性主要集中在需求理解，技术设计和代码框架对企业内部知识有依赖，AI 无法全面完成，其余的工作 Agent 在程序员的监督下可以代劳。之前觉得 AI 编程无法取代自己的侥幸感，正是来自于 AI 擅长通用问题的解决，而两个障碍让 AI 没法帮我写代码

  

1.针对单文件的 Tab 提醒，没法结合项目最佳实践，给我最优建议。

2.因为大量使用企业内部的代码规范、基础组件、中间件、框架等，AI 不懂这些。

  

随着最近三个月 AI 圈的不断刷新，发现事情并没有那么简单。

  

Codebase Indexing 理解项目全文

Cursor 在诞生时就通过 Codebase Indexing 解决了第一个问题，把护城河填埋了一半。

  

﻿![](https://mmbiz.qpic.cn/mmbiz_jpg/Z6bicxIx5naLYpcX77tOy3epFKUtjFwCHZLf4ia4DqyPFaapaamY34Tz0iaBx26WRUVvViaaWVadiavz8mZTZNglq1w/640?wx_fmt=other&from=appmsg)﻿

当用 Cursor 打开一个项目时，Cursor 会自动对代码库进行扫描和索引。它会分析代码中的各种元素，如函数、类、变量等，并建立它们之间的关系。通过这种方式，AI 可以快速定位到相关的代码片段，了解代码的上下文和用途

*   上下文感知的代码片段关联，如识别 `getUserInfo()` 在不同模块中的重载形态；
    
*   跨文件语义追溯，如通过接口定义自动定位所有实现类；
    
*   增量更新机制，新增文件在保存后 30 秒内完成索引同步；
    

  

这样，当开发者提出一个代码生成需求时，AI 可以根据索引信息，参考项目中已有的代码模式和风格，生成更符合项目实际情况的代码

  

RAG + Function Call 让模型更懂企业

模型不懂企业内部或专业领域知识以往多通过模型微调来解决，微调虽能针对性注入领域知识，但始终面临幻觉、降智的顽疾，同时面临“知识固化”困境——调整后的参数无法动态适应业务规则变化，且高频次全量微调带来的算力/预算消耗也是一个巨大的成本。﻿

  

如果仅为了专业知识的拓展，相对而言 RAG 是个不错的选择，可以解决知识的实时性和成本问题，但会让 AI 生成代码进入预定流程编排的 Workflow 模式，而且需要开发使用强激活词调用 RAG 后拼装 Prompt，比如 调用组件库，使用 Button 组件改写代码。虽然业界有 RAG “无感知智能化”演进的实践，例如微软的 GraphRAG 通过知识图谱实现多跳推理检索，但考虑到技术难度和成本问题，应用并不广泛。

  

Function Call 算是个救星，通过将自然语言指令转化为结构化预定义 API 请求，执行具体操作或获取实时数据。根据 OpenAI 最新指南，Function Call 的“数据获取”模式本质上是一种 RAG 实现——在生成回答前，通过 Function Call 主动触发知识库检索动态补充模型知识，大模型实现从被动应答向主动求知的认知跃迁。

  

﻿![](https://mmbiz.qpic.cn/mmbiz_jpg/Z6bicxIx5naLYpcX77tOy3epFKUtjFwCHGiaQUg8K8jic5NU3XJZ4vseSib2Zx7jNqNamia7r7JZrYYe7lj2BT7BZwA/640?wx_fmt=other&from=appmsg)﻿

  

Function calling example with get_weather function

```
`from openai import OpenAI``client = OpenAI()``tools = [{` `"type": "function",` `"name": "get_weather",` `"description": "Get current temperature for a given location.",` `"parameters": {` `"type": "object",` `"properties": {` `"location": {` `"type": "string",` `"description": "City and country e.g. Bogotá, Colombia"` `}` `},` `"required": [` `"location"` `],` `"additionalProperties": False` `}``}]``response = client.responses.create(` `model="gpt-4o",` `input=[{` `"role": "user",`  `"content": "What is the weather like in Paris today?"` `}],` `tools=tools``)``print(response.output)`
```

  

但 Function Call 每次调用需将 API 返回数据全量注入提示词，导致上下文长度线性膨胀。 同时 Function Call 的生态极其碎片化，不同模型厂商定义各自的函数调用格式（如 OpenAI 的 JSON 结构），导致跨平台开发需重复适配，同时因为开发者需手动实现三阶段流程（函数定义→模型适配→结果解析），每新增一个 API 需投入大概 20+ 小时开发。﻿

  

然而这个复杂度也在 MCP 的冲击下也开始松动起来。

  

  

**04**

  

  

**MCP：大模型与企业知识的 USB-C**

**什么是 MCP**

The Model Context Protocol (MCP)lets you build servers that expose data and functionality to LLM applications in a secure, standardized way. Think of it like a web API, but specifically designed for LLM interactions. 

  

﻿![](https://mmbiz.qpic.cn/mmbiz_jpg/Z6bicxIx5naLYpcX77tOy3epFKUtjFwCH1y6KVkckibiboEL9yIOVSyXAqfEMX6cRq6nfWu1xKjBcoxgMDDJ6OmIQ/640?wx_fmt=other&from=appmsg)﻿

  

简单来讲 MCP 是 Anthropic 提出的一个用于标准化应用程序如何向大语言模型提供上下文的开放协议，相当于模型与各种应用程序之间的 USB-C。﻿

  

MCP 使用 C/S 模式，它们之间用一种标准的消息格式（基于JSON-RPC）交流

*   MCP Client：嵌入在 AI 应用内（比如 Cursor），负责跟服务器“聊天”，根据用户自然语言，智能发起 MCP 服务调用，获取数据、资源或命令执行。
    
*   MCP Server 器：开发者提供的向模型暴露内部数据、资源、功能的服务。
    

﻿  

![](https://mmbiz.qpic.cn/mmbiz_jpg/Z6bicxIx5naLYpcX77tOy3epFKUtjFwCHOUGzTo7IeTZSoYNc9MibGDzToBfus3Ta9voTQnEL2oXSLNelebfIGmw/640?wx_fmt=other&from=appmsg)  

  

Cursor 支持 MCP 的关键角色

Cursor、MCP Server 与模型之间的数据流程是一个标准化的协作体系，通过 MCP协议实现三者的高效交互。

*   Cursor 本身作为一个 MCP Client，负责发起请求、展示结果，并作为用户与系统的交互入口。
    
*   开发者实现的 MCP Server 提供标准化接口，处理客户端请求，对接本地或远程资源（如GitHub API、本地文件系统），并返回结构化数据。
    
*   模型接收 Cursor 传递的请求和上下文，生成决策（如是否需要调用工具）及自然语言响应。
    

  

MCP 数据流转

#### 阶段一：初始化

1.Cursor 启动时，初始化 MCP Client 并与项目或 IDE 全局配置的 MCP Server 建立连接。

2.双方进行功能协商，确定 Server 提供的可用资源/工具列表，为后续调用做好准备。

####   

#### 阶段二：处理用户请求

1.请求传递与工具选择：用户在 Cursor 通过自然语言输入指令，Cursor 将请求发送至 MCP Server，获取当前可用的工具描述。Cursor 将工具列表与用户指令拼接，发送给模型。

2.模型决策与工具调用：模型解析请求，判断需调用的工具，并生成结构化参数，Cursor 通过 MCP Server 执行工具调用。

####   

#### 阶段三：数据交互与结果整合

1.资源访问与执行：MCP Server 响应请求，结果通过 JSON-RPC 2.0 协议返回给 Cursor。

2.模型生成最终响应：Cursor 将 MCP Server 返回结果发送给模型，模型整合上下文生成自然语言响应，最终结果通过 Cursor 界面展示给用户。

因为有了模型决策调用哪个工具来解决用户问题的步骤，因此如果模型判断需要调用 MCP Server 的话会多一次模型的调用。

  

﻿![](https://mmbiz.qpic.cn/mmbiz_jpg/Z6bicxIx5naLYpcX77tOy3epFKUtjFwCHxzYBzuwPonicvyhE15seXvnHIZNd9WWEpjYWCVP9bVHh60I0pOUA4FA/640?wx_fmt=other&from=appmsg)  

MCP 核心优势

MCP 带来了几个核心优势

*   协议标准化驱动生态统一： MCP通过统一的协议简化了AI与外部工具的连接，开发者无需为每个工具单独编写接口代码，实现一次开发多工具复用
    

这里有数千开源的 MCP Server 实现可以使用、借鉴，Cursor、Windsurf 等 IDE 均已支持：

https://glama.ai/mcp/tools﻿

https://smithery.ai/﻿

*   开发效率：MCP 官方提供了开发工具包 & 调试工具，相对于兼容各种 AI 模型的 Function Call，实现一个通用的 MCP Server 极其简单。  
    

  

demo MCP Server

```
`from mcp.server.fastmcp import FastMCP``# Create an MCP server``mcp = FastMCP('Demo')``# Add an addition tool``@mcp.tool()``def add(a: int, b: int) -> int:` `'''Add two numbers'''` `return a + b``# Add a dynamic greeting resource``@mcp.resource('greeting://{name}')``def get_greeting(name: str) -> str:` `'''Get a personalized greeting'''` `return f'Hello, {name}!'`
```

*   根治上下文爆炸：MCP 采用模块化上下文管理，将外部数据源抽象为独立模块，模型仅在需要时激活指定模块，同时通过增量索引，MCP 仅同步变更数据，相比 Function Call 的全量注入模式，Token 消耗显著降低。
    
*   动态发现与灵活性：MCP 支持动态发现可用工具，AI 可自动识别并调用新接入的数据源或功能，无需提前配置。
    

  

MCP 通过协议层革新重构了 AI 与外部系统的协作范式，其标准化、动态化、安全性的特征，正在解决 Function Call 面临的生态碎片化、上下文冗余、权限粗放等核心痛点。随着 Anthropic 携 Claude 之利 的生态推进，MCP 有望成为下一代智能系统的核心基础设施。

  

Fuction Call 与 MCP

乍一看两者非常相似，都在解决模型与外部工具交互问题，两者的数据流程有很大区别

*   Function Call：用户输入 → 模型识别意图 → 生成工具参数 → 执行外部函数 → 返回结果整合。
    
*   MCP：用户输入 → Client 向 MCP Server 请求可用工具列表 → 模型生成自然语言指令 → Client 解析指令并调用工具 → 返回结果 -> 模型根据结果更好的响应用户输入。
    

  

Function Call 依赖模型层面的支持，而 MCP 更像是模型的外挂，只要有 Client 负责提供可用服务列表并对模型发起询问，任何模型都可以使用 MCP，no magic, just chat。

  

Function Call 是模型能力的延伸，而 MCP 是工具交互的基础设施。两者的差异本质上是“垂直优化”与“横向通用”的技术路线之争，未来或将共存互补。

  

  

**05**

  

  

**六个月后的 AI Code 范式**

未来 AI 代码生成体系将形成 “规范驱动→知识沉淀→协议贯通→智能执行” 的闭环架构，确保代码生成的高质量、可维护性和智能化。

  

*   规范驱动体系，通过一系列技术规范和最佳实践来确保架构合理，生成企业级高可用代码。
    
*   业务知识库作为 AI 代码生成的核心认知模块，负责存储和管理多模态知识，支持动态更新和智能检索。
    
*   MCP Server 作为系统的感官系统，负责与各类智能 Agent 交互，获取并处理开发过程中的各种信息，确保系统的高效运行。
    
*   IDE 集成智能编码工作流和多智能体协作机制，通过人机协同界面提升开发效率和代码质量。
    

  

在这种模式下前端普遍尝试的 D2C 生成 UI 代码，可以进化到 UI + 数据绑定 + 交互逻辑 我全都要。

  

<table style="min-width: 100px;"><tbody><tr><td><p style="line-height: 1.6em;margin-bottom: 0px;margin-top: 0px;">﻿<img src="https://mmbiz.qpic.cn/mmbiz_jpg/Z6bicxIx5naLYpcX77tOy3epFKUtjFwCHl7miajpu9F9biaQ2MZPY5LxYbqAFIJ7Rj6ps9kUZ5jQ83a0C3d7gZFpw/640?wx_fmt=other&amp;from=appmsg">﻿</p></td><td><p style="line-height: 1.6em;margin-bottom: 0px;text-align: center;margin-top: 0px;">﻿<img src="https://mmbiz.qpic.cn/mmbiz_jpg/Z6bicxIx5naLYpcX77tOy3epFKUtjFwCH5OeshdPnNPcqHMK5755tPTYoXBK3OfErTCQcRwoE8iczBjhichbI9Kww/640?wx_fmt=other&amp;from=appmsg">﻿</p></td><td><p style="line-height: 1.6em;margin-bottom: 0px;margin-top: 0px;">﻿<img src="https://mmbiz.qpic.cn/mmbiz_jpg/Z6bicxIx5naLYpcX77tOy3epFKUtjFwCHpSibOuHicPyFhTEjL69AwqdTaJEYWghX5JIZF2jFfgPLkxKnRCrt1VYw/640?wx_fmt=other&amp;from=appmsg">﻿</p><p style="line-height: 1.6em;margin-bottom: 0px;margin-top: 0px;">﻿</p></td><td><p style="line-height: 1.6em;margin-bottom: 0px;margin-top: 0px;">﻿<img src="https://mmbiz.qpic.cn/mmbiz_jpg/Z6bicxIx5naLYpcX77tOy3epFKUtjFwCHhhsPfIYpgQNoDrkWSGlvVY4cibtYR6gibDer4BSCl4tF26icSIuJicSS7Q/640?wx_fmt=other&amp;from=appmsg">﻿</p></td></tr></tbody></table>

  

顺便便可以看一下目前根据 Figma 生成 HTML 代码有多简单：  

https://glama.ai/mcp/servers/kcftotr525  

  

  

**06**

  

  

**留给中国队的时间已经不多了**

Dario Amodei 认为，随着模型不断变强、变得足够好，它们会在现实中“破圈”。有些更偏研究用途的模型，我们自己也在做，很快就会出来，再下一步是 Agent，可以去自主执行任务，这会是另一个层级。到那时候，我相信人们会在接下来的两年中，比以前更深刻地理解到 AI 的风险和收益。我只是担心，这种觉醒可能来得很突然。

  

⚠️  虽然 AI Code 现阶段对前端开发者冲击最大，但可能几个月后后端开发者会更猝不及防。

参考链接

1.你的职业规划跟上AI节奏了吗？Anthropic CEO：初级程序员确定将在18个月内被淘汰：https://news.qq.com/rain/a/20250303A01G7Q00﻿

2.所有人都在谈Agent！Claude团队通俗解释：它是什么以及能做什么？：https://news.qq.com/rain/a/20250312A01Z4S00﻿

3.﻿[Claude MCP 突然爆火！AI Agent 圈的“万能插头”，直接让 Cursor 工作流效率提升 10 倍](https://mp.weixin.qq.com/s?__biz=MzU1NDA4NjU2MA==&mid=2247635098&idx=1&sn=08bb47b8cd535bd6ce33f5a4b9676b72&scene=21#wechat_redirect)：https://mp.weixin.qq.com/s/IM5T-5bOoezlJT0pN2bFLw  

  

![](https://mmbiz.qpic.cn/mmbiz_gif/OmCbZ5JK30GbpbpADRYqgC6MMvNfRPY8cGySPF2f9miavJibvCOiaUelcS2LX6uSMGEic1ztD9ECCvN2rcupEseySg/640?wx_fmt=gif&wxfrom=5&wx_lazy=1&tp=webp)

  

  

![](https://mmbiz.qpic.cn/mmbiz_gif/OmCbZ5JK30GMpsz1kdxGBRiclB5yjhBh5iamZLYrF2BOK4YNhXbjibibx63B6o5ubgOvYGRyUa0DK6gpI9YibkKUUCQ/640?wx_fmt=gif&wxfrom=5&wx_lazy=1&tp=webp)

欢迎留言一起参与讨论~