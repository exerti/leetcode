package review.heap

class Solution22224{

    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
        nums.sort() // 必须排序，双指针依赖有序数组
        val size = nums.size
        val result = mutableListOf<List<Int>>()
        for (i in 0 until size - 3) {
            // i > 0 防止 nums[i-1] 越界
            if (i > 0 && nums[i] == nums[i - 1]) continue
            for (j in i + 1 until size - 2) {
                // j > i+1 防止误跳过
                if (j > i + 1 && nums[j] == nums[j - 1]) continue
                var left = j + 1
                var right = size - 1
                while (left < right) {
                    val sum = nums[i] + nums[j] + nums[left] + nums[right]
                    when {
                        sum == target -> {
                            // 存的是值，不是索引
                            result.add(listOf(nums[i], nums[j], nums[left], nums[right]))
                            while (left < right && nums[left] == nums[left + 1]) left++
                            while (left < right && nums[right] == nums[right - 1]) right--
                            left++
                            right--
                        }
                        sum > target -> right--
                        else -> left++
                    }
                }
            }
        }
        return result
    }
}