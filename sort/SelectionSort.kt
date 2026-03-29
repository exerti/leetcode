

// 每次找到最小元素，与当前位置交换
fun selectionSort(arr: ArrayList<Int>): ArrayList<Int> {
    val n = arr.size

    // 每轮找一个最小值
    for (i in 0 until n - 1) {
        var minIdx = i  // 假设当前位置是最小值

        // 从 i+1 往后找最小值
        for (j in i + 1 until n) {
            if (arr[j] < arr[minIdx]) {
                minIdx = j
            }
        }

        // 交换最小值到当前位置
        if (minIdx != i) {
            val temp = arr[i]
            arr[i] = arr[minIdx]
            arr[minIdx] = temp
        }
    }
    return arr
}
