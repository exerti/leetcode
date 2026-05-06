package LRU

class TTLLruCache<K, V>(private val capacity: Int) {

    private data class Node<K, V>(
        val key: K,
        var value: V,
        var expireAt: Long,             // 绝对过期时间，毫秒时间戳
        var prev: Node<K, V>? = null,
        var next: Node<K, V>? = null
    )

    private val map = HashMap<K, Node<K, V>>()
    private val head: Node<K, V> = Node(null as K, null as V, Long.MAX_VALUE)
    private val tail: Node<K, V> = Node(null as K, null as V, Long.MAX_VALUE)

    init {
        head.next = tail
        tail.prev = head
    }

    // ---------- 公开方法 ----------

    fun get(key: K): V? {
        val node = map[key] ?: return null
        if (isExpired(node)) {
            removeNode(node)
            map.remove(node.key)
            return null
        }
        moveToHead(node)
        return node.value
    }

    fun put(key: K, value: V, ttlMs: Long) {
        val now = currentTimeMs()
        val existing = map[key]
        if (existing != null) {
            existing.value = value
            existing.expireAt = now + ttlMs
            moveToHead(existing)
            return
        }
        // 新节点
        val node = Node(key, value, now + ttlMs)
        map[key] = node
        addToHead(node)
        // 容量满了逐出
        if (map.size > capacity) {
            evict()
        }
    }

    fun remove(key: K): V? {
        val node = map.remove(key) ?: return null
        removeNode(node)
        return node.value
    }

    fun size(): Int = map.size

    fun clear() {
        map.clear()
        head.next = tail
        tail.prev = head
    }

    // ---------- 内部 ----------

    private fun evict() {
        // 从尾部向前扫，优先踢已过期的
        var cur = tail.prev
        while (cur !== head && map.size > capacity) {
            val next = cur!!.prev
            if (isExpired(cur)) {
                removeNode(cur)
                map.remove(cur.key)
            }
            cur = next
        }
        // 如果还没降到容量以内，按 LRU 踢尾部
        while (map.size > capacity) {
            val last = tail.prev
            if (last === head) break
            removeNode(last!!)
            map.remove(last.key)
        }
    }

    private fun isExpired(node: Node<K, V>): Boolean {
        return currentTimeMs() >= node.expireAt
    }

    private fun removeNode(node: Node<K, V>) {
        node.prev?.next = node.next
        node.next?.prev = node.prev
    }

    private fun addToHead(node: Node<K, V>) {
        node.prev = head
        node.next = head.next
        head.next?.prev = node
        head.next = node
    }

    private fun moveToHead(node: Node<K, V>) {
        removeNode(node)
        addToHead(node)
    }

    // 抽出来方便测试时 mock 时间
    private fun currentTimeMs(): Long = System.currentTimeMillis()
}
