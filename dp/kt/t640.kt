    fun findLengthOfLCIS(nums: IntArray): Int {

        val  n = nums.size
        if(n<=1) return n 
        val dp = IntArray(n){1}
        for(i in  1 until n){
            if(nums[i]>nums[i-1]){
                dp[i]=dp[i-1]+1
            }
        }

        return dp.max()

    }