package ListNode


fun removeElements2(head: ListNode?, `val`: Int): ListNode? {
    val dummy = ListNode(-1)
    dummy.next = head
    var next = dummy

    while (next.next != null) {
        if (next.next!!.`val` == `val`) {
            next.next = next.next!!.next
        } else {
            next = next.next!!
        }
    }

    return dummy.next
}
