package ListNode

///“先数 k，不够回；够了先递归；翻当前；尾接后；返新头。”
fun kReverse(head: ListNode?, k: Int): ListNode? {
    if (head == null || k <= 1) {
        return head
    }

    var p = head
    repeat(k) {
        if (p == null) {
            return head
        }
        p = p!!.next
    }

    val segment = kReverse(p, k)
    val reversed = reverseK(head, k)
    head.next = segment
    return reversed
}

fun reverseK(head: ListNode?, k: Int): ListNode? {
    var pre: ListNode? = null
    var cur = head

    repeat(k) {
        val next = cur?.next
        cur?.next = pre
        pre = cur
        cur = next
    }

    return pre
}


fun main() {
    val root1 = ListNode(3)
    root1.next = ListNode(2)
    root1.next!!.next = ListNode(1)
    root1.next!!.next!!.next = ListNode(4)

    val root2 = ListNode(3)
    root2.next = ListNode(2)
    root2.next!!.next = ListNode(1)
    root2.next!!.next!!.next = ListNode(4)

    print("原链表(递归): ")
    printListNode(root1)
    val r1 = kReverse(root1, 3)
    print("递归结果    : ")
    printListNode(r1)

    print("原链表(迭代): ")
    printListNode(root2)
    val r2 = kReverseIterative(root2, 3)
    print("迭代结果    : ")
    printListNode(r2)
}

fun kReverseIterative(head: ListNode?, k: Int): ListNode? {
    if (head == null || k <= 1) return head

    val dummy = ListNode(0)
    dummy.next = head
    var pre: ListNode = dummy

    while (true) {
        // 1) 找到这一组的 tail（第 k 个）
        var tail: ListNode? = pre
        repeat(k) { tail = tail?.next }
        if (tail == null) break  // 不足 k 个，结束

        // 2) 翻转 [pre.next, tail] 这段
        val nextGroupHead = tail!!.next
        var prev: ListNode? = nextGroupHead
        var cur = pre.next
        while (cur !== nextGroupHead) {
            val tmp = cur!!.next
            cur.next = prev
            prev = cur
            cur = tmp
        }

        // 3) 接回去，并移动 pre 到下一组前面
        val start = pre.next!!   // 翻转前的头，翻转后变尾
        pre.next = tail          // 翻转后的新头
        pre = start
    }

    return dummy.next
}
