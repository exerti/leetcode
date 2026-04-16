package ListNode

class 随机链表的复制 {
}

class RandomListNode(var `val`: Int) {
    var next: RandomListNode? = null
    var random: RandomListNode? = null
}

// 版本1：HashMap（O(n) 额外空间）
fun copyRandomList(node: RandomListNode?): RandomListNode? {
    if (node == null) return null

    val map = HashMap<RandomListNode, RandomListNode>()
    var cur: RandomListNode? = node

    while (cur != null) {
        map[cur] = RandomListNode(cur.`val`)
        cur = cur.next
    }

    cur = node
    while (cur != null) {
        val copy = map[cur]!!
        copy.next = cur.next?.let { map[it] }
        copy.random = cur.random?.let { map[it] }
        cur = cur.next
    }

    return map[node]
}

// 版本2：穿插节点（O(1) 额外空间）
fun copyRandomListInPlace(node: RandomListNode?): RandomListNode? {
    if (node == null) return null

    var cur: RandomListNode? = node
    while (cur != null) {
        val copy = RandomListNode(cur.`val`)
        copy.next = cur.next
        cur.next = copy
        cur = copy.next
    }

    cur = node
    while (cur != null) {
        cur.next?.random = cur.random?.next
        cur = cur.next?.next
    }

    cur = node
    val newHead = node.next
    while (cur != null) {
        val copy = cur.next
        cur.next = copy?.next
        copy?.next = copy?.next?.next
        cur = cur.next
    }

    return newHead
}

fun main() {
    // 原链表：1 -> 2
    // random: 1.random -> 2, 2.random -> 1
    val n1 = RandomListNode(1)
    val n2 = RandomListNode(2)
    n1.next = n2
    n1.random = n2
    n2.random = n1

    val copied = copyRandomListInPlace(n1)

    println("原头值=${n1.`val`}, 拷贝头值=${copied?.`val`}")
    println("头节点是否同一对象: ${n1 === copied}")
    println("next 节点是否同一对象: ${n1.next === copied?.next}")
    println("copy.random 是否指向原节点: ${copied?.random === n2}")
    println("copy.random 是否指向拷贝 next: ${copied?.random === copied?.next}")
}

