package Array.`88`

class `合并两个有序数组` {
    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
        var i = m - 1    // nums1 有效元素末尾
        var j = n - 1    // nums2 末尾
        var k = m + n - 1// 合并后总末尾

        // 从后往前填，避免覆盖
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--]
            } else {
                nums1[k--] = nums2[j--]
            }
        }

        // 把 nums2 剩下的元素填进去
        while (j >= 0) {
            nums1[k--] = nums2[j--]
        }
    }
}