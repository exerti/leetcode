package ListNode


//保存 赋值 前进
fun reverseList(head: ListNode?): ListNode? {
    var pre: ListNode? = null
    var cur: ListNode? = head
    while (cur != null) {
        var next = cur.next
        cur.next = pre
        pre = cur
        cur = next
    }
    return pre
}