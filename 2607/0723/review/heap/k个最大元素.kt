
import  java.util.PriorityQueue

class Solution3 {
    fun findKthLargest(nums: IntArray, k: Int): Int {
       val minHeap = PriorityQueue<Int>()
        for(num in nums){
            minHeap.offer(num)
            if(minHeap.size>k){
                minHeap.poll()
            }
        }
        return minHeap.peek()
    }
}


fun main() {
    val testCase = intArrayOf(3, 2, 1, 5, 6, 4)
    val handler = Solution3()::findKthLargest  // 绑定引用，无需传 receiver
    println(handler(testCase, 2))
}