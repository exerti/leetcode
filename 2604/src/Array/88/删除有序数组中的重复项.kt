package Array.`88`


fun removeDuplicates(nums: IntArray): Int {
    if (nums.isEmpty()) return 0 // 空数组直接返回

    var slow = 0 // 慢指针：指向新数组最后一个有效元素

    // 快指针从 1 开始遍历
    for (fast in 1 until nums.size) {
        // 发现不重复的元素
        if (nums[fast] != nums[slow]) {
            slow++               // 慢指针先前进一位
            nums[slow] = nums[fast] // 再赋值
        }
    }

    return slow + 1 // 长度 = 下标 + 1
}


/**
 * 输入：nums = [1,1,2]
 * 输出：2, nums = [1,2,_]
 */


fun removeDuplicates_reveiw(nums:IntArray): Int {
    if(nums.isEmpty()) return 0
    var slow = 0
    for (fast in 1  until nums.size) {
        if (nums[fast] != nums[slow]) {
            slow++
            nums[slow] = nums[fast]
        }
    }
    return  slow+1
}

fun main() {
//    var test_nums = intArrayOf(1,1,2)
//    print(removeDuplicates_reveiw(test_nums))
    var test_nums = intArrayOf(0,0,1,1,1,2,2,3,3,4)
    print(removeDuplicates_reveiw(test_nums))
    println()
    print(test_nums.contentToString())
}