//https://leetcode.cn/problems/generate-parentheses/description/

fun generateParenthesis(n: Int): List<String> {
    val result = mutableListOf<String>()

    fun dfs(s: String, left: Int, right: Int) {
        // 终止条件：字符串长度达到 2n
        if (s.length == 2 * n) {
            result.add(s)
            return
        }
        // 左括号没用完，可以放 (
        if (left < n) {
            dfs(s + '(', left + 1, right)
        }
        // 右括号数小于左括号数，可以放 )
        if (right < left) {
            dfs(s + ')', left, right + 1)
        }
    }

    dfs("", 0, 0)
    return result
}

// 自底向上 DP
fun generateParenthesisDP(n: Int): List<String> {
    val dp = List(n + 1) { mutableListOf<String>() }
    dp[0] = mutableListOf("")

    for (i in 1..n) {                     // 逐步构造 dp[1] .. dp[n]
        for (a in 0 until i) {            // 里面 a 对
            val b = i - 1 - a             // 右边 b 对
            for (inner in dp[a]) {
                for (outer in dp[b]) {
                    dp[i].add("($inner)$outer")
                }
            }
        }
    }
    return dp[n]
}

fun main(args: Array<String>) {
    
}