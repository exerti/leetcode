package new.heap

import java.util.PriorityQueue

class ListNode(var `val`: Int) {
         var next: ListNode? = null
 }

class Solution23 {
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
       val minHeap = PriorityQueue<ListNode>(compareBy { it.`val` })
        lists.forEach {
           head->if(head!=null){
               minHeap.add(head)
           }
        }
       val dummy = ListNode(0)
        var curr = dummy

        while (minHeap.isNotEmpty()) {
            val node = minHeap.poll()
            curr.next = node
            curr= node
            if(node.next!=null){
                minHeap.offer(node.next)
            }
        }

        return dummy.next

    }
}