package ListNode

class `K 个一组翻转链表` {
}

fun reverseKGroup(head: ListNode?, k: Int): ListNode? {

// 1. 找到第k个节点，作为本次翻转的尾部
    var tail = head
    for (i in 0 until k) {
        if (tail == null) return head // 不足k个，不翻转，直接返回
        tail = tail.next
    }

    // 2. 翻转 [head ~ 第k个节点前] 这段链表
    val newHead = reverse(head, tail)

    // 3. 原来的头节点变成尾节点，递归拼接下一组翻转结果
    head?.next = reverseKGroup(tail, k)

    return newHead

}

fun reverse(head: ListNode?, tail: ListNode?): ListNode? {
    var pre: ListNode? = null
    var cur = head
    while (cur != tail) { // 只翻转到tail前为止
        val next = cur?.next
        cur?.next = pre
        pre = cur
        cur = next
    }
    return pre
}