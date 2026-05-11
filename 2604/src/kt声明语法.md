# Kotlin 常用声明方式速查

## 1. 变量声明

```kotlin
val x: Int = 10        // 不可变(推荐),类似 Java final
var y: Int = 10        // 可变
val z = 10             // 类型推断

val name: String? = null   // 可空类型(加 ?)
```

## 2. 数组

```kotlin
val a = IntArray(5)                 // [0,0,0,0,0]
val b = intArrayOf(1, 2, 3)         // IntArray
val c = arrayOf("a", "b", "c")      // Array<String>
val d = Array(3) { it * 2 }         // [0,2,4]

val matrix = Array(3) { IntArray(4) }   // 3x4 二维数组
```

## 3. List / 列表

```kotlin
val list1: List<Int> = listOf(1, 2, 3)              // 不可变
val list2 = mutableListOf<Int>()                     // 可变,空
val list3 = mutableListOf(1, 2, 3)                   // 可变,有初值
val list4 = ArrayList<Int>()                         // 等价 mutableListOf
```

常用方法:`add`, `removeAt`, `get` / `[]`, `size`, `indices`, `isEmpty()`

### 3.1 二维可变列表

```kotlin
// 空的二维可变列表(最常用)
val g1: MutableList<MutableList<Int>> = mutableListOf()

// 有初值
val g2 = mutableListOf(
    mutableListOf(1, 2, 3),
    mutableListOf(4, 5, 6)
)

// 指定 rows x cols,填 0
val rows = 3; val cols = 4
val g3 = MutableList(rows) { MutableList(cols) { 0 } }

// 等价写法
val g4 = ArrayList<ArrayList<Int>>()
```

常用操作:

```kotlin
g1.add(mutableListOf(1, 2))   // 加一行
g1[0].add(3)                  // 第 0 行末尾加元素
g1[0][1] = 9                  // 修改某格
```

DFS/回溯场景:加结果时**要拷贝一份**,避免后续回溯修改。

```kotlin
val ans = mutableListOf<MutableList<Int>>()
ans.add(ArrayList(path))      // path 是回溯中的可变列表
```

## 4. Set / 集合

```kotlin
val s1: Set<Int> = setOf(1, 2, 3)
val s2 = mutableSetOf<Int>()
val s3 = HashSet<Int>()
```

## 5. Map / 哈希表

```kotlin
val m1: Map<Int, String> = mapOf(1 to "a", 2 to "b")   // 不可变
val m2 = mutableMapOf<Int, String>()                    // 可变
val m3 = HashMap<Int, Int>()                            // 常用于算法题

m3[1] = 100                    // 插入/更新
m3.containsKey(1)              // 判断
val v = m3[1]                  // 取值,返回 Int? (可空)
val v2 = m3.getOrDefault(1, 0) // 取值带默认
m3.remove(1)
for ((k, v) in m3) { /* ... */ }
```

## 6. 栈 / 队列

```kotlin
val stack = ArrayDeque<Int>()     // 栈: addLast / removeLast / lastOrNull
val queue = ArrayDeque<Int>()     // 队列: addLast / removeFirst / firstOrNull
```

## 7. 堆 / 优先队列

Kotlin 没有独立的堆类型,用 Java 的 `PriorityQueue`,默认是**小顶堆**。

```kotlin
import java.util.PriorityQueue

// 小顶堆(默认):堆顶是最小值
val minHeap = PriorityQueue<Int>()
minHeap.offer(3); minHeap.offer(1); minHeap.offer(2)
minHeap.peek()      // 1,查看堆顶不弹出
minHeap.poll()      // 1,弹出堆顶
minHeap.size
minHeap.isEmpty()

// 大顶堆:传比较器倒序
val maxHeap = PriorityQueue<Int>(compareByDescending { it })
// 等价写法
val maxHeap2 = PriorityQueue<Int> { a, b -> b - a }

// 按对象属性建堆:比如按 freq 升序
data class Item(val v: Int, val freq: Int)
val h1 = PriorityQueue<Item>(compareBy { it.freq })

// 多字段排序:freq 升序,v 降序
val h2 = PriorityQueue<Item>(
    compareBy<Item> { it.freq }.thenByDescending { it.v }
)

// 指定初始容量
val h3 = PriorityQueue<Int>(16)
```

常用方法:`offer/add` 入堆,`poll` 弹堆顶,`peek` 看堆顶,`remove(e)` 删指定元素 O(n)。

## 8. 链表

```kotlin
val ll = java.util.LinkedList<Int>()
ll.addFirst(0); ll.addLast(3)
ll.first; ll.last
```

## 9. 字符串

```kotlin
val s = "hello"
s.length
s[0]                    // 'h'
s.substring(1, 3)       // "el"
val sb = StringBuilder()
sb.append('a').append("bc")
sb.toString()
```

## 9. 函数声明

