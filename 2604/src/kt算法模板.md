# Kotlin 算法模板速查

按"可复用模板"收录,每条配:适用场景 / 骨架 / 心智模型 / 记忆口诀。

---

## 1. 自底向上两两合并(分治迭代版)

### 适用场景
- 有 **k 个同类有序单元**(链表、数组段、区间……),要全部合并成 1 个。
- 希望 **O(N log k)** 时间 + **O(1)** 额外空间(避免递归栈)。
- 经典题:合并 K 个升序链表、归并排序、k 路归并。

### 5 行骨架

```kotlin
var interval = 1
while (interval < n) {
    for (i in 0 until n - interval step interval * 2) {
        units[i] = mergeTwo(units[i], units[i + interval])
    }
    interval *= 2
}
return units[0]
```

要点:
- `i` 上界 `n - interval`:保证右半存在
- `step = interval * 2`:跳过刚合并完的左半
- 结果写回 `units[i]`:下轮还能命中

### 心智模型(合并二叉树)

```
k=4:
         merge(AB, CD)            <- 轮 2 (interval=2)
         /          \
    merge(A,B)   merge(C,D)       <- 轮 1 (interval=1,叶子层)
     / \          / \
    A   B        C   D
```

- **外层 while = 树的层数**(log k 层)
- **内层 for = 这一层所有待合并对**
- **interval 翻倍 = 爬到上一层**

### 与递归版的对应

```kotlin
// 自顶向下递归版(产生同一棵树,只是 DFS 顺序不同)
fun divide(l: Int, r: Int): T {
    if (l == r) return units[l]
    val m = (l + r) ushr 1
    return mergeTwo(divide(l, m), divide(m + 1, r))
}
```

递归 = 深度优先填树;while+for = 层序从底向上填树。**同一棵树,两种遍历。**

### 记忆口诀

> **"左吃右,步长翻倍,全部归零位。"**

- 左吃右:合并两条,结果写左边
- 步长翻倍:interval 1 → 2 → 4 …
- 归零位:最终答案永远在 `units[0]`

### 边界自动覆盖
- **空集**:显式 `if (n == 0) return ...`
- **单元**:`interval=1 < 1` 为 false,跳过循环直接返回 `units[0]`

### 已使用本模板的题目
- `2604/src/ListNode/合并 K 个升序链表.kt` - 分治版 `mergeKLists`
- `2604/src/ListNode/排序链表.kt` - 迭代版 `sortListIterative`

---

## 2. 回溯三步(DFS 排列 / 组合 / 子集)

### 适用场景
- 枚举所有可行解:全排列、组合、子集、路径、解数独等。
- 需要"选 → 递归 → 撤销"的模板。
- 经典题:全排列、组合总和、子集、N 皇后。

### 骨架

```kotlin
fun dfs(
    nums: IntArray,
    path: MutableList<Int>,
    used: BooleanArray,
    ans: MutableList<MutableList<Int>>
) {
    // 1) 终止
    if (path.size == nums.size) {
        ans.add(ArrayList(path))   // 必须深拷贝
        return
    }
    // 2) 选择 + 递归 + 撤销
    for (i in nums.indices) {
        if (used[i]) continue
        path.add(nums[i]); used[i] = true
        dfs(nums, path, used, ans)
        used[i] = false; path.removeAt(path.size - 1)
    }
}
```

### 心智模型
一棵决策树:每个节点是"当前 path";每条边是"选一个未用的元素"。  
回溯 = DFS 这棵树,到达叶子就记录答案。

### 三个坑
- `ans.add(path)` **必须深拷贝**:`ArrayList(path)` 或 `path.toMutableList()`,否则最后 `ans` 全是空。
- `used[i] = true` 必须有对应 `used[i] = false`(成对出现)。
- `path.add` 必须有对应 `path.removeAt(size-1)`(成对出现)。

### 口诀
> **"选进去,递下去,撤回来。"**

### 变体
- **组合(不允许重复顺序)**:for 从 `start` 开始,递归时传 `i + 1`。
- **含重复元素去重**:先 `nums.sort()`,`if (i > start && nums[i] == nums[i-1] && !used[i-1]) continue`。

### 已使用本模板的题目
- `2604/src/dfs/全排列.kt` - `permute` / `dfs`

