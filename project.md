---
name: kuvio
description: 在设计规范和结构决策完成后，用于为 Alkaa 设计系统实现新的 Kuvio 组件
---

# Kuvio 组件实现

## 概述

Kuvio 是 Alkaa 设计系统的组件库。实现 Kuvio 组件意味着将设计规范转换为可重用的、主题感知的 Compose Multiplatform 可组合项，遵循严格的命名、结构和样式约定。

**核心原则：** 设计规范和组件架构在实现开始前必须完成。本指南指导实现工作流程。

## 何时使用

**必需：在以下技能完成后使用：**
- `superpowers:brainstorming` — 设计决策和组件结构已完成
- 设计规范存在且可访问（HTML 设计系统参考或 Figma）

**在以下情况下使用此技能：**
- 设计规范已定义（变体、布局、交互状态都已决定）
- 组件结构和插槽已完成
- 准备好编写实现代码
- 组件是代码库中的新组件

**不要在以下情况下使用：**
- 仍在设计或讨论结构（先使用 `superpowers:brainstorming`）
- 探索组件是否应该存在（先使用 `superpowers:writing-plans`）
- 设计决策不清楚或不完整
- 不确定变体、插槽或要公开的内容

**可选但推荐：**
- `superpowers:test-driven-development` — 先编写预览测试，其次是实现
- 使用 TDD 在完成前验证组件变体是否与设计规范匹配

## 开始前：验证设计规范已准备好

**您的设计规范必须包括：**
- 视觉结构（什么放在哪里、布局）
- 所有变体（大小、颜色角色、状态、禁用/活跃）
- 间距和大小（多少填充、相隔多远）
- 排版（每个区域使用哪种文本样式）
- 交互行为（点击、焦点等时发生什么）

**如果规范不完整或不清楚：**
- 停止。不要继续实现。
- 使用 `superpowers:brainstorming` 先完成规范。
- 不清楚的规范会导致返工。在编码前澄清。

## 实现工作流程

### 步骤 1：提取设计规范

读取设计系统 HTML 以找到组件部分：

```bash
grep -n "section-title\|subsection-title\|id=" /path/to/alkaa-ds.html | grep -i "ComponentName"
```

从规范中提取：
- 视觉结构和布局
- 变体（大小、状态、颜色角色）
- 间距/大小令牌
- 使用的排版
- 交互状态

### 步骤 2：研究现有组件

阅读至少 2 个参考实现：

- `KuvioCounterCard.kt` — 复杂卡片布局与插槽
- `KuvioDialog.kt` — 多部分组件与状态管理
- `KuvioEmojiIcon.kt` — 简单图标组件
- `KuvioBodyMediumText.kt` — 文本原语
- 徽章/项目组件以获取更多模式

查看：
- 插槽如何公开（`icon: (@Composable () -> Unit)?`）
- 令牌如何使用（`MaterialTheme.colorScheme.*`）
- 嵌套如何结构化（最多 ~3 级）
- 如何提取 ~60 行以下的可组合项

### 步骤 3：选择输出目录

放在：`libraries/designsystem/src/commonMain/kotlin/com/escodro/designsystem/components/kuvio/<folder>/`

| 类别 | 文件夹 | 示例 |
|------|--------|------|
| 文本变体 | `text/` | `KuvioBodyMediumText`, `KuvioLabelSmallText` |
| 图标/头像 | `icon/` | `KuvioEmojiIcon`, `KuvioAvatar` |
| 数据显示 | `card/` | `KuvioCounterCard`, `KuvioTaskCard` |
| 徽章/标签 | `badge/` | `KuvioBadge`, `KuvioChip` |
| 列表项 | `item/` | `KuvioTaskItem`, `KuvioListItem` |
| 模态/覆盖层 | `dialog/` | `KuvioDialog`, `KuvioAlertDialog` |
| 新类别 | `<lowercase-no-spaces>/` | 如果没有匹配则创建 |

### 步骤 4：实现组件

**命名规则：**
- 所有公开可组合项：`Kuvio<Name>` 前缀（PascalCase）
- 包：`com.escodro.designsystem.components.kuvio.<folder>`
- 私有子可组合项：小写 camelCase

**代码风格：**
- 重用现有 Kuvio 原语（`KuvioBodyMediumText`, `KuvioEmojiIcon`）— 从不使用原始 `Text`
- 形状/图标不使用 `Canvas` — 使用 `Box`, `Surface`, `Icon`, `clip`, `background`
- 仅主题感知：`MaterialTheme.colorScheme.*` 和 `MaterialTheme.shapes.*` — 除预览外无硬编码颜色
- 插槽优于配置：优先使用 `icon: (@Composable () -> Unit)?` 而不是嵌套对象
- 每个可组合项最多 ~60 行，最多 ~3 级嵌套
- 需要时将逻辑块提取到私有可组合项

**结构：**
- 每个可组合项一个文件（除非密切相关）
- 每个公开函数和参数的 KDoc
- 常量命名：**PascalCase**（`AddTaskPlaceholder`，不是 `ADD_TASK_PLACEHOLDER`）

### 步骤 5：外部化字符串

用户可见的字符串放在 `resources/` 模块中（不是硬编码）：

