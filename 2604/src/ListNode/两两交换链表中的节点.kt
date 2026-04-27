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


/**
 * 22交换，
 */
fun solution(head: ListNode?): ListNode? {
    if(head==null){
        return head
    }
    var dummy = ListNode(0)
    dummy.next = head
    var pre = dummy
    while(pre.next!=null&&pre.next!=null){
        var first = pre.next!!
        var second = pre.next!!.next

        // pre -> A -> B -> C
        first.next = second!!.next  // 1. first 先链上后面的
        second.next = first       // 2. second 反过来链 first
        pre.next = second         // 3. 前驱节点指向 second
        pre = first               // 4. pre 移动到下一组的前驱
    }

    return dummy.next
}