package linkList

class LRU(private val capacity: Int) {
    private class Node(val key: Int, var value: Int) {
        var prev: Node? = null
        var next: Node? = null
    }

    private val cache = mutableMapOf<Int, Node>()
    private val head = Node(0, 0)
    private val tail = Node(0, 0)

    init {
        head.next = tail
        tail.prev = head
    }

    fun get(key: Int): Int {
        val node = cache[key] ?: return -1
        moveToHead(node)
        return node.value
    }

    fun put(key: Int, value: Int) {
        val node = cache[key]
        if (node != null) {
            node.value = value
            moveToHead(node)
        } else {
            val newNode = Node(key, value)
            cache[key] = newNode
            addToHead(newNode)
            if (cache.size > capacity) {
                val removed = removeTail()
                cache.remove(removed.key)
            }
        }
    }

    private fun addToHead(node: Node) {
        node.prev = head
        node.next = head.next
        head.next?.prev = node
        head.next = node
    }

    private fun removeNode(node: Node) {
        node.prev?.next = node.next
        node.next?.prev = node.prev
    }

    private fun moveToHead(node: Node) {
        removeNode(node)
        addToHead(node)
    }

    private fun removeTail(): Node {
        val node = tail.prev!!
        removeNode(node)
        return node
    }


}


class MyLRU(private val capacity: Int) {
   private class Node(val key: Int, var value: Int) {
        var prev: Node? = null
        var next: Node? = null
    }

    private val cache = mutableMapOf<Int, Node>()
    private val head = Node(0, 0)
    private val tail = Node(0, 0)

    init {
        head.next = tail
        tail.prev = head
    }


    ///get

    fun get(key: Int): Int {
        val node = cache[key] ?: return -1
        moveToHead(node)
        return node.value
    }

    fun put(key: Int, value: Int) {
        var node = cache[key]
        if (node != null) {
            node.value = value
            moveToHead(node)
        } else {
            var newNode = Node(key, value)
            cache[key] = newNode
            addToHead(newNode)
            if (cache.size > capacity) {
                var tail = removeTail()
                cache.remove(tail.key)
            }
        }
    }

    private fun moveToHead( node: Node) {
        remove(node)
        addToHead(node)
    }

    private fun remove( node: Node) {
        node.prev?.next = node.next
        node.next?.prev = node.prev
    }


    private fun removeTail(): Node {
        val node = tail.prev!!
        remove(node)
        return node
    }


    private  fun addToHead(node: Node) {
        node.prev = head
        node.next = head.next?.next
        head.next?.prev = node
        head.next = node
    }
}