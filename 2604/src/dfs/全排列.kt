    
    
    
    /**
 示例 1：

输入：nums = [1,2,3]
输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
示例 2：

输入：nums = [0,1]
输出：[[0,1],[1,0]]
示例 3：

输入：nums = [1]
输出：[[1]] */
    
    
    fun permute(nums: IntArray): List<List<Int>> {
       val ans = mutableListOf<MutableList<Int>>()
       val path = mutableListOf<Int>()
       val used = BooleanArray(nums.size)
       dfs(nums,path,used,ans)
       return ans
    }


fun dfs(nums:IntArray, 
path:MutableList<Int>, 
used:BooleanArray,
ans:MutableList<MutableList<Int>>,
){
  if(path.size==nums.size) {
    //ans 里所有引用都指向同一个空列表，结果就全空了。必须拷一份再存。
    ans.add(ArrayList(path))
    return
  }

  for(i in nums.indices){
    if(used[i]) continue
    path.add(nums[i])
    used[i]=true
    dfs(nums,path,used,ans)
    used[i]=false
    path.removeAt(path.size-1)
  }


}