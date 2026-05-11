package Array.`88`


//双指针

fun trap(height: IntArray): Int {
    if(height.size<=2) return 0
    var left = 0
    var right = height.size - 1
    var leftMax = 0
    var rightMax = 0
    var ans = 0
    while (left <= right) {
        leftMax = Math.max(leftMax,height[left])
        rightMax = Math.max(rightMax,height[right])
        if (leftMax < rightMax) {
            ans+=  leftMax- height[left]
            left++
        }else{
            ans += rightMax- height[right]
            right--
        }
    }
    return ans
}


/**
 * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出：6
 * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
 */

// 双指针
fun review_trap(height: IntArray):Int{
   if(height.size<=2) return 0
    var left = 0
    var right = height.size - 1
    var leftMax = 0
    var rightMax = 0
    var ans = 0
    while (left <= right) {
        leftMax = Math.max(leftMax,height[left])
        rightMax = Math.max(rightMax,height[right])
        if (leftMax < rightMax) {
            ans +=  leftMax- height[left]
            left++
        }else{
            ans += rightMax- height[right]
            right--
        }
    }

    return  ans
}

fun main() {

    print(    trap(intArrayOf(0,1,0,2,1,0,1,3,2,1,2,1)))
    println()
    print(    review_trap(intArrayOf(0,1,0,2,1,0,1,3,2,1,2,1)))
}