```kotlin
fun add(a: Int, b: Int): Int = a + b          // 表达式体
fun greet(name: String = "world"): String {   // 默认参数
    return "hi, $name"
}
```

## 10. 类 / 数据类

```kotlin
class Node(var value: Int, var next: Node? = null)

data class Point(val x: Int, val y: Int)      // 自动 equals/hashCode/toString
```

## 11. 常见算法返回类型

```kotlin
return intArrayOf()                // 空 IntArray
return intArrayOf(a, b)            // 构造 IntArray
return emptyList()                 // 空 List
return mutableListOf<Int>()        // 空 MutableList
```

## 12. 可空与断言

```kotlin
val x: Int? = map[key]
val y = x ?: 0                     // Elvis: 为 null 用默认
val z = x!!                        // 断言非空(null 会抛 NPE)
x?.let { /* 非空才执行 */ }
```

## 13. 区间与遍历

```kotlin
for (i in 0..n)        // 闭区间 [0, n]
for (i in 0 until n)   // 半开 [0, n)
for (i in n downTo 0)  // 递减
for (i in 0..n step 2) // 步长
for (i in arr.indices) // 下标
for ((i, v) in arr.withIndex())
```

## 14. 自己实现数据结构

### 14.1 单链表

```kotlin
class ListNode(var `val`: Int, var next: ListNode? = null)

// 头插
fun addFirst(head: ListNode?, v: Int): ListNode {
    return ListNode(v, head)
}

// 遍历
var cur: ListNode? = head
while (cur != null) {
    println(cur.`val`)
    cur = cur.next
}
```

### 14.2 双向链表(LRU/LFU 常用)

```kotlin
class DNode(var key: Int = 0, var value: Int = 0) {
    var prev: DNode? = null
    var next: DNode? = null
}

class DList {
    private val head = DNode()
    private val tail = DNode()
    init { head.next = tail; tail.prev = head }

    fun addFirst(node: DNode) {
        node.prev = head
        node.next = head.next
        head.next?.prev = node
        head.next = node
    }

    fun remove(node: DNode) {
        node.prev?.next = node.next
        node.next?.prev = node.prev
    }

    fun removeLast(): DNode? {
        val node = tail.prev
        if (node === head) return null
        remove(node!!)
        return node
    }
}
```

### 14.3 栈 / 队列(基于数组)

```kotlin
class MyStack<T> {
    private val data = ArrayList<T>()
    fun push(x: T) { data.add(x) }
    fun pop(): T = data.removeAt(data.size - 1)
    fun peek(): T = data[data.size - 1]
    fun isEmpty() = data.isEmpty()
    val size get() = data.size
}

// 环形队列
class MyQueue(capacity: Int) {
    private val data = IntArray(capacity)
    private var head = 0
    private var tail = 0
    private var count = 0
    fun offer(x: Int): Boolean {
        if (count == data.size) return false
        data[tail] = x
        tail = (tail + 1) % data.size
        count++
        return true
    }
    fun poll(): Int? {
        if (count == 0) return null
        val x = data[head]
        head = (head + 1) % data.size
        count--
        return x
    }
}
```

### 14.4 HashMap(拉链法简化版)

```kotlin
class MyHashMap<K, V> {
    private data class Entry<K, V>(val key: K, var value: V, var next: Entry<K, V>? = null)
    private val buckets = arrayOfNulls<Entry<K, V>>(1024)

    private fun idx(key: K) = (key.hashCode() and 0x7fffffff) % buckets.size

    fun put(key: K, value: V) {
        val i = idx(key)
        var e = buckets[i]
        while (e != null) {
            if (e.key == key) { e.value = value; return }
            e = e.next
        }
        buckets[i] = Entry(key, value, buckets[i])
    }

    fun get(key: K): V? {
        var e = buckets[idx(key)]
        while (e != null) {
            if (e.key == key) return e.value
            e = e.next
        }
        return null
    }
}
```

## 15. 手写堆(二叉堆)

数组实现,索引关系:父 `(i-1)/2`,左子 `2i+1`,右子 `2i+2`。

```kotlin
class MinHeap {
    private val data = ArrayList<Int>()
    val size get() = data.size
    fun isEmpty() = data.isEmpty()
    fun peek(): Int = data[0]

    fun offer(x: Int) {
        data.add(x)
        siftUp(data.size - 1)
    }

    fun poll(): Int {
        val top = data[0]
        val last = data.removeAt(data.size - 1)
        if (data.isNotEmpty()) {
            data[0] = last
            siftDown(0)
        }
        return top
    }

    private fun siftUp(start: Int) {
        var i = start
        while (i > 0) {
            val p = (i - 1) / 2
            if (data[i] >= data[p]) break
            swap(i, p); i = p
        }
    }

    private fun siftDown(start: Int) {
        var i = start
        val n = data.size
        while (true) {
            val l = 2 * i + 1
            val r = 2 * i + 2
            var smallest = i
            if (l < n && data[l] < data[smallest]) smallest = l
            if (r < n && data[r] < data[smallest]) smallest = r
            if (smallest == i) break
            swap(i, smallest); i = smallest
        }
    }

    private fun swap(a: Int, b: Int) {
        val t = data[a]; data[a] = data[b]; data[b] = t
    }

    // 批量建堆 O(n)
    fun heapify(arr: IntArray) {
        data.clear()
        data.addAll(arr.toList())
        for (i in data.size / 2 - 1 downTo 0) siftDown(i)
    }
}
```

