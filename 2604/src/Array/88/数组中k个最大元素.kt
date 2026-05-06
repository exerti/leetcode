package Array.`88`

import java.util.PriorityQueue
import kotlin.math.min

/**
 * 215. 数组中的第K个最大元素
 * 中等
 * 相关标签
 * premium lock icon
 * 相关企业
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 *
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 *
 * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
 */


//ologn
//fun findKthLargest(nums: IntArray, k: Int): Int {
//    nums.sortDescending()
//    return nums[k - 1]
//}


/*
 小顶堆
 */
fun findKthLargest(nums: IntArray, k: Int): Int {
    val  minHeap = PriorityQueue<Int>()
    for(i in nums.indices) {
        var size = minHeap.size
        if (size<k) {
            minHeap.offer(nums[i])
        }else{
            if (nums[i] > minHeap.peek()) {   // 只有比堆顶大才值得进去
                minHeap.poll()
                minHeap.offer(nums[i])
            }
        }
    }
    return minHeap.peek()
}


fun main() {
    val testCases = mapOf(
        intArrayOf(3, 2, 1, 5, 6, 4) to 2 to 5,
        intArrayOf(3, 2, 3, 1, 2, 4, 5, 5, 6) to 4 to 4,
    )

    for ((pair, expected) in testCases) {
        val (nums, k) = pair
        val result = findKthLargest(nums, k)
        println("nums=${nums.contentToString()}, k=$k → result=$result, expected=$expected ${if (result == expected) "✓" else "✗ FAIL"}")
    }
}

//fix 实现堆

//fix 使用快速排序方法