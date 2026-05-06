package `stack&queue`

import java.util.PriorityQueue

// ==================== 方法一：PriorityQueue ====================

fun findKthLargest(nums: IntArray, k: Int): Int {
    val minHeap = PriorityQueue<Int>()
    for (num in nums) {
        if (minHeap.size < k) {
            minHeap.offer(num)
        } else if (num > minHeap.peek()) {
            minHeap.poll()
            minHeap.offer(num)
        }
    }
    return minHeap.peek()
}

// ==================== 方法二：手写堆 ====================

fun findKthLargestByHeap(nums: IntArray, k: Int): Int {
    val heap = IntArray(k)
    var size = 0

    fun siftUp(i: Int) {
        var idx = i
        while (idx > 0) {
            val p = (idx - 1) / 2
            if (heap[idx] >= heap[p]) break
            heap[idx] = heap[p].also { heap[p] = heap[idx] }
            idx = p
        }
    }

    fun siftDown(i: Int) {
        var idx = i
        while (true) {
            val l = 2 * idx + 1
            val r = 2 * idx + 2
            var smallest = idx
            if (l < size && heap[l] < heap[smallest]) smallest = l
            if (r < size && heap[r] < heap[smallest]) smallest = r
            if (smallest == idx) break
            heap[idx] = heap[smallest].also { heap[smallest] = heap[idx] }
            idx = smallest
        }
    }

    for (num in nums) {
        if (size < k) {
            heap[size] = num
            siftUp(size)
            size++
        } else if (num > heap[0]) {
            heap[0] = num
            siftDown(0)
        }
    }
    return heap[0]
}

// ==================== 方法三：快速选择 ====================

fun findKthLargestByQuickSelect(nums: IntArray, k: Int): Int {
    // 第 K 大 = 排序后下标 nums.size - k 的位置
    val target = nums.size - k
    var lo = 0
    var hi = nums.size - 1

    while (lo < hi) {
        val pivot = partition(nums, lo, hi)
        when {
            pivot == target -> return nums[pivot]
            pivot < target  -> lo = pivot + 1
            else            -> hi = pivot - 1
        }
    }
    return nums[lo]
}

private fun partition(nums: IntArray, lo: Int, hi: Int): Int {
    val pivotVal = nums[hi]
    var i = lo
    for (j in lo until hi) {
        if (nums[j] <= pivotVal) {
            nums[i] = nums[j].also { nums[j] = nums[i] }
            i++
        }
    }
    nums[i] = nums[hi].also { nums[hi] = nums[i] }
    return i
}

// ==================== 测试 ====================

fun main() {
    val nums1 = intArrayOf(3, 2, 1, 5, 6, 4)
    val nums2 = intArrayOf(3, 2, 3, 1, 2, 4, 5, 5, 6)

    println("--- PriorityQueue ---")
    println("${findKthLargest(nums1, 2)} (expected 5)")
    println("${findKthLargest(nums2, 4)} (expected 4)")

    println("\n--- 手写堆 ---")
    println("${findKthLargestByHeap(nums1, 2)} (expected 5)")
    println("${findKthLargestByHeap(nums2, 4)} (expected 4)")

    println("\n--- 快速选择 ---")
    println("${findKthLargestByQuickSelect(intArrayOf(3,2,1,5,6,4), 2)} (expected 5)")
    println("${findKthLargestByQuickSelect(intArrayOf(3,2,3,1,2,4,5,5,6), 4)} (expected 4)")
}