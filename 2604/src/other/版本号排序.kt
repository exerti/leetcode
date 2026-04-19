/**
 * Kotlin 数组类型总结
 *
 * 1. 泛型数组 Array<T>（引用类型，有装箱开销）
 *    val arr = Array<String>(3) { "" }
 *    val arr = arrayOf("a", "b", "c")
 *
 * 2. 基本类型数组（无装箱，性能更好）
 *    IntArray     intArrayOf(1, 2, 3)
 *    LongArray    longArrayOf(...)
 *    DoubleArray  doubleArrayOf(...)
 *    BooleanArray booleanArrayOf(...)
 *    CharArray    charArrayOf(...)
 *
 * 3. 二维数组
 *    val dp = Array(n) { IntArray(m) { 0 } }   // ✓ 正确
 *    val dp = IntArray(n) { IntArray(m) { 0 } } // ✗ 类型不匹配
 *
 * 4. 常用操作
 *    arr.size                    // 长度
 *    arr[0]                      // 取值
 *    arr.max()                   // 最大值
 *    arr.sorted()                // 排序（返回 List）
 *    arr.getOrElse(i) { 0 }      // 越界返回默认值
 *    arr.map { it * 2 }          // 映射
 *    arr.filter { it > 1 }       // 过滤
 *
 * 选择原则：
 *    存 Int/Long/Double 等基本类型 → IntArray 等，性能好
 *    存对象/字符串/自定义类       → Array<T>
 *    DP 二维数组                  → Array(n) { IntArray(m) }
 */

fun versionSort(versions: List<String>): List<String> {
    return versions.sortedWith(Comparator { v1, v2 ->
        val parts1 = v1.split(".").map { it.toInt() }
        val parts2 = v2.split(".").map { it.toInt() }
        val maxLen = maxOf(parts1.size, parts2.size)

        for (i in 0 until maxLen) {
            val p1 = parts1.getOrElse(i) { 0 }  // 不足的位补 0
            val p2 = parts2.getOrElse(i) { 0 }
            if (p1 != p2) return@Comparator p1 - p2  // 找到差异就返回
        }
        0  // 完全相同
    })
}

fun main() {
    val versions = listOf("1.45.0", "1.5.0", "6", "3.3.3.3.3")
    println("排序前: $versions")
    val sorted = versionSort(versions)
    println("排序后: $sorted")
    // 期望: [1.5.0, 1.45.0, 3.3.3.3.3, 6]
}