---

## 3. Kadane(以 i 结尾的最值 DP)

### 适用场景
- 连续子数组的"最大和 / 最大积 / 最大长度"等。
- 状态定义可归约为 `f(i) = 依赖 f(i-1)`。
- 经典题:最大子数组和、乘积最大子数组、最长连续递增子序列。

### 骨架(最大子数组和)

```kotlin
var cur = nums[0]
var best = nums[0]
for (i in 1 until nums.size) {
    cur = maxOf(cur + nums[i], nums[i])   // 接 or 另起
    best = maxOf(best, cur)
}
return best
```

### 心智模型
以位置 `i` 结尾的最大子数组,形态只有两种:
- A. 只含 `nums[i]` 本身
- B. `nums[i]` 接在"以 i-1 结尾的最优段"后面

取两者中大的,即 `f(i) = max(nums[i], f(i-1) + nums[i])`。  
最终答案 = 所有 `f(i)` 的最大值。

### 空间压缩
`f(i)` 只依赖 `f(i-1)` → 一个变量 `cur` 就够,空间 O(1)。

### 口诀
> **"累加变亏就重来,一路盯住最大值。"**

### 变体
- **乘积最大子数组**:同时维护 `curMax` 和 `curMin`(负数翻身)。
- **环形子数组最大和**:`max(Kadane, total - minSubarray)`。
- **前缀和视角**:`best = max(pre[j] - min(pre[0..j-1]))`,同样 O(n)。

### 边界
- 数组非空假设;若允许空数组,开头加 `if (nums.isEmpty()) return 0`。
- **全负数也对**:`cur = max(...)` 会一直取 `nums[i]`(另起),`best` 取其中最大负数。

### 已使用本模板的题目
- `2604/src/dp/ 最大子数组和.kt` - `maxSubArray`(Kadane 版)与展开版 / 前缀和版 / 分治版对拍

---

## 4. 双指针对撞(两端往中间扫)

### 适用场景
- 数组(通常有序或单调)两端有可比信息,每轮淘汰一端。
- 经典题:两数之和(有序)、盛最多水的容器、接雨水、回文判定、三数之和的内层双指针。

### 骨架(通用)

```kotlin
var l = 0
var r = n - 1
while (l <= r) {
    // 根据"较差 / 较小 / 不满足条件"的一端决策
    if (cond(l, r)) {
        // 结算 l,l++
        l++
    } else {
        // 结算 r,r--
        r--
    }
}
```

### 接雨水实例

```kotlin
var leftCeil = height.first()
var rightCeil = height.last()
while (l <= r) {
    if (leftCeil < rightCeil) {
        cap += leftCeil - height[l]; l++
        if (l < height.size) leftCeil = maxOf(leftCeil, height[l])
    } else {
        cap += rightCeil - height[r]; r--
        if (r >= 0) rightCeil = maxOf(rightCeil, height[r])
    }
}
```

### 心智模型
双方各自"当前看到的最好值"已经固定,**较差的一端无法再被改善**,  
所以先结算较差的一端、让它前进 —— 这是贪心的正当性。

### 正确性要点
- **单调不变式**:`leftCeil/rightCeil` 只增不减。
- **较小一端封顶**:`min(leftCeil, rightCeil)` 就是当前格子能接水的上限。
- **每个下标只处理一次**:不重不漏,O(n)。

### 口诀
> **"比小的一边,算这一格,指针前进,更新上限。"**

### 变体
- **两数之和(有序)**:`sum < target → l++;sum > target → r--;sum == target → 返回`。
- **盛最多水的容器**:每轮移动较矮的一端,记录面积。
- **回文判定**:两端比较,不等则 false。

### 已使用本模板的题目
- `2604/src/单调栈/接雨水.kt` - `trap`(双指针版)

---

## 5. 网格 Flood Fill(DFS / BFS)

### 适用场景
- 二维网格里按连通块处理:岛屿数量、最大岛屿面积、封闭区域、图像染色、01 矩阵距离。
- 起点扫到整片连通区,常伴随"标记访问"避免重复。
- 遍历**所有未访问的陆地**,每次开启一次 flood fill → **答案往往 = fill 次数 / 最大一次的收获**。

