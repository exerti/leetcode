package review

/**
 * ⚠️ 上次失误:
 * 1. when 缺 {} 包裹分支，Kotlin when 表达式必须用花括号
 * 2. result.add 存的是索引 i/left/right，应存 nums[i]/nums[left]/nums[right]
 * 3. 缺 i>0 判断，i=0 时 nums[i-1] 越界，且未跳过重复 i 产生冗余结果
 * 📈 掌握度: 1/5 | 累计错误: 3次 | 间隔: 1天
 */

class ThreeSum {
    fun threeSum(nums: IntArray): List<List<Int>> {
        nums.sort()
        val size = nums.size
        val result = mutableListOf<List<Int>>()
        for (i in 0 until size - 2) {
            if (i > 0 && nums[i - 1] == nums[i]) continue
            var left = i + 1
            var right = size - 1
            while (left < right) {
                val sum = nums[i].toLong() + nums[left] + nums[right]
                when {
                    sum == 0L -> {
                        result.add(intArrayOf(nums[i], nums[left], nums[right]).toList())
                        while (left < right && nums[left] == nums[left + 1]) left++
                        while (left < right && nums[right] == nums[right - 1]) right--
                        left++
                        right--
                    }
                    sum > 0L -> right--
                    else -> {
                        left++
                    }
                }
            }


        }
        return result
    }
}

fun main() {
    // 测试用例
    val nums = intArrayOf(-1, 0, 1, 2, -1, -4)
    // 预期: [[-1,-1,2], [-1,0,1]]
}
