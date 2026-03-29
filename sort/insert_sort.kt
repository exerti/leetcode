
// 认为第1个元素已经排序
// 逐个取出后续元素，插入到前面已排序的部分
fun insertSort(arr: ArrayList<Int>): ArrayList<Int> {
    // 从第1个元素开始（第0个已认为排序）
    for (i in 1 until arr.size) {
        val key = arr[i]  // 待插入的元素
        var j = i - 1     // 已排序部分的最后位置

        // 从右往左找到合适的插入位置
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j]  // 元素右移
            j--
        }
        arr[j + 1] = key  // 插入元素
    }
    return arr
}