### DFS 骨架(原地改,最常用)

```kotlin
val dirs = arrayOf(intArrayOf(-1,0), intArrayOf(1,0), intArrayOf(0,-1), intArrayOf(0,1))

fun numIslands(grid: Array<CharArray>): Int {
    if (grid.isEmpty()) return 0
    val m = grid.size; val n = grid[0].size
    var count = 0
    for (i in 0 until m) for (j in 0 until n) {
        if (grid[i][j] == '1') {
            count++
            dfs(grid, i, j, m, n)
        }
    }
    return count
}

fun dfs(grid: Array<CharArray>, x: Int, y: Int, m: Int, n: Int) {
    if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] != '1') return
    grid[x][y] = '0'                          // 访问标记:原地改,省 visited 数组
    for (d in dirs) dfs(grid, x + d[0], y + d[1], m, n)
}
```

> 不想改原数据时:开 `visited: Array<BooleanArray>`,判断条件多一条即可。

### BFS 骨架(大网格首选,避免栈溢出)

```kotlin
fun numIslandsBFS(grid: Array<CharArray>): Int {
    if (grid.isEmpty()) return 0
    val m = grid.size; val n = grid[0].size
    var count = 0
    val q: ArrayDeque<IntArray> = ArrayDeque()
    for (i in 0 until m) for (j in 0 until n) {
        if (grid[i][j] != '1') continue
        count++
        q.addLast(intArrayOf(i, j)); grid[i][j] = '0'
        while (q.isNotEmpty()) {
            val (x, y) = q.removeFirst()
            for (d in dirs) {
                val nx = x + d[0]; val ny = y + d[1]
                if (nx in 0 until m && ny in 0 until n && grid[nx][ny] == '1') {
                    grid[nx][ny] = '0'            // 入队前立刻标记,避免重复入队
                    q.addLast(intArrayOf(nx, ny))
                }
            }
        }
    }
    return count
}
```

### 心智模型
- 主循环扫每个格子,只关心"没访问过的陆地":一见到就**开一次 flood fill**。
- flood fill 内部:把整片连通陆地一次性全部染色(改 0 或 visited=true)。
- **外层计数 / 内层收集信息**:外层累加 fill 次数,内层可返回"该片面积/周长/是否触边"等。

### 口诀
> **"见陆地就淹一片,淹完再数下一片。"**

### 三个典型坑
- **忘记标记 → 死循环或超时**:入队/进 DFS 前立刻标记,不要等出队才改。
- **坐标越界判断顺序**:先判越界,再读 `grid[nx][ny]`,否则 NPE/ArrayIndexOutOfBounds。
- **大网格 DFS 栈爆**:>几千深度时改 BFS 或显式栈。

### 变体
- **岛屿最大面积**:DFS 返回 `1 + 四个方向 dfs 之和`,外层取 max。
- **封闭区域 / 被围绕的区域**:先从边界上所有陆地反向 fill 标成"安全",剩下的才是封闭的。
- **01 矩阵(最近 0 的距离)**:多源 BFS,所有 0 同时入队,按层扩散。
- **并查集解法**:把网格看成图,相邻陆地 union,答案 = 独立集合数。大网格 + 动态加陆地时更方便。

### 已使用本模板的题目
- `2604/src/dfs/岛屿数量.kt` - `numIslands`(visited 数组版 DFS)
- `2604/src/dfs/岛屿的最大面积.kt` - 变体:内层返回面积

---

## 6. 堆 / 优先队列(PriorityQueue)

### 适用场景
- 需要"动态取最小/最大"而不是一次排好:Top K、合并 K 路有序、数据流中位数、任务调度、丑数。
- 频繁插入 + 频繁取极值:每次 O(log n)。
- 经典题:前 K 个高频元素、合并 K 个升序链表、数据流中位数、第 K 大、最小的 K 个数、会议室 II。

### 基本 API(速记;详见 `2604/src/kt声明语法.md` 第 7 / 15 节)

