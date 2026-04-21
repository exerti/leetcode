package 单调栈

//维护一个队列，遍历数组，每次遇到一个新的大的数字 ，就把前面的都给复制
class Solution {
    fun dailyTemperatures(temperatures: IntArray): IntArray {
        var n = temperatures.size
        val res = IntArray(n) { 0 }
        val stack: ArrayDeque<Int> = ArrayDeque()

        for (i in 0 until n) {
            while (stack.isNotEmpty() && temperatures[i] > temperatures[stack.last()]) {
                var prev = stack.removeLast()
                res[prev] = i - prev
            }
            stack.addLast(i)
        }
        return res
    }
}

fun main() {

    var temperatures = Solution().dailyTemperatures(intArrayOf(73,74,75,71,69,72,76,73))
    print('[')
    for (temperature in temperatures) {
        print(temperature.toString() +",")
    }
    print(']')
}