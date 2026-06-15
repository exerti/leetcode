fun merge(intervals: Array<IntArray>): Array<IntArray> {
    val result = mutableListOf<IntArray>()
    // 1. 按左端点排序
    val sorted = intervals.sortedBy { it[0] }
    // 2. 上一个区间初始为 null
    var preInterval: IntArray? = null
    for (interval in sorted) {
        val preTail = preInterval?.get(1) ?: -1
        if (preInterval != null && interval[0] <= preTail) {
            // 重叠：合并，更新上一个区间的右端点
            preInterval!![1] = maxOf(preTail, interval[1])
        } else {
            // 不重叠：结算上一个，开启新区间
            preInterval?.let { result.add(it) }
            preInterval = intArrayOf(interval[0], interval[1])
        }
    }
    // 3. 最后一个区间入结果
    preInterval?.let { result.add(it) }
    return result.toTypedArray()
}