```kotlin
import java.util.PriorityQueue

val minHeap = PriorityQueue<Int>()                              // 默认小顶堆
val maxHeap = PriorityQueue<Int>(compareByDescending { it })    // 大顶堆(推荐)
val maxHeap2 = PriorityQueue<Int> { a, b -> b - a }             // 大顶堆(注意溢出)

pq.offer(x)        // 入堆
pq.peek()          // 看堆顶,不弹
pq.poll()          // 弹堆顶
pq.size; pq.isEmpty()
```

> 大数比较用 `a.compareTo(b)` 或 `compareBy`,别直接 `a - b`(溢出)。

### 套路 1:Top K 最大(反直觉 —— 用**小顶堆**)

```kotlin
fun topKLargest(nums: IntArray, k: Int): IntArray {
    val pq = PriorityQueue<Int>()                   // 小顶堆!
    for (x in nums) {
        pq.offer(x)
        if (pq.size > k) pq.poll()                  // 堆大小始终 ≤ k,踢掉最小的
    }
    return pq.toIntArray()                          // 剩下的就是最大的 k 个
}
```

**为什么小顶堆?** 堆顶是堆内最小值,堆大小始终为 k。新元素如果比堆顶还小,说明它没资格进 Top K;如果比堆顶大,就把堆顶踢出来换新的。最终留下的必然是整体最大的 k 个。

### 套路 2:Top K 最小 —— 对称,用**大顶堆**

```kotlin
val pq = PriorityQueue<Int>(compareByDescending { it })
for (x in nums) { pq.offer(x); if (pq.size > k) pq.poll() }
```

### 套路 3:合并 K 路有序(链表 / 数组 / 流)

```kotlin
val pq = PriorityQueue<ListNode> { a, b -> a.`val` - b.`val` }
for (head in lists) if (head != null) pq.offer(head)         // 每路的头入堆

val dummy = ListNode(0); var tail: ListNode = dummy
while (pq.isNotEmpty()) {
    val node = pq.poll()
    tail.next = node; tail = node
    if (node.next != null) pq.offer(node.next)               // 取谁,补谁的下一个
}
return dummy.next
```

堆里**任意时刻最多 k 个节点**(每路一个当前候选)。

### 套路 4:双堆求数据流中位数

```kotlin
class MedianFinder {
    private val lo = PriorityQueue<Int>(compareByDescending { it })  // 左半,大顶堆
    private val hi = PriorityQueue<Int>()                            // 右半,小顶堆

    fun addNum(x: Int) {
        lo.offer(x)                    // 先进左
        hi.offer(lo.poll())            // 左的最大归位到右
        if (hi.size > lo.size) lo.offer(hi.poll())   // 保持 lo.size ≥ hi.size
    }

    fun findMedian(): Double =
        if (lo.size > hi.size) lo.peek().toDouble()
        else (lo.peek() + hi.peek()) / 2.0
}
```

**不变式**:`lo.size == hi.size` 或 `lo.size == hi.size + 1`;  
`lo.peek() ≤ hi.peek()`(左半最大 ≤ 右半最小)。

### 套路 5:对象按属性排(多字段)

```kotlin
data class Task(val priority: Int, val time: Int)

val pq = PriorityQueue<Task>(
    compareBy<Task> { it.priority }.thenBy { it.time }    // priority 升,相同看 time 升
)
```

### 心智模型
- **堆 = "永远知道当下最值"的动态容器**。
- 想要 Top K 大 → 小顶堆(堆顶是门槛);想要 Top K 小 → 大顶堆。
- "合并 K 路"本质是 K 条有序流各自派 1 名代表到堆里比赛,冠军出堆并换下一位候选。

### 三个常见坑
- **Top K 堆选型搞反**:记住"要最大用小顶,要最小用大顶,堆顶做门槛"。
- **comparator 用减法溢出**:`a - b` 在 Int 边界不安全,用 `a.compareTo(b)` 或 `compareBy`。
- **忘了补下一个候选**:合并 K 路里,`poll` 之后必须把该路的 `next` 入堆,否则丢数据。

### 口诀
> **"堆顶守门槛,新人比一场,大的留、小的走 —— 或反过来。"**

### 复杂度
- 入堆 / 出堆:**O(log n)**
- Top K:**O(n log k)**,比全排序 O(n log n) 省
- 合并 K 路(总 N 个元素):**O(N log k)**
- 空间:取决于维持的堆规模(k 或 K)

