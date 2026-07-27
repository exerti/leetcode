import java.util.PriorityQueue
import kotlin.comparisons.compareBy

/**
 * 数组中的第K个最大元素 (LeetCode 215)
 *
 * ============================
 * 核心思路：快速选择 QuickSelect
 * ============================
 *
 * 快排：partition 后 pivot 归位 → 左边递归 + 右边递归 → O(n log n)
 * 快速选择：partition 后 pivot 归位 → 只递归目标所在的那一半 → O(n)
 *
 * 第K大 = 从小到大排序后下标为 n-k 的元素（记为 target）
 *
 * 每次 partition 后：
 *   pivotIndex == target → 命中，直接返回 ✓
 *   pivotIndex <  target → 目标在右边，丢弃左边
 *   pivotIndex >  target → 目标在左边，丢弃右边
 *
 * 平均每次丢弃一半 → O(n + n/2 + n/4 + ...) = O(2n) = O(n)
 */

class Solution2 {

    fun findKthLargest(nums: IntArray, k: Int): Int {
        val target = nums.size - k
        return quickSelect(nums, 0, nums.size - 1, target)
    }

    private fun quickSelect(nums: IntArray, left: Int, right: Int, target: Int): Int {
        val pivotIndex = partition(nums, left, right)

        return when {
            pivotIndex == target -> nums[pivotIndex]          // 找到了！
            pivotIndex < target  -> quickSelect(nums, pivotIndex + 1, right, target) // 去右边
            else                 -> quickSelect(nums, left, pivotIndex - 1, target)   // 去左边
        }
    }

    /**
     * partition 分区过程（以 nums[right] 为 pivot）：
     *
     *   [3, 2, 1, 5, 6, 4]   pivot=4
     *    ↑           ↑  ↑
     *    i           j  pivot
     *
     *   j 从左扫到右：
     *     遇到 ≤4 的 → 换到 i 位置，i++
     *     遇到 >4 的 → 跳过
     *
     *   [3, 2, 1, 5, 6, 4]
     *              ↑     ↑
     *   j=0: 3≤4 ✓ → swap(0,0), i=1
     *   j=1: 2≤4 ✓ → swap(1,1), i=2
     *   j=2: 1≤4 ✓ → swap(2,2), i=3
     *   j=3: 5≤4 ✗ → 跳过, i 不动
     *   j=4: 6≤4 ✗ → 跳过, i 不动
     *
     *   最后 swap(i=3, right=5) → [3, 2, 1, 4, 6, 5]
     *                                         ↑ pivot 归位
     */
    private fun partition(nums: IntArray, left: Int, right: Int): Int {
        val pivot = nums[right]
        var i = left
        for (j in left until right){
            if(nums[j] <= pivot){
                swap(nums,i,j)
                i++
            }
        }
        swap(nums,i,right)
        return i
    }

    private fun swap(nums: IntArray, a: Int, b: Int) {
        val temp = nums[a]
        nums[a] = nums[b]
        nums[b] = temp
    }
}

fun main() {
    val handler = Solution2()::findKthLargest

    println(handler(intArrayOf(3, 2, 1, 5, 6, 4), 2))                      // 期望 5
    println(handler(intArrayOf(3, 2, 3, 1, 2, 4, 5, 5, 6), 4))             // 期望 4
}


class  Soluntion3{

    fun findKthLargest(nums: IntArray, k: Int): Int {
        val queue = PriorityQueue<Int>(k)  // 默认自然序 = 小顶堆，不需要 compareBy

        for (num in nums) {
            queue.offer(num)
            if (queue.size > k) {
                queue.poll()  // 弹出最小的，保持堆大小为 k
            }
        }
        return queue.peek()  // 堆顶 = k 个最大元素中最小的 = 第K大


    }
}
