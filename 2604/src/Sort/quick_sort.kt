package Sort

fun quick_sort(array: ArrayList<Int>, low: Int = 0, high: Int = array.size - 1) {
    if (low >= high) return  // 这里修复
    val pivot = partition(array, low, high)
    quick_sort(array, low, pivot - 1)
    quick_sort(array, pivot + 1, high)
}

// ✅ 完全正确的分区函数
fun partition(array: ArrayList<Int>, low: Int, high: Int): Int {
    val pivot = array[low]  // 选第一个当基准
    var i = low + 1

    // ❗ 关键修复：j 必须遍历到 high，不能是 until high
    for (j in low + 1 .. high) {
        // ❗ 关键修复：和基准 pivot 比较，不是和 array[i] 比较
        if (array[j] < pivot) {
            val temp = array[i]
            array[i] = array[j]
            array[j] = temp
            i++
        }
    }

    // 把基准放到正确位置
    val temp = array[low]
    array[low] = array[i - 1]
    array[i - 1] = temp

    return i - 1
}


fun main() {
    var array = ArrayList<Int>()
    array.add(5)
    array.add(6)
    array.add(7)
    array.add(1)
    array.add(2)
    array.add(3)
    array.add(4)
    array.add(7)
    array.add(1)
    array.add(2)
    array.add(3)

    quick_sort(array)
    print_array(array)
}