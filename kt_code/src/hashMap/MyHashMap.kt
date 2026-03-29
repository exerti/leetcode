/**
 * 手写 HashMap - 使用数组 + 链表（链地址法解决哈希冲突）
 */
class MyHashMap<K, V> {
    // 链表节点
    private class Node<K, V>(
        val key: K,
        var value: V,
        var next: Node<K, V>? = null
    )

    private var buckets: Array<Node<K, V>?> = arrayOfNulls(16)  // 初始容量 16
    private var size = 0
    private val loadFactor = 0.75  // 负载因子

    /**
     * 计算哈希值并映射到桶索引
     */
    private fun hash(key: K): Int {
        val h = key.hashCode()
        return (h xor (h ushr 16)) and (buckets.size - 1)  // 扰动函数 + 取模
    }

    /**
     * 添加或更新键值对
     */
    fun put(key: K, value: V) {
        // 检查是否需要扩容
        if (size >= buckets.size * loadFactor) {
            resize()
        }

        val index = hash(key)
        var node = buckets[index]

        // 桶为空，直接插入
        if (node == null) {
            buckets[index] = Node(key, value)
            size++
            return
        }

        // 遍历链表，查找是否已存在该 key
        var prev: Node<K, V>? = null
        while (node != null) {
            if (node.key == key) {
                node.value = value  // 更新值
                return
            }
            prev = node
            node = node.next
        }

        // key 不存在，插入到链表尾部
        prev?.next = Node(key, value)
        size++
    }

    /**
     * 获取值
     */
    fun get(key: K): V? {
        val index = hash(key)
        var node = buckets[index]

        while (node != null) {
            if (node.key == key) {
                return node.value
            }
            node = node.next
        }
        return null
    }

    /**
     * 删除键值对
     */
    fun remove(key: K): V? {
        val index = hash(key)
        var node = buckets[index]
        var prev: Node<K, V>? = null

        while (node != null) {
            if (node.key == key) {
                val oldValue = node.value
                if (prev == null) {
                    // 删除的是链表头节点
                    buckets[index] = node.next
                } else {
                    prev.next = node.next
                }
                size--
                return oldValue
            }
            prev = node
            node = node.next
        }
        return null
    }

    /**
     * 扩容：容量翻倍，重新哈希所有元素
     */
    private fun resize() {
        val oldBuckets = buckets
        buckets = arrayOfNulls(oldBuckets.size * 2)
        size = 0

        for (head in oldBuckets) {
            var node = head
            while (node != null) {
                put(node.key, node.value)  // 重新插入
                node = node.next
            }
        }
    }

    /**
     * 判断是否包含 key
     */
    fun containsKey(key: K): Boolean = get(key) != null

    /**
     * 获取大小
     */
    fun size(): Int = size

    /**
     * 是否为空
     */
    fun isEmpty(): Boolean = size == 0

    /**
     * 打印内部结构（调试用）
     */
    fun printStructure() {
        println("HashMap size: $size, buckets: ${buckets.size}")
        buckets.forEachIndexed { index, head ->
            if (head != null) {
                print("Bucket[$index]: ")
                var node = head
                while (node != null) {
                    print("(${node.key}=${node.value}) -> ")
                    node = node.next
                }
                println("null")
            }
        }
    }
}

// 测试代码
fun main() {
    val map = MyHashMap<String, Int>()

    // 插入数据
    map.put("apple", 1)
    map.put("banana", 2)
    map.put("cherry", 3)
    map.put("apple", 10)  // 更新

    println("get(apple): ${map.get("apple")}")  // 10
    println("get(banana): ${map.get("banana")}")  // 2
    println("size: ${map.size()}")  // 3

    // 删除
    map.remove("banana")
    println("After remove banana, size: ${map.size()}")  // 2

    // 触发扩容
    for (i in 1..20) {
        map.put("key$i", i)
    }

    map.printStructure()
}
