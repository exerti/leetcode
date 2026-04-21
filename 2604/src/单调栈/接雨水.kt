package 单调栈

import kotlin.math.max

fun trap(height: IntArray): Int {
    // 小于 3 根柱子不可能形成凹槽，无法接水
    if (height.size <= 2) return 0

    // l/r 分别从两端向中间收缩
    var l = 0
    var r = height.size - 1

    // cap: 当前累计接到的总水量
    var cap = 0

    // leftCeil: [0..l] 区间出现过的最高柱子
    // rightCeil: [r..n-1] 区间出现过的最高柱子
    var leftCeil = height.first()
    var rightCeil = height.last()

    // 只要双指针未交错，就继续处理
    while (l <= r) {
        // 哪边“天花板”更低，就先结算哪边
        // 因为一格能装的水量由 min(左最高,右最高) 决定
        // 当 leftCeil < rightCeil 时，左侧这一格的上限已经确定为 leftCeil
        if (leftCeil < rightCeil) {
            // 当前左格可接水 = 左侧上限 - 当前柱高
            // 不会出现负数：leftCeil 始终是 [0..l] 的最大值，必然 >= height[l]
            cap += leftCeil - height[l]

            // 结算完当前左格后，左指针右移，准备处理下一格
            l++

            // 更新左侧最高柱（供后续格子计算上限）
            if (l < height.size) {
                leftCeil = max(leftCeil, height[l])
            }
        } else {
            // 对称逻辑：当 rightCeil <= leftCeil 时，先结算右侧
            cap += rightCeil - height[r]

            // 右指针左移，处理下一格
            r--

            // 更新右侧最高柱
            if (r >= 0) {
                rightCeil = max(rightCeil, height[r])
            }
        }
    }

    // 双指针相遇后，所有格子都已被结算
    return cap
}

fun trap2(height: IntArray): Int {

    if (height.size <= 2) return 0
    var left = 0
    var right = height.size - 1
    var cap = 0
    var leftCeil = height.first()
    var rightCeil = height.last()
    while (left <= right) {
        if (leftCeil < rightCeil) {
            cap+= leftCeil-height[left]
            left++
            if(left<height.size) {
                leftCeil = max(leftCeil, height[left])
            }
        } else {
           cap += rightCeil - height[right]
            right--
            if (right>=0) {
                rightCeil = max(rightCeil, height[right])
            }
        }
    }
    return cap
}


fun main() {
    val input = intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1)
    println(trap2(input))

//    print('[')
//    for (temperature in temperatures) {
//        print(temperature.toString() +",")
//    }
//    print(']')
}