package Array.`88`


/**
 * 直觉
 * 三数之和 → 先排序 → 固定一个数 → 剩下两个数用双指针找。
 *
 *
 * nums = [-1, 0, 1, 2, -1, -4]
 *
 * 排序后: [-4, -1, -1, 0, 1, 2]
 *
 * 固定 i=0 (-4)，在右边区间 [-1,-1,0,1,2] 用双指针找两数之和=4 → 没有
 * 固定 i=1 (-1)，在右边区间 [-1,0,1,2] 用双指针找两数之和=1 → [-1,2] 和 [0,1]
 * ...
 * 框架三步
 *
 * 1. 排序
 * 2. 遍历 i（0 ~ n-3）
 *    如果 i > 0 且 nums[i] == nums[i-1] → 跳过（去重）
 * 3. 对每个 i，在 [i+1, n-1] 区间双指针：
 *      sum = nums[i] + nums[left] + nums[right]
 *      sum == 0 → 记录答案，left 右移，right 左移，跳过重复
 *      sum < 0  → left++
 *      sum > 0  → right--
 * 问自己三个问题
 * Q1：为什么必须排序？
 * 不排序双指针不知道该往哪走。排完序后 sum < 0 → left++（需要更大的数），sum > 0 → right--（需要更小的数）。
 *
 * Q2：去重怎么做？
 * 三处都要去重：
 *
 *
 * i 去重:  if (i > 0 && nums[i] == nums[i-1]) continue
 * left 去重: while (nums[left] == nums[left+1]) left++
 * right 去重: while (nums[right] == nums[right-1]) right--
 * 去重放在找到答案之后做，不要放在之前。
 *
 * Q3：和两数之和 II（有序数组）的关系？
 * 三数之和 = 外层固定 i + 内层调用一次两数之和 II。
 *
 * 把这个框架写出来，你去建文件并实现。卡在去重就问我。
 */


fun threeSum(nums: IntArray): List<List<Int>> {
    nums.sort()                              // ① 必须排序
    val result = mutableListOf<List<Int>>()

    for (i in 0 until nums.size - 2) {
        if (i > 0 && nums[i] == nums[i - 1]) continue  // ② i 去重

        val target = -nums[i]
        var left = i + 1
        var right = nums.size - 1

        while (left < right) {               // ③ left < right，不是 <=
            val sum = nums[left] + nums[right]
            when {
                sum == target -> {
                    result.add(listOf(nums[i], nums[left], nums[right]))
                    left++
                    right--
                    // ④ left、right 去重
                    while (left < right && nums[left] == nums[left - 1]) left++
                    while (left < right && nums[right] == nums[right + 1]) right--
                }
                sum < target -> left++
                else -> right--
            }
        }
    }

    return result
}

fun threeSumClosest(nums: IntArray, target: Int): Int {
    nums.sort()
    var closest = nums[0] + nums[1] + nums[2]

    for (i in 0 until nums.size - 2) {
        var left = i + 1
        var right = nums.size - 1

        while (left < right) {
            val sum = nums[i] + nums[left] + nums[right]

            if (sum == target) return sum

            if (Math.abs(sum - target) < Math.abs(closest - target)) {
                closest = sum
            }

            if (sum < target) left++ else right--
        }
    }
    return closest
}

fun main() {
    var nums = intArrayOf(-1, 0, 1, 2, -1, -4)
    println("threeSum: ${threeSum(nums)}")

    nums = intArrayOf(-1, 2, 1, -4)
    println("threeSumClosest(1): ${threeSumClosest(nums, 1)}")  // expected: 2

    nums = intArrayOf(0, 0, 0)
    println("threeSumClosest(1): ${threeSumClosest(nums, 1)}")  // expected: 0
}