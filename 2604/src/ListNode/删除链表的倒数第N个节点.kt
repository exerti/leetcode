package ListNode

class 删除链表的倒数第N个节点 {
}

fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {

    //最只管的做法是2次便利
    //一次遍历使用快慢指针的方式
    val dummy = ListNode(-1)
    dummy.next = head
    var slow: ListNode? = dummy
    var fast: ListNode? = dummy
    var step = n + 1
    while (step-- > 0) {
        if (fast != null) {
            fast = fast.next
        }
    }
    while (fast != null) {
        fast = fast.next
        slow = slow?.next
    }

    slow?.let { it.next = slow.next?.next }

    return dummy.next

}