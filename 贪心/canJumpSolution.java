package 贪心;

public class canJumpSolution {
    public boolean canJump(int[] nums) {
        if(nums.length==1){
            return true;
        }
        int range = 0 ;
        for(int i = 0; i<=range ; i++){
            range = Math.max(i+nums[i], range);
            if(range>=nums.length-1){
                return true;
            }
        }
        return false;
    }
}
