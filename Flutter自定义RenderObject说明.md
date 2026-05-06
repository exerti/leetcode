# Flutter 自定义 RenderObject 注意事项与实战示例

## 一、什么时候需要自定义 RenderObject

大多数 Flutter UI 场景，优先使用下面这些能力即可：

- 普通 `Widget`
- `CustomPaint`
- `SingleChildRenderObjectWidget`
- `MultiChildRenderObjectWidget`
- 现成布局组件，如 `Wrap`、`Stack`、`Column`

只有在下面这些场景里，才建议直接写 `RenderObject`：

1. 需要自定义布局协议
2. 需要精确控制子节点位置
3. 需要自定义命中测试
4. 需要在渲染层统一绘制额外效果
5. 现有 Widget 组合方式实现起来过于别扭、性能差或者逻辑不清晰

你的例子就是一个很典型的场景：

> 普通 `Wrap` 只能做自动换行，但你需要在某些 item 所在行下方动态插入一个子面板。

这个需求更适合放到 `RenderObject` 层处理。

相关代码位置：

- `ExpandableWrap`：`/Users/gaodun/flutter/gd_tiku_module/lib/dialog/expand_panel_dialog.dart`
- 核心布局实现：`/Users/gaodun/flutter/gd_tiku_module/lib/dialog/expand_panel_dialog.dart` 中的 `_RenderExpandableWrap`

---

## 二、自定义 RenderObject 的核心职责

在 Flutter 渲染体系里，职责大致如下：

- `Widget`：描述配置
- `Element`：连接 Widget 和 RenderObject
- `RenderObject`：真正负责布局、绘制、命中测试、语义

所以写 `RenderObject` 时要注意：

- 不要把业务状态塞进 `RenderObject`
- `RenderObject` 更适合只保存“渲染相关状态”
- 布局逻辑、绘制逻辑、命中测试逻辑要清晰分层

---

## 三、最小可用模板

下面是一份最小可用的单子节点 `RenderObject` 模板，适合理解基础结构：

```dart
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';

class DemoBox extends SingleChildRenderObjectWidget {
  final Color color;

  const DemoBox({
    super.key,
    required this.color,
    super.child,
  });

  @override
  RenderObject createRenderObject(BuildContext context) {
    return _RenderDemoBox(color: color);
  }

  @override
  void updateRenderObject(BuildContext context, covariant _RenderDemoBox renderObject) {
    renderObject.color = color;
  }
}

class _RenderDemoBox extends RenderBox with RenderObjectWithChildMixin<RenderBox> {
  _RenderDemoBox({
    required Color color,
    RenderBox? child,
  }) : _color = color {
    this.child = child;
  }

  Color _color;
  Color get color => _color;
  set color(Color value) {
    if (_color == value) return;
    _color = value;
    markNeedsPaint();
  }

  @override
  void performLayout() {
    if (child != null) {
      child!.layout(constraints.loosen(), parentUsesSize: true);
      size = constraints.constrain(
        Size(child!.size.width + 16, child!.size.height + 16),
      );
    } else {
      size = constraints.constrain(const Size(80, 40));
    }
  }

  @override
  void paint(PaintingContext context, Offset offset) {
    final canvas = context.canvas;
    final rect = offset & size;
    final paint = Paint()..color = color;
    canvas.drawRect(rect, paint);

    if (child != null) {
      context.paintChild(child!, offset + const Offset(8, 8));
    }
  }

  @override
  bool hitTestSelf(Offset position) => true;

  @override
  bool hitTestChildren(BoxHitTestResult result, {required Offset position}) {
    if (child == null) return false;
    return result.addWithPaintOffset(
      offset: const Offset(8, 8),
      position: position,
      hitTest: (result, transformed) {
        return child!.hitTest(result, position: transformed);
      },
    );
  }
}
```

---

## 四、写 RenderObject 时最重要的注意事项

### 1. 分清 `markNeedsLayout()` 和 `markNeedsPaint()`

属性更新后，必须根据影响范围触发正确刷新：

- 影响尺寸、换行、位置：`markNeedsLayout()`
- 只影响颜色、阴影、边框：`markNeedsPaint()`
- 影响无障碍语义：`markNeedsSemanticsUpdate()`

这是最容易写错的地方。

### 2. `performLayout()` 只做布局

`performLayout()` 中应该只做：

- 给 child 下发约束
- 调 child 的 `layout`
- 读取 child 的 `size`
- 计算自己的 `size`
- 记录 child 的 `offset`

不要在这里做绘制。

