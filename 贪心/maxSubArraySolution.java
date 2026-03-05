package 贪心;

public class maxSubArraySolution {
    public int maxSubArray(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        ans = dp[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = { 1, -1, 2, 3, 2, -1 };
        maxSubArraySolution solution = new maxSubArraySolution();
        System.out.println(solution.maxSubArray(arr));

        int[] arrw = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
        System.out.println(solution.maxSubArray(arrw));

    }
}