```kotlin
// ❌ 错误
Text("Add task here")

// ✅ 正确 - 在 resources/src/commonMain/resources/values/strings.xml
<string name="kuvio_add_task_bar_placeholder">Add task here</string>

// 然后通过 stringResource() 使用
Text(stringResource(Res.string.kuvio_add_task_bar_placeholder))
```

字符串键模式：`kuvio_<component>_<purpose>`

**例外：** 预览常量可以在文件底部硬编码为 `private const val`。

### 步骤 6：添加预览

每个文件必须有浅色和深色预览：

```kotlin
@Preview(showBackground = true)
@Composable
private fun KuvioXxxLightPreview() {
    AlkaaThemePreview {
        // 带有真实示例数据的组件
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1B2D)
@Composable
private fun KuvioXxxDarkPreview() {
    AlkaaThemePreview(isDarkTheme = true) {
        // 相同组件
    }
}
```

使用 `AlkaaThemePreview`（来自 `com.escodro.designsystem.theme`）。

对于变体：每个有意义的变体（大小、状态、颜色）一对预览。

将预览字符串放在文件底部作为 `private const val`。

### 步骤 7：验证代码质量

编译检查：
```bash
./gradlew :libraries:designsystem:compileKotlinDesktop 2>&1 | tail -30
```

静态分析：
```bash
./gradlew :libraries:designsystem:detektCommonMainSourceSet ktlintCheck 2>&1 | grep -E "\.kt:|BUILD|FAILED"
```

修复报告的问题：
- 常量名称是 PascalCase（不是 SCREAMING_SNAKE_CASE）
- 没有可组合项超过 ~60 行
- Lambda 名称是现在时（`onFocus`，不是 `onFocused`）
- 所有字符串外部化到资源

### 步骤 8：报告

列出创建/修改的文件并描述：
- 公开的变体
- 可用的插槽
- 对其他 Kuvio 组件的依赖
- 使用的设计令牌

## 快速参考：关键规则

| 规则 | 正确 | 错误 |
|------|------|------|
| **命名** | `KuvioTaskChip` | `TaskChip`, `TASK_CHIP` |
| **字符串** | `stringResource(Res.string.kuvio_component_label)` | `"Add task"` |
| **颜色** | `MaterialTheme.colorScheme.primary` | `Color(0xFF123456)` |
| **文本** | `KuvioBodyMediumText()` | `Text(fontSize=14.sp, ...)` |
| **大小** | ~60 行，最多 3 级嵌套 | 200+ 行，深度嵌套 |
| **插槽** | `icon: (@Composable () -> Unit)?` | `iconColor: Color, iconSize: Dp, ...` |
| **常量** | `private const val AddTaskPlaceholder = "Add task..."` | `private const val ADD_TASK_PLACEHOLDER` |

## 常见错误

| 错误 | 为什么错误 | 修复 |
|------|-----------|------|
| 硬编码颜色 | 破坏深色模式，难以主题化 | 使用 `MaterialTheme.colorScheme.*` |
| 原始 `Text()` 而不是 Kuvio 文本 | 排版不一致 | 使用 `KuvioBodyMediumText()` 等 |
| 代码中的字符串 | 不可本地化，违反模式 | 移到 `resources/` strings.xml |
| 函数 > 60 行 | 难以测试、理解、重用 | 提取私有子可组合项 |
| 深度嵌套（4+ 级） | 可读性下降 | 提取逻辑块到私有可组合项 |
| 配置对象 | 难以扩展、测试、组合 | 使用可组合项插槽代替 |
| 跳过预览 | 无法视觉验证 | 为每个变体添加浅色/深色对 |
| `Canvas` 用于形状 | 难以维护，难以主题化 | 使用 `Box`, `clip`, `background` 修饰符 |

## 红旗 - 停止并审查

如果您注意到以下任何情况，您即将违反 kuvio 模式：

**实现前：**
- [ ] 没有设计规范或规范不完整/不清楚 → 停止。先使用头脑风暴。
- [ ] 不确定组件需要哪些变体、插槽或状态 → 停止。先完成设计。
- [ ] 不清楚要研究哪些现有组件 → 停止。询问哪些模式适用于您的组件。

**实现期间：**
- [ ] 即将硬编码颜色（即使"仅用于此变体"）→ 使用 `MaterialTheme.colorScheme.*`
- [ ] 函数接近 80+ 行 → 提取私有子可组合项
- [ ] 嵌套深于 3 级 → 提取逻辑块
- [ ] 原始 `Text()` 而不是 Kuvio 文本原语 → 使用 `KuvioBodyMediumText()` 等
- [ ] 代码中的字符串（预览可以）→ 移到 `resources/strings.xml`
- [ ] 由于时间压力想跳过预览 → 编写它们。它们能及早发现错误。

**编写代码后：**
- [ ] 跳过 detekt/ktlint 检查 → 运行它们。它们能发现样式违规。
- [ ] 预览看起来不对 → 现在修复，不要在合并后修复。
- [ ] 组件在预览中与设计规范不匹配 → 完成前修复。

**所有这些意味着：** 您即将错过 kuvio 模式的一部分。暂停并在继续前更正。

## 设计系统上下文

关键模块：
- `libraries/designsystem/` — Kuvio 组件、主题、令牌
- `resources/` — 字符串（本地化）、颜色、间距
- 参考：`/assets/designsystem/alkaa-ds.html`

Kuvio 组件是多平台（Android/iOS/Desktop）、主题感知、可访问且可通过预览测试的。
