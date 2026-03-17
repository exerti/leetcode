class findLengthOfLCISSolution {
    fun findLengthOfLCIS(nums: IntArray): Int {
        val n = nums.size 
        if(n<=1) return n
        //d[i] 表示 以i 结尾的最长连续数组是 最大长度
        val dp = IntArray(n) { 1 }
        for(i in 1 until n ){
            if(nums[i]>nums[i-1]){
              dp[i] = dp[i-1]+1;
            }
        }
        return dp.max()

    }
}