### 已使用本模板的题目
- `2604/src/ListNode/合并 K 个升序链表.kt` - `mergeKListsByHeap`(合并 K 路套路)

---

## 7. 子序列 / 子串 DP(LIS / LCS / 最长重复子数组)

### 适用场景
- 在一个或两个序列上,求"最长 / 最多 / 最少"的某种子结构:
  - **子序列**(可跳过元素,非连续):LIS、LCS、编辑距离、最长回文子序列。
  - **子串 / 子数组**(必须连续):最长重复子数组、最长回文子串、最长公共子串、连续递增。

### 关键对比(这是这条模板最重要的认知)

| 维度 | 子序列 | 子串 / 子数组 |
|---|---|---|
| 是否必须连续 | 否 | 是 |
| 状态定义 | `f[i]`:前 i 个的最优 / 以 i 结尾的最优 | `f[i]`:**必须以 i 结尾**的最优 |
| 不匹配时 | 继承/取两边较大(继续累积) | **清零**(必须重新起算) |
| 答案位置 | `f[n-1]` 或 `max(f)` | **一定是** `max(f)`(因为每个 i 都只代表结尾在 i) |
| 二维例子 | LCS:`f[i][j]=max(f[i-1][j], f[i][j-1], 匹配+1)` | 最长重复子数组:`f[i][j]=匹配?f[i-1][j-1]+1:0` |

**一句话区分**:子序列允许"跳过断点继续累积";子串一断就清零从头算。

---

### 套路 1:LIS - O(n²) 经典 DP

```kotlin
fun lengthOfLIS(nums: IntArray): Int {
    if (nums.isEmpty()) return 0
    val f = IntArray(nums.size) { 1 }     // f[i] = 以 i 结尾的最长递增子序列长度
    for (i in 1 until nums.size) {
        for (j in 0 until i) {
            if (nums[j] < nums[i]) f[i] = maxOf(f[i], f[j] + 1)
        }
    }
    return f.max()!!                       // 答案是 max(f),不是 f[n-1]
}
```

注意:子序列问题的答案**不一定在 f[n-1]**,因为"以 n-1 结尾"未必最优。

---

### 套路 2:LIS - O(n log n) 贪心 + 二分

```kotlin
fun lengthOfLISFast(nums: IntArray): Int {
    val tails = IntArray(nums.size)        // tails[len] = 长度为 len+1 的 LIS 的"最小结尾"
    var size = 0
    for (x in nums) {
        // 二分找第一个 >= x 的位置
        var l = 0; var r = size
        while (l < r) {
            val m = (l + r) ushr 1
            if (tails[m] < x) l = m + 1 else r = m
        }
        tails[l] = x
        if (l == size) size++
    }
    return size
}
```

**核心诀窍**:`tails` **不是**真正的 LIS,只是"每种长度的最小可能结尾"。  
越小的结尾越容易被延长 → 贪心正确性来源。

---

### 套路 3:LCS - 最长公共子序列(双序列 DP)

```kotlin
fun lcs(a: String, b: String): Int {
    val m = a.length; val n = b.length
    val f = Array(m + 1) { IntArray(n + 1) }
    for (i in 1..m) for (j in 1..n) {
        f[i][j] = if (a[i - 1] == b[j - 1]) f[i - 1][j - 1] + 1
                  else maxOf(f[i - 1][j], f[i][j - 1])
    }
    return f[m][n]                         // 答案在右下角
}
```

**两类转移**:
- **字符相等**:来自 `f[i-1][j-1]` 再 +1(把这对字符"配上")。
- **字符不等**:要么舍弃 a 的末尾取 `f[i-1][j]`,要么舍弃 b 的末尾取 `f[i][j-1]`,取大的那个(因为是子序列可以跳过)。

---

### 套路 4:最长重复子数组(双序列连续 DP)

```kotlin
fun findLength(a: IntArray, b: IntArray): Int {
    val m = a.size; val n = b.size
    val f = Array(m + 1) { IntArray(n + 1) }
    var best = 0
    for (i in 1..m) for (j in 1..n) {
        if (a[i - 1] == b[j - 1]) {
            f[i][j] = f[i - 1][j - 1] + 1   // 必须同时以 i-1、j-1 结尾
            best = maxOf(best, f[i][j])
        }
        // 不等时 f[i][j] 保持 0,意味着"以 (i,j) 结尾的重复长度 = 0"
    }
    return best                             // 答案是 max(f),不是 f[m][n]
}
```

