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
        TODO()
    }
}

fun main() {
    // 测试用例
    val nums = intArrayOf(-1, 0, 1, 2, -1, -4)
    // 预期: [[-1,-1,2], [-1,0,1]]
}
