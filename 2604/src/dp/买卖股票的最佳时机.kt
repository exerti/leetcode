package dp

import sun.swing.MenuItemLayoutHelper.max

fun maxProfit(prices: IntArray): Int {
    val n = prices.size
    if (n == 0) return 0
    val dp = Array(n) { IntArray(2) }
    dp[0][0] = 0
    dp[0][1] = -prices[0]

    for (i in 1 until n) {
        dp[i][0] = Math.max(dp[i - 1][1] + prices[i], dp[i - 1][0])  // 持有→卖出  vs  继续不持有
        dp[i][1] = Math.max(dp[i - 1][1], -prices[i])                  // 继续持有    vs  今天买入
    }
    return dp[n - 1][0]
}