package Array.`88`

class 子数组 {
}

fun getMax(nums:IntArray):Int{
    var size = nums.size
    var array = ArrayList<Int>()
    var maxSum = Int.MIN_VALUE
    for(i in 0 until size){
        for(j in i until size){
              array.add(nums[j])
              maxSum = Math.max(maxSum, array.sum())
        }
        array.clear()
    }
    return maxSum
}

fun getMax2(nums:IntArray):Int{
    var size = nums.size
    var prex = ArrayList<Int>()
    var maxSum = Int.MIN_VALUE
    prex[0]= nums[0]
    for(i in 0..size-1){
        prex[i] +=prex[i-1]
    }
    for(i in 0 ..size-1){
        
    }
    return maxSum
}

fun main() {
    var nums = intArrayOf(-2,1,-3,4,-1,2,1,-5,4)
    println(getMax(nums))

    var nums2 = intArrayOf(5,4,-1,7,8)
    println(getMax(nums2))
}