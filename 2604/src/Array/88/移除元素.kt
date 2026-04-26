package Array.`88`

class 移除元素 {
}


//双指针
fun removeElement(nums: IntArray, `val`: Int): Int {
    var slow = 0 // 慢指针：指向新数组的下一个位置
    // 快指针：遍历整个数组，找不需要删除的元素
    for (fast in nums.indices) {
        // 如果快指针找到 不是要删除的元素
        if (nums[fast] != `val`) {
            nums[slow] = nums[fast] // 把它赋值给慢指针位置
            slow++ // 慢指针前进
        }
        // 如果是要删除的元素，快指针自己走，慢指针不动
    }
    return slow // slow 就是最终新数组长度
}


/**
 * 输入：nums = [3,2,2,3], val = 3
 * 输出：2, nums = [2,2,_,_]
 * 解释：你的函数应该返回 k = 2, 并且 nums 中的前两个元素均为 2。
 * 你在返回的 k 个元素之外留下了什么并不重要（因此它们并不计入评测）。
 */

fun review_remove(nums: IntArray,`val`: Int): Int {
    var slow = 0
    for (fast in nums.indices) {
        if (nums[fast] != `val`) {
            nums[slow++] = nums[fast]
        }
    }
    return  slow+1
}


/**
 * 双指针 slow fast  移动数组
 */


fun main() {
//    var test_nums = intArrayOf(0,0,1,1,1,2,2,3,3,4)
//    review_remove(test_nums, 1)
//    println(
//        test_nums.contentToString()
//    )

    var test_nums = intArrayOf(3,2,2,3)
    review_remove(test_nums, 3)
    println(
        test_nums.contentToString()
    )

}