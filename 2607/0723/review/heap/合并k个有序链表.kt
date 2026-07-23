package review.heap

import java.util.PriorityQueue

class ListNode(var value: Int) {
    var next: ListNode? = null
}

class Solution {

    fun mergeKListNode(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) return null

        // 小顶堆，按节点值升序
        val minHeap = PriorityQueue<ListNode>(compareBy { it.value })
        // Kotlin lambda: { head -> ... }，不是 (head -> { ... })
        lists.forEach { head ->
            if (head != null) {
                minHeap.offer(head)
            }
        }

        val dummy = ListNode(0)
        var cur: ListNode = dummy
        while (minHeap.isNotEmpty()) {
            val node = minHeap.poll()        // poll 取出最小节点
            cur.next = node
            cur = node                        // 指针前进
            if (node.next != null) {
                minHeap.offer(node.next!!)
            }
        }

        return dummy.next
    }
}

fun main() {
    val testCase = arrayOf(
        ListNode(1).apply {
            next = ListNode(4).apply {
                ListNode(5)
            }
        },
        ListNode(1).apply {
            next = ListNode(3).apply {
                ListNode(4)
            }
        },
        ListNode(2).apply {
            next = ListNode(6)
        },

    ) as Array<ListNode?>



    val handler =  Solution()::mergeKListNode

    //打印链表

    var  head = handler(testCase)

    while(head!=null ){
        println(head.value)
        head=head.next

    }

}