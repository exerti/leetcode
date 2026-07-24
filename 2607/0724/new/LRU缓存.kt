package new

/**
 * LRU 缓存 (LeetCode 146)
 * 设计一个满足 LRU (最近最少使用) 缓存约束的数据结构。
 *
 * 关键要求：get 和 put 都是 O(1)
 */

class LRUCache(val capacity: Int) {

    // =====================================================================
    // 数据结构设计引导
    // =====================================================================
    /**
     * ▎需求分析
     *   1. get(key) → O(1) 查值 → HashMap/HashSet
     *   2. put(key, value) → O(1) 插入/更新
     *   3. 容量满时淘汰"最久未使用"的 → 需要维护访问顺序
     *
     * ▎为什么单独 HashMap 不够？
     *   HashMap 可以 O(1) 存取，但无法知道"谁最久未使用"
     *   需要额外数据结构维护顺序
     *
     * ▎什么数据结构能维护顺序且 O(1) 删除任意位置？
     *   数组：删除 O(n)
     *   队列：只能删头尾
     *   链表：O(1) 删除任意节点（前提：有该节点的引用）
     *
     * ▎所以答案是？
     *   HashMap<Key, Node> + 双向链表
     *   HashMap → O(1) 找到节点
     *   双向链表 → O(1) 删除/插入（头尾操作）
     *
     * ▎链表怎么组织？
     *   - 最近使用的放在链表头部
     *   - 最久未使用的在链表尾部
     *   - get 命中 → 把该节点移到头部
     *   - put 新数据 → 插入头部；如果满了 → 删尾部
     *
     * ▎为什么用双向链表而非单向？
     *   删除中间节点时，需要知道前驱节点
     *   双向链表删除：node.prev.next = node.next （O(1)）
     *   单向链表删除：需要从头遍历找前驱 （O(n)）
     *
     * ▎虚拟头尾节点（dummy head/tail）
     *   避免处理 null 边界
     *   链表永远非空，简化代码
     *
     * ▎手写推演 capacity=2
     *   put(1,1): map={1→...}, 链表: [head↔1↔tail]
     *   put(2,2): map={1→...,2→...}, 链表: [head↔2↔1↔tail]
     *   get(1):   map={1→...,2→...}, 链表: [head↔1↔2↔tail]  ← 1移到头部
     *   put(3,3): map={1→...,3→...}, 链表: [head↔3↔1↔tail]  ← 删尾部2，插入3
     */

    // TODO: 定义内部 Node 类
    // TODO: 实现 get(key): Int
    // TODO: 实现 put(key: Int, value: Int)

    fun get(key: Int): Int {
        TODO()
    }

    fun put(key: Int, value: Int) {
        TODO()
    }
}

// =====================================================================
// 变体思考
// =====================================================================
/**
 * =====================================================================
 * 变体1：LFU 缓存 (LeetCode 460)
 * =====================================================================
 * ▎LRU vs LFU 的区别
 *   LRU: 淘汰"最久没被访问"的 → 时间维度
 *   LFU: 淘汰"访问次数最少"的  → 频率维度
 *
 * ▎LFU 的难点在哪？
 *   LRU 只需要一个维度（时间顺序），双向链表一条线搞定。
 *   LFU 多了"频率"维度，数据结构变成二维：
 *     - 外层：频率 → 该频率下的节点集合
 *     - 内层：同一频率内，按时间排序（LRU）
 *
 * ▎LFU 的核心数据结构
 *   HashMap<Key, Node>          → O(1) 查节点
 *   HashMap<Freq, LinkedHashSet> → 每个频率对应一组节点（保持插入顺序）
 *   minFreq                      → 记录当前最小频率，淘汰时 O(1) 定位
 *
 * ▎LFU 的操作逻辑
 *   get(key):
 *     1. 不存在 → 返回 -1
 *     2. 存在 → 频率+1，从旧频率集合移除，加入新频率集合
 *   put(key, value):
 *     1. 存在 → 更新值，频率+1（同 get 的步骤2）
 *     2. 不存在，没满 → 新建节点，频率=1，加入 freq=1 集合
 *     3. 不存在，满了 → 淘汰 minFreq 集合中最旧的节点，再插入新节点
 *
 * ▎为什么需要 minFreq？
 *   淘汰时要找"频率最低的节点"。如果每次都遍历所有频率 → O(n)
 *   维护 minFreq 变量，新增节点时置 1，某个频率集合变空时更新 → O(1)
 *
 * ▎手写推演 capacity=2
 *   put(1,1): freq[1]={1}, minFreq=1
 *   put(2,2): freq[1]={1,2}, minFreq=1
 *   get(1):   1 频率 1→2: freq[1]={2}, freq[2]={1}, minFreq=1
 *   put(3,3): 满了→淘汰 minFreq=1 中最旧的(2), freq[1]={3}, minFreq=1
 *   get(3):   3 频率 1→2: freq[1] 空了→minFreq=2, freq[2]={1,3}
 *
 * =====================================================================
 * 变体2：LinkedHashMap 实现 LRU
 * =====================================================================
 * ▎LinkedHashMap 是什么？
 *   HashMap + 双向链表，既能 O(1) 存取，又能维护插入/访问顺序。
 *   Kotlin 中对应的是 LinkedHashMap。
 *
 * ▎怎么用它实现 LRU？
 *   重写 removeEldestEntry 方法：
 *     - 返回 true → 自动删除最旧的条目
 *     - 条件：size() > capacity
 *
 *   Kotlin 示例：
 *   ```
 *   val cache = object : LinkedHashMap<Int, Int>(capacity, 0.75f, true) {
 *       override fun removeEldestEntry(eldest: Map.Entry<Int, Int>?): Boolean {
 *           return size > capacity
 *       }
 *   }
 *   ```
 *
 * ▎构造参数 (accessOrder = true) 的含义：
 *   false → 按插入顺序（先插入的算旧）
 *   true  → 按访问顺序（get/put 后会移到末尾，没访问的算旧）← LRU 用这个
 *
 * ▎面试用 LinkedHashMap 会不会太取巧？
 *   通常考官期望你自己实现（双向链表 + HashMap），但提一句
 *   "标准库有 LinkedHashMap 可以一行搞定"能体现知识广度。
 */


class Node<K, V>(val key: K, var value: V) {
    var next: Node<K, V>? = null
    var prev: Node<K, V>? = null
}

class LruCacheImpl<K, V>(val capacity: Int) {

    private val map = HashMap<K, Node<K, V>>()
    private val head = Node<K, V>(null as K, null as V)
    private val tail = Node<K, V>(null as K, null as V)

    init {
        head.next = tail
        tail.prev = head
    }

    fun get(key: K): V? {
        val node = map[key] ?: return null
        moveToHead(node)
        return node.value
    }

    fun put(key: K, value: V) {
        val exist = map[key]
        if (exist != null) {
            exist.value = value
            moveToHead(exist)
        } else {
            val newNode = Node(key, value)
            map[key] = newNode
            addToHead(newNode)
            if (map.size > capacity) {
                removeTail()
            }
        }
    }

    private fun addToHead(node: Node<K, V>) {
        node.next = head.next
        head.next?.prev = node
        head.next = node
        node.prev = head
    }

    private fun removeNode(node: Node<K, V>) {
        node.prev?.next = node.next
        node.next?.prev = node.prev
    }

    private fun moveToHead(node: Node<K, V>) {
        removeNode(node)
        addToHead(node)
    }

    private fun removeTail() {
        val last = tail.prev!!
        removeNode(last)
        map.remove(last.key)
    }
}