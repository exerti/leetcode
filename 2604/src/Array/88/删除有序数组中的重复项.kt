package Array.`88`

class 删除有序数组中的重复项 {
}

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