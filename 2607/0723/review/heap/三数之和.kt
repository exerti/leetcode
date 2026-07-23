package review.heap

class Solution2222 {

    fun threeSum(nums: IntArray): List<List<Int>> {
        nums.sort()
        val result = mutableListOf<List<Int>>()
        val size = nums.size
        for (i in 0 until size - 2) {
            // 跳过重复的 i
            if (i > 0 && nums[i] == nums[i - 1]) continue

            var left = i + 1
            var right = size - 1

            while (left < right) {
                val sum = nums[i] + nums[left] + nums[right]
                when {                               // when 必须加 { }
                    sum == 0 -> {
                        result.add(listOf(nums[i], nums[left], nums[right])) // 增加值，不是索引
                        while (left < right && nums[left] == nums[left + 1]) left++
                        while (left < right && nums[right] == nums[right - 1]) right--
                        left++
                        right--
                    }
                    sum > 0 -> right--
                    else -> left++
                }
            }
        }
        return result
    }

}