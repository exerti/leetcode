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