package `stack&queue`

import ListNode.ListNode
import java.util.PriorityQueue

/**
 * 链表1: 1 → 4 → 5
 * 链表2: 1 → 3 → 4
 * 链表3: 2 → 6
 *
 * 初始：三个链表头入堆 → 堆 [1, 1, 2]
 *      弹出 1（链表1的头）
 *      链表1的头变成 4，把 4 入堆 → 堆 [1, 2, 4]
 */


/**
 * 核心就四步，对应代码逐行看：
 *
 *
 * 1. 建堆：按 val 排序的小顶堆
 * 2. 所有非空链表头入堆
 * 3. 循环：poll 最小的 → 接到 tail 后面 → 该节点有 next 就入堆
 * 4. dummy.next 就是结果
 */
fun mergeKLists(lists: Array<ListNode?>): ListNode? {
    val heap = PriorityQueue<ListNode>(compareBy { it.`val` })

    for (head in lists) {
        if (head != null) heap.offer(head)
    }

    val dummy = ListNode(0)
    var tail: ListNode = dummy

    while (heap.isNotEmpty()) {
        val min = heap.poll()
        tail.next = min
        tail = min
        if (min.next != null) {
            heap.offer(min.next)
        }
    }



    return dummy.next
}