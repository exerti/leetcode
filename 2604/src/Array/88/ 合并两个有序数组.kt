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


/*
输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
输出：[1,2,2,3,5,6]
解释：需要合并 [1,2,3] 和 [2,5,6] 。
合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
 */



/**
 * 倒序合并，大数后置；
 * 取 1 数组元素就i--，取 2 数组元素就j--；
 * nums2 有剩余必须额外遍历覆盖，nums1 剩余无需处理。
 */
//合并2个数组
fun review_merger(nums1:IntArray,m:Int ,nums2: IntArray, n : Int){
    var i  = m - 1  //m是长的
    var j = n - 1
    var k = m+n-1
    while (i >= 0 && j >= 0) {
        if (nums1[i] > nums2[j]) {
            nums1[k--] = nums1[i--]
        }else{
            nums1[k--] = nums2[j--]
        }
    }

    while (j >= 0) {
        nums1[k--] = nums2[j--]
    }
}


fun main() {
//    var nums1 = intArrayOf(1,2,3,0,0,0,)
//    var nums2 = intArrayOf(2,5,6)
//    var m = 3
//    var n = 3
//    review_merger(nums1,m,nums2,n)
//    print(nums1.contentToString())


    var nums1 = intArrayOf(0,)
    var nums2 = intArrayOf(1)
    var m = 0
    var n = 1
    review_merger(nums1,m,nums2,n)
    print(nums1.contentToString())




}