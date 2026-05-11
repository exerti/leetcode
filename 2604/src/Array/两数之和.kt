    fun twoSum(nums: IntArray, target: Int): IntArray {
        val map = HashMap<Int, Int>()
        for (i in nums.indices) {
            val need = target - nums[i]
            if (map.containsKey(need)) {
                return intArrayOf(map[need]!!, i)
            }
            map[nums[i]] = i
        }
        return intArrayOf()
    }