**和 LCS 的唯一差别**:不等时**不继承两边最大值,而是清零**。这就是"子序列 → 子串"的本质变化。

---

### 套路 5:最长回文子串(中心扩展,非 DP 但更快)

```kotlin
fun longestPalindrome(s: String): String {
    if (s.isEmpty()) return ""
    var start = 0; var end = 0
    for (i in s.indices) {
        val l1 = expand(s, i, i)             // 奇数长中心
        val l2 = expand(s, i, i + 1)         // 偶数长中心
        val len = maxOf(l1, l2)
        if (len > end - start) {
            start = i - (len - 1) / 2
            end = start + len
        }
    }
    return s.substring(start, end)
}
private fun expand(s: String, l0: Int, r0: Int): Int {
    var l = l0; var r = r0
    while (l >= 0 && r < s.length && s[l] == s[r]) { l--; r++ }
    return r - l - 1
}
```

> 区间 DP 版本也能做,但中心扩展更短、常数更小。

---

### 心智模型
- **子序列**:想象一条"可以跳步"的路径,两端各自往前挪,断开不影响继续挪。
- **子串 / 子数组**:想象一条"一旦断就作废"的链,只能以"结尾位置"为状态。
- **二维 DP 表**:子序列取 max(左、上、匹配),子串只取匹配(左上对角 +1)或清零。

---

### 口诀
> **"子序列允许断口续命,子串一断就归零;答案 max 靠二维表,LIS 想快就贪心配二分。"**

---

### 常见坑
- **答案位置搞错**:子序列 LIS / 子串类都得 `max(f)`,不是 `f[n]`。LCS 才是 `f[m][n]`。
- **初始化错位**:`f` 开 `(m+1) × (n+1)` 的边界行/列默认 0,避免单独处理首行首列。
- **下标偏移**:用 `a[i-1]`、`b[j-1]` 对应 DP 里的第 i、j 维,一开始容易写成 `a[i]` 越界。
- **LIS 二分版**:二分找的是"第一个 `>= x`"(非严格递增)还是"`> x`"(严格),题目要求不同。

---

### 变体
- **编辑距离**:`f[i][j] = min(删/插/改) + 1`,匹配时直接继承对角。
- **最长回文子序列**:区间 DP,`f[l][r] = s[l]==s[r] ? f[l+1][r-1]+2 : max(f[l+1][r], f[l][r-1])`。
- **最长递增**对应**最长不减**:二分条件从 `<` 改 `<=`。
- **两个字符串的最小删除操作**:先求 LCS,再 `m + n - 2*LCS`。

---

### 已使用本模板的题目
- (待补充,你动手后把路径回填这里)


---

## 8. 二叉树遍历(DFS 三序 / BFS 层序)

### 适用场景
- 访问二叉树的每个节点,顺序要求不同:前序(根左右)、中序(左根右)、后序(左右根)、层序(按层)。
- 几乎所有二叉树题的基础:最大深度、路径和、对称、右视图、Zigzag、层平均、LCA、BST 中序有序性。

### 骨架 1:DFS 三序递归(同一结构,只换"访问位置")

```kotlin
fun dfs(node: TreeNode?) {
    if (node == null) return
    // visit(node)           // 前序:到达时访问
    dfs(node.left)
    // visit(node)           // 中序:回到自己时访问
    dfs(node.right)
    // visit(node)           // 后序:离开时访问
}
```

**关键认知**:三序不是三个算法,是**同一次 DFS 在不同时机**记录节点。

### 骨架 2:DFS 迭代前序(栈,先压右后压左)

```kotlin
fun preorder(root: TreeNode?): List<Int> {
    val ans = mutableListOf<Int>()
    if (root == null) return ans
    val stack = ArrayDeque<TreeNode>()
    stack.addLast(root)
    while (stack.isNotEmpty()) {
        val node = stack.removeLast()
        ans.add(node.`val`)
        node.right?.let { stack.addLast(it) }   // 先压右
        node.left?.let { stack.addLast(it) }    // 后压左,弹栈时左先出
    }
    return ans
}
```

