
fun bubbleSort(arr: ArrayList<Int>): ArrayList<Int> {
    val len = arr.size
    for (i in 0 until len - 1) {
        for (j in 0 until len - 1 - i) {
            if (arr[j] > arr[j + 1]) {
                val temp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = temp
            }
        }
    }
    return arr
}