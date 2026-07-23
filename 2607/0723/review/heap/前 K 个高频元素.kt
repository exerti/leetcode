import java.util.PriorityQueue

class Solution {

    fun topFreq(nums: IntArray, k: Int): IntArray {
        val freqMap = nums.toList().groupingBy { it }.eachCount()
        // 小顶堆：按频率升序，堆顶是频率最小的元素
        val minHeap = PriorityQueue<Int>(compareBy { freqMap[it] })
        for (num in freqMap.keys) {
            minHeap.offer(num)
            if (minHeap.size > k) {
                minHeap.poll() // 弹出频率最小的
            }
        }
        return minHeap.toList().toIntArray()
    }

}

fun main() {
    val testcase = intArrayOf(1, 1, 1, 2, 2, 3)
    println(Solution().topFreq(testcase, 2).contentToString())
}