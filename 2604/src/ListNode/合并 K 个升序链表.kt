import ListNode.ListNode
import java.util.PriorityQueue

// 解法 1:分治两两合并 O(N log k),额外空间 O(1)
fun mergeKLists(lists: Array<ListNode?>): ListNode? {
    if (lists.isEmpty()) return null

    var interval = 1
    while (interval < lists.size) {
        for (i in 0 until lists.size - interval step interval * 2) {
            lists[i] = mergeTwo(lists[i], lists[i + interval])
        }
        interval *= 2
    }

    return lists[0]
}

// 解法 2:小顶堆 O(N log k),额外空间 O(k)
fun mergeKListsByHeap(lists: Array<ListNode?>): ListNode? {
    if (lists.isEmpty()) return null

    val pq = PriorityQueue<ListNode> { a, b -> a.`val` - b.`val` }
    for (head in lists) {
        if (head != null) pq.offer(head)
    }

    val dummy = ListNode(0)
    var tail: ListNode = dummy
    while (pq.isNotEmpty()) {
        val node = pq.poll()
        tail.next = node
        tail = node
        if (node.next != null) pq.offer(node.next)
    }

    return dummy.next
}

fun buildListK(values: IntArray): ListNode? {
    val dummy = ListNode(0)
    var tail: ListNode = dummy
    for (v in values) {
        tail.next = ListNode(v)
        tail = tail.next!!
    }
    return dummy.next
}

fun toListK(head: ListNode?): List<Int> {
    val result = mutableListOf<Int>()
    var cur = head
    while (cur != null) {
        result.add(cur.`val`)
        cur = cur.next
    }
    return result
}

fun main() {
    val input = listOf(
        intArrayOf(1, 4, 5),
        intArrayOf(1, 3, 4),
        intArrayOf(2, 6)
    )

    val listsA: Array<ListNode?> = input.map { buildListK(it) }.toTypedArray()
    val listsB: Array<ListNode?> = input.map { buildListK(it) }.toTypedArray()

    val resA = toListK(mergeKLists(listsA))
    val resB = toListK(mergeKListsByHeap(listsB))

    println("分治结果:   $resA")
    println("小顶堆结果: $resB")
    println("结果一致: ${resA == resB}")
}

/**
1、22合并
2、小顶堆
 */




    // fun mergeKLists(lists: Array<ListNode?>): ListNode? {
    //     if (lists.isEmpty()) return null
    //     val vq = PriorityQueue<ListNode>({ a, b -> a.`val` - b.`val` })
    //     for(head in lists){
    //     if(head!=null) vq.offer(head)
    //     }
    //    val dummy = ListNode(0)
    //    var tail: ListNode = dummy
    //     while(vq.isNotEmpty()){
    //         val node = vq.poll()
    //         tail.next = node
    //         tail=tail!!.next
    //         if(node.next !=null){
    //             vq.offer(node.next)
    //         }

    //     }
    //     return dummy.next
    // }