### 3. `paint()` 只做绘制

`paint()` 中应该只做：

- 画背景
- 画边框
- 画额外装饰
- 调 `context.paintChild()` 绘制子节点

不要在 `paint()` 里改布局数据。

### 4. `size` 必须满足 `constraints`

推荐总是用：

```dart
size = constraints.constrain(yourSize);
```

否则很容易触发布局断言。

### 5. 命中测试不能漏

如果组件需要响应点击、拖拽或手势，要检查：

- `hitTestSelf`
- `hitTestChildren`

视觉上能看到，不代表能点到。

### 6. 多子节点一定要配好 `ParentData`

如果是多子节点布局，通常会用：

- `ContainerRenderObjectMixin`
- `RenderBoxContainerDefaultsMixin`
- 自定义 `ParentData`

`ParentData` 常用于保存：

- `offset`
- 行号
- 布局标记
- 自定义缓存信息

---

## 五、结合你的 `ExpandableWrap` 看整体结构

文件：`/Users/gaodun/flutter/gd_tiku_module/lib/dialog/expand_panel_dialog.dart`

### 1. Widget 层：只负责传配置

`ExpandableWrap` 继承自 `MultiChildRenderObjectWidget`，主要职责是把这些信息传给渲染对象：

- `spacing`
- `runSpacing`
- `expandedIndices`
- `itemCount`
- `subPanelCount`
- 所有 children

这部分的职责非常标准：

- Widget 负责描述配置
- RenderObject 负责真正布局

### 2. ParentData：保存每个 child 的布局结果

```dart
class _ExpandableWrapParentData extends ContainerBoxParentData<RenderBox> {}
```

这里虽然没有额外字段，但 `ContainerBoxParentData` 已经自带 `offset`，足够保存每个子节点最终位置。

如果以后布局更复杂，可以扩展：

```dart
class _ExpandableWrapParentData extends ContainerBoxParentData<RenderBox> {
  int? rowIndex;
  bool isPanel = false;
}
```

### 3. RenderObject：负责整套布局流程

`_RenderExpandableWrap` 的职责主要有三块：

1. 给 item 和 panel 分别 layout
2. 把 item 按最大宽度分行
3. 把对应的 panel 插入到目标行后面

这正是自定义 RenderObject 最擅长处理的事情。

---

## 六、`ExpandableWrap` 的布局流程拆解

下面按你的实现逻辑拆成 5 步来理解。

### 第 1 步：区分普通 item 和子面板 panel

你的 `children` 实际是：

```dart
[...children, ...subPanels]
```

所以前 `itemCount` 个是普通 item，后面的是子面板。

在布局时，先遍历所有 child：

- item 用 `itemConstraints`
- panel 用 `panelConstraints`

其中 panel 被强制拉满整行宽度：

```dart
final panelConstraints = BoxConstraints(minWidth: maxWidth, maxWidth: maxWidth);
```

这就保证了子面板一定是整行展示。

### 第 2 步：把所有 item 按宽度分成多行

你这里手动实现了一套类似 `Wrap` 的换行逻辑：

- 当前行为空，直接放入
- 当前行不空时，判断 `currentRowWidth + spacing + item.width > maxWidth`
- 超过则换行
- 否则继续塞进当前行

这一步的产物是：

```dart
List<List<RenderBox>> rows
```

也就是每一行有哪些 item。

### 第 3 步：根据 `expandedIndices` 确定 panel 插入到哪一行

你传入的是展开 item 的索引列表：

```dart
expandedIndices
```

然后通过逐行累计 item 数量，找到某个索引属于哪一行。

找到后，把对应 panel 放到：

```dart
rowSubPanels[r]
```

也就是“第 r 行下面要插入哪些 panel”。

### 第 4 步：先算每一行高度

每行里 item 高度可能不同，所以你先求每行最大高度：

```dart
final List<double> rowHeights = rows.map((row) =>
  row.fold<double>(0, (h, item) => h < item.size.height ? item.size.height : h)
).toList();
```

这是很关键的一步。

因为 panel 不能接在某个 item 后面，而应该接在整行最低边之后。

### 第 5 步：给每个 child 写入最终 offset

然后统一做位置计算：

- item 从左到右摆放，更新 `dx`
- 一行结束后，`dy += rowHeight`
- 如果该行下方有 panel，就继续给 panel 设置 `Offset(0, dy)`
- panel 放完后，再决定是否加 `runSpacing`

最后：

```dart
size = constraints.constrain(Size(maxWidth, dy));
```

整个布局完成。

---

