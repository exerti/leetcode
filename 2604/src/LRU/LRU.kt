package LRU

class LRU {
    //
}

//LRU（Least Recently Used，最近最少使用）是经典缓存淘汰算法：当缓存容量满时，优先删除最近最少使用的数据。


class LruCache<k, v>(private val capacity: Int) {

    //
    private val cache = LinkedHashMap<k, v>(capacity, 0.75f, true)

    operator fun get(key: k): v? {
        return cache[key]
    }

    operator fun set(key: k, v: v) {
        cache[key] = v
        if (cache.size > capacity) {
            val oldestKey = cache.entries.iterator().next().key
            cache.remove(oldestKey)
        }
    }


    fun clear() = cache.clear()
    fun size() = cache.size

}

fun main() {
    val lru = LruCache<String, Int>(3)
    lru["a"] = 1
    lru["b"] = 2
    lru["c"] = 3
    println(lru.get("a")) // 1（访问后 a 移到头部）

    lru["d"] = 4 // 容量满，删除 b
    println(lru.get("b")) // null（已被淘汰）
    println(lru.get("d")) // 3
}