> 后序迭代可以"仿前序 + 先左后右"得到"根右左",再反转结果得到"左右根"。

### 骨架 3:DFS 迭代中序(栈 + 游动指针,BST 必备)

```kotlin
fun inorder(root: TreeNode?): List<Int> {
    val ans = mutableListOf<Int>()
    val stack = ArrayDeque<TreeNode>()
    var cur = root
    while (cur != null || stack.isNotEmpty()) {
        while (cur != null) {       // 一路向左压栈
            stack.addLast(cur)
            cur = cur.left
        }
        val node = stack.removeLast()
        ans.add(node.`val`)         // 中序访问点
        cur = node.right            // 转向右子树
    }
    return ans
}
```

### 骨架 4:BFS 层序(按层)—— 右视图 / Zigzag / 层平均 的根基

```kotlin
fun levelOrder(root: TreeNode?): List<List<Int>> {
    val ans = mutableListOf<List<Int>>()
    if (root == null) return ans
    val queue = ArrayDeque<TreeNode>()
    queue.addLast(root)
    while (queue.isNotEmpty()) {
        val size = queue.size                // ⚠ 先"拍照",锁定本层节点数
        val level = mutableListOf<Int>()
        for (i in 0 until size) {
            val node = queue.removeFirst()
            level.add(node.`val`)
            node.left?.let { queue.addLast(it) }
            node.right?.let { queue.addLast(it) }
        }
        ans.add(level)
    }
    return ans
}
```

### 心智模型
- **DFS = 深度优先**:栈(递归栈或显式栈),天然携带"路径信息",适合"路径/累加/对称/LCA"。
- **BFS = 广度优先**:队列,天然按层,适合"最短路/视图/层统计/Zigzag"。
- **前/中/后 的时机含义**:
  - 前序 = **先处理自己再下去**(适合传递上下文,如 DFS 建树)。
  - 中序 = **左边做完回来再处理自己**(BST 里等价于升序扫描)。
  - 后序 = **孩子都处理完才轮到自己**(适合汇总,如求深度、路径和、LCA)。

### 口诀
> **"三序一个模板,换访问时机;按层用队列,先拍照再循环。"**

### 常见坑
- **BFS 忘记缓存 size**:直接 `while queue.isNotEmpty` 会把下一层和本层混起来,分层失败。
- **迭代前序压栈顺序搞反**:必须"先压右后压左",出栈顺序才是"左前右后"。
- **迭代中序缺 while(cur!=null) 内循环**:漏了就停不下来或访问顺序错。
- **空指针**:子节点入队/入栈前要判空,或用 `?.let { ... }`。
- **TreeNode 值字段**:LeetCode 惯用 `` `val` ``(Kotlin 反引号转义),调用时别漏反引号。

### 变体
- **右视图 / 左视图**:BFS 取每层最后/第一;或 DFS 前序先右后左,`depth == ans.size` 时第一次进这层就记。
- **最大深度**:DFS 后序 `1 + max(left, right)`;或 BFS 数层数。
- **最小深度**:注意空子树不算叶子;BFS 遇到第一个叶子即可返回。
- **对称判断**:双指针 DFS 同步走两棵子树,一左一右镜像比较。
- **Zigzag 层序**:BFS 按层,偶数层 `addLast`、奇数层 `addFirst`(用 `ArrayDeque<Int>`)。
- **路径总和 / 所有路径**:DFS 前序 + 回溯(`path.add` / `path.removeAt(size-1)`)。
- **最近公共祖先 LCA**:DFS 后序,左右子都找到就返回当前节点。
- **层平均 / 层最大**:BFS 按层累加除以 size。
- **完全二叉树判断**:BFS 遇到第一个空节点后,后续应全空。
- **从前序+中序 / 后序+中序 构造树**:前序定根,中序切左右子数组,递归。

### 已使用本模板的题目
- `2604/src/Tree/ 二叉树的右视图.kt` - BFS 层序(取每层最后) + DFS 右优先对拍
- `2604/src/Tree/Tree.kt` - 前/中/后序 的递归与迭代版
- `2604/src/Tree/遍历二叉树.kt` - 遍历主题练习
