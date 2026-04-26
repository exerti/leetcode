package 单调栈

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

fun main() {

 print(    trap(intArrayOf(0,1,0,2,1,0,1,3,2,1,2,1)))
}