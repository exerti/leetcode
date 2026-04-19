package ListNode


fun swapPairs(head: ListNode?): ListNode? {

    val dummy = ListNode(0)
    dummy.next= head
    var pre = dummy
    while (pre.next!=null&&pre.next!!.next!=null){
        val first = pre.next!!
        val second = pre.next!!.next

        //first second
        if(second!=null){
            first.next =second.next
        }
        second?.let { it .next = first}
        pre.next = second
        pre = first

    }

    return dummy.next
}