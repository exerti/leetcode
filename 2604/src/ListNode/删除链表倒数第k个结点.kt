package ListNode


fun deleteNodeK(node: ListNode?, k: Int): ListNode? {
    if (node == null) return node
    var dummy = ListNode(-1)
    dummy.next = node
    var fast: ListNode? = dummy
    var slow: ListNode? = dummy
    repeat(k+1) {
        if (fast == null) return node
        fast = fast.next
    }
    while (fast != null) {
        fast = fast.next
        slow = slow?.next
    }
    slow?.let { it.next = slow.next?.next }

    return dummy.next
}

fun main() {
    val root1 = ListNode(3)
    root1.next = ListNode(2)
    root1.next!!.next = ListNode(1)
    root1.next!!.next!!.next = ListNode(4)

    printListNode(root1)
    deleteNodeK(root1, 2)
    printListNode(root1)
}