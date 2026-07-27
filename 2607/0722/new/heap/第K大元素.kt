package new.heap

import java.util.PriorityQueue


class Solution{


    fun findKthLargest(nums: IntArray, k: Int): Int {

        var minHeap = PriorityQueue<Int>(k)
        for( element in nums){
            minHeap.offer(element)
            if(minHeap.size>k){
                minHeap.poll()
            }
        }
        return minHeap.peek()
    }
}