## 七、为什么这个需求适合 RenderObject，而不是继续堆 Widget

因为这个需求的核心不是“画一个 panel”，而是：

- item 自动换行
- 计算每一行高度
- panel 插入到指定行下面
- panel 推动后续行整体下移

这是一套典型的“行级布局控制”。

如果强行用普通 Widget 树组合：

- 逻辑会变复杂
- 很难准确知道 item 最终落在哪一行
- 面板插入位置不好算
- 容易出现多层嵌套和不稳定的布局行为

而自定义 `RenderObject` 可以一次性拿到所有 child，统一排版，逻辑更直接。

---

## 八、这个实现里值得注意的坑

### 1. `constraints.maxWidth` 最好保证是有限值

当前实现默认依赖有限宽度。

如果未来这个组件被放到一个横向无限宽场景，换行逻辑会失效。更稳妥的做法是加断言：

```dart
assert(constraints.hasBoundedWidth);
```

如果你的业务语义就是“它只能在有边界宽度的容器里用”，那么直接断言是最清晰的。

### 2. `expandedIndices` 与 `subPanels` 的顺序必须严格对应

当前实现默认：

- `expandedIndices[i]`
- 对应 `subPanelChildren[i]`

这意味着外部传参时，两者顺序必须一致。

这是一个隐式约束，后续维护时要特别注意。

### 3. setter 可以尽量避免无效重排

当前 `expandedIndices` 的 setter 每次都会 `markNeedsLayout()`。

如果父组件频繁 rebuild，而列表内容其实没变，就会导致多余 layout。

数据量小时问题不大，但如果后续变复杂，可以做内容比较后再决定是否重排。

---

## 九、可以怎么继续扩展这个 RenderObject

### 扩展 1：给展开项绘制高亮背景

你现在的 `paint()` 只是：

```dart
defaultPaint(context, offset);
```

如果以后想给“展开中的 item”加高亮，可以直接在 `paint()` 里先画背景，再调用 `defaultPaint`：

```dart
@override
void paint(PaintingContext context, Offset offset) {
  final canvas = context.canvas;

  RenderBox? child = firstChild;
  int idx = 0;
  while (child != null) {
    final pd = child.parentData as _ExpandableWrapParentData;

    if (idx < _itemCount && _expandedIndices.contains(idx)) {
      final rect = (offset + pd.offset) & child.size;
      final paint = Paint()..color = const Color(0x143B82F6);
      canvas.drawRRect(
        RRect.fromRectAndRadius(rect, const Radius.circular(6)),
        paint,
      );
    }

    child = pd.nextSibling;
    idx++;
  }

  defaultPaint(context, offset);
}
```

这种增强非常适合在渲染层做，因为布局结果已经有了，不需要再修改 Widget 树。

### 扩展 2：给 ParentData 加调试字段

当布局越来越复杂时，可以给 `ParentData` 增加：

- 当前 child 属于哪一行
- 是否是 panel
- 是否是展开项

这样调试会更直观。

---

## 十、实战总结

结合你这个例子，自定义 RenderObject 最值得记住的几句话是：

1. Widget 传配置，RenderObject 做布局
2. `performLayout()` 只负责算尺寸和位置
3. `paint()` 只负责画
4. 影响布局用 `markNeedsLayout()`，影响外观用 `markNeedsPaint()`
5. 多子节点布局时，`ParentData` 很重要
6. 你的 `ExpandableWrap` 是一个很标准、很适合学习的多子节点 RenderObject 实战案例

---

## 十一、建议的学习顺序

如果你想彻底吃透这份代码，建议按下面顺序看：

1. 先看 `ExpandableWrap` 如何把配置传给 `_RenderExpandableWrap`
2. 再看 `_RenderExpandableWrap.performLayout()`，重点理解“分行”和“插 panel”
3. 最后再看 `paint()` 和 `hitTestChildren()`，理解为什么这里可以先复用默认实现

---

## 十二、后续可做的优化方向

如果你接下来要继续打磨这个组件，可以优先考虑：

1. 给 `constraints.hasBoundedWidth` 加断言
2. 明确 `expandedIndices` 与 `subPanels` 的顺序约束
3. 给 `expandedIndices` setter 加变化判断，避免无效重排
4. 如果后续需要视觉增强，优先在 `paint()` 层做

---

如果后续你需要，我还可以继续补两份内容：

1. `ExpandableWrap.performLayout()` 逐行注释版
2. `SingleChildRenderObjectWidget` 和 `MultiChildRenderObjectWidget` 对比笔记
