package review

/**
 * ⚠️ 上次失误:
 * 1. doPartition 中 var left=0 覆盖了参数 left，导致分区逻辑错
 * 2. for(right in 0..nums.size) 覆盖了参数 right，且循环范围应从 left 开始
 * 3. povit 是值(nums[right])但被当索引用(nums[povit])
 * 4. swap(nums, left, povit) 第二个参数是值不是索引
 * 5. nums[partition]==nums[targetIndex] 比的是值，应比索引 partition==targetIndex
 * 6. quickSelect 右边界传 nums.size，应为 nums.size-1
 * 7. return when { ... return } 双层 return
 * 8. partition 与 Kotlin 标准库扩展函数撞名
 * 9. import Solution3 未被使用
 * 📈 掌握度: 1/5 | 累计错误: 9次 | 间隔: 1天
 */

import java.util.*

class KthLargestQuickSelect {
    fun findKthLargest(nums: IntArray, k: Int): Int {
        TODO()
    }
}

fun main() {
    // 测试用例
    val testCase = intArrayOf(3, 2, 1, 5, 6, 4)
    // 预期: k=2 → 5
}
