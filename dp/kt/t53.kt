 fun maxSubArray(nums: IntArray): Int {
        val n = nums.size
        val dp = IntArray(n)
        dp[0]=nums[0]
        var result = dp[0]
        for( i in 1 until n){
            dp[i] = maxOf(dp[i-1]+nums[i],nums[i])
            result =maxOf(result,dp[i])
        }
        return result;
        
    }