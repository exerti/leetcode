class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
    val dummy = ListNode(0).apply { next = head }
    var pre: ListNode? = dummy
    // 1. pre 走到 left 前一个
    repeat(left - 1) { pre = pre?.next }
    val start = pre?.next
    var then = start?.next
    // 2. 头插法反转 [left, right]
    repeat(right - left) {
        start?.next = then?.next
        then?.next = pre?.next
        pre?.next = then
        then = start?.next
    }
    return dummy.next
}