**大顶堆**:把上面两处比较 `<` 改成 `>` 即可(或直接把入堆元素取负存小顶堆)。

**泛型 + 比较器版本**:

```kotlin
class Heap<T>(private val cmp: Comparator<T>) {
    private val data = ArrayList<T>()
    val size get() = data.size
    fun peek(): T = data[0]
    fun offer(x: T) { data.add(x); siftUp(data.size - 1) }
    fun poll(): T {
        val top = data[0]
        val last = data.removeAt(data.size - 1)
        if (data.isNotEmpty()) { data[0] = last; siftDown(0) }
        return top
    }
    private fun siftUp(s: Int) {
        var i = s
        while (i > 0) {
            val p = (i - 1) / 2
            if (cmp.compare(data[i], data[p]) >= 0) break
            val t = data[i]; data[i] = data[p]; data[p] = t; i = p
        }
    }
    private fun siftDown(s: Int) {
        var i = s; val n = data.size
        while (true) {
            val l = 2 * i + 1; val r = 2 * i + 2; var best = i
            if (l < n && cmp.compare(data[l], data[best]) < 0) best = l
            if (r < n && cmp.compare(data[r], data[best]) < 0) best = r
            if (best == i) break
            val t = data[i]; data[i] = data[best]; data[best] = t; i = best
        }
    }
}

// 用法
val minH = Heap<Int>(Comparator { a, b -> a - b })
val maxH = Heap<Int>(Comparator { a, b -> b - a })
```

## 16. 排序与比较器

### 16.1 基本排序

```kotlin
// IntArray 原地排序(升序)
val a = intArrayOf(3, 1, 2)
a.sort()                      // [1,2,3]
a.sortDescending()            // [3,2,1]

// List 返回新列表
val list = listOf(3, 1, 2)
val asc = list.sorted()                  // [1,2,3]
val desc = list.sortedDescending()       // [3,2,1]

// MutableList 原地
val ml = mutableListOf(3, 1, 2)
ml.sort()
```

### 16.2 按属性排序

```kotlin
data class Person(val name: String, val age: Int)
val ps = listOf(Person("a", 30), Person("b", 20))

ps.sortedBy { it.age }                   // 升序
ps.sortedByDescending { it.age }         // 降序
```

### 16.3 多字段排序

```kotlin
// age 升序,age 相同时 name 降序
ps.sortedWith(
    compareBy<Person> { it.age }.thenByDescending { it.name }
)

// 二维数组:先按第 0 列升序,再按第 1 列降序
val arr = arrayOf(intArrayOf(1,5), intArrayOf(1,3), intArrayOf(2,4))
arr.sortWith(
    compareBy<IntArray> { it[0] }.thenByDescending { it[1] }
)

// 等价 lambda 形式(注意减法可能溢出,大数用 compareTo)
arr.sortWith { x, y ->
    if (x[0] != y[0]) x[0] - y[0] else y[1] - x[1]
}

// 大数安全写法
arr.sortWith { x, y ->
    if (x[0] != y[0]) x[0].compareTo(y[0]) else y[1].compareTo(x[1])
}
```

### 16.4 Comparator 构造工具

```kotlin
// 自然顺序
val c1: Comparator<Int> = naturalOrder()
val c2: Comparator<Int> = reverseOrder()

// 按 key 提取
val c3 = compareBy<Person> { it.age }

// 链式
val c4 = compareBy<Person> { it.age }
    .thenBy { it.name }
    .thenByDescending { it.name.length }

// Kotlin 的比较器传给 Java 的 PriorityQueue
val pq = java.util.PriorityQueue<Person>(compareBy { it.age })
```

### 16.5 字符串排序

```kotlin
val words = listOf("banana", "apple", "cherry")
words.sorted()                                       // 字典序
words.sortedBy { it.length }                         // 按长度
words.sortedWith(compareBy({ it.length }, { it }))   // 先长度再字典
```

### 16.6 稳定性与原地/非原地区别

- Kotlin 的 `sort*` 基于 Java `Arrays.sort` / `Collections.sort`:对象类型**稳定**,基本类型 `IntArray.sort` 用快排**不稳定**。
- `sorted / sortedBy / sortedWith` 返回**新 List**;`sort / sortBy / sortWith` **原地**修改。

