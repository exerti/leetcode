fun findLength(nums1: IntArray, nums2: IntArray): Int {

    val n = nums1.size
    val m =  nums2.size
    //dp[i][j] 并使。0 - i-1  0 - j-1 的最长子数组
    val dp = Array(n+1){ IntArray(m + 1) { 0 }}
    var result = 0 ;
    for(i in 1..n){
        for( j in 1  .. m){
            if(nums1[i-1]==nums2[j-1]){
                dp[i][j] = dp[i-1][j-1]+1;
                result = maxOf (dp[i][j],result)
            }
        }
    }
    
    return result
    

    
}
