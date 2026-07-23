package new

class SolutionKNums {
    fun kNums(nums: IntArray, k: Int): Int {
        val targetIndex = nums.size - k
        return quickSelect(nums, 0, nums.size - 1, targetIndex)
    }

    fun quickSelect(nums: IntArray, left: Int, right: Int, targetIndex: Int): Int {
        val pivotIndex = doPartition(nums, left, right)
        return when {
            // 比的是索引，不是值
            pivotIndex == targetIndex -> nums[pivotIndex]
            pivotIndex > targetIndex -> quickSelect(nums, left, pivotIndex - 1, targetIndex)
            else -> quickSelect(nums, pivotIndex + 1, right, targetIndex)
        }
    }

    fun doPartition(nums: IntArray, left: Int, right: Int): Int {
        val pivotVal = nums[right]              // 选最右元素的值
        var i = left                            // i: 小元素区的右边界，不用 left 避免覆盖参数
        for (j in left until right) {           // j: 遍历指针，不用 right 避免覆盖参数
            if (nums[j] < pivotVal) {           // 小于 pivot 的放左边（升序分区）
                swap(nums, i, j)
                i++
            }
        }
        swap(nums, i, right)                    // pivot 归位到 i，right 是索引不是值
        return i
    }

    fun swap(nums: IntArray, i: Int, j: Int) {
        val temp = nums[i]
        nums[i] = nums[j]
        nums[j] = temp
    }
}

fun main() {
    val testCase = intArrayOf(3, 2, 1, 5, 6, 4)
    val handler = SolutionKNums()::kNums
    println(handler(testCase, 2)) // 预期输出 5
}