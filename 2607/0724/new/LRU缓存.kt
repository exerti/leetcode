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
 * ▎LFU 缓存 (LeetCode 460)
 *   LRU 按"时间"淘汰，LFU 按"频率"淘汰
 *   需要多维护一个频率维度
 *
 * ▎LinkedHashMap
 *   Java/Kotlin 标准库的 LinkedHashMap 天然支持 LRU
 *   了解它怎么实现的
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
        // 1. map 里找不到 → 返回 null
        // 2. 找到了 → 把节点移到头部，返回值
        TODO()
    }

    fun put(key: K, value: V) {
        // 1. key 已存在 → 更新值，移到头部
        // 2. key 不存在 → 新建节点，插入头部
        //    2a. 如果满了 → 删尾部节点，同时从 map 移除
        TODO()
    }

    // 辅助方法骨架（自己实现）：
    // private fun addToHead(node)    — 把 node 插到 head 后面
    // private fun removeNode(node)   — 从链表中断开 node
    // private fun moveToHead(node)   — removeNode + addToHead
    // private fun removeTail(): Node — 删 tail.prev 并返回
}