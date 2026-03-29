fun mergeSort(arr: ArrayList<Int>): ArrayList<Int> {
    if (arr.size <= 1) return arr
    val mid = arr.size / 2
    val left = mergeSort(ArrayList(arr.subList(0, mid)))
    val right = mergeSort(ArrayList(arr.subList(mid, arr.size)))
    return merge(left, right)
}

private fun merge(left: ArrayList<Int>, right: ArrayList<Int>): ArrayList<Int> {
    val result = ArrayList<Int>()
    var i = 0
    var j = 0
    while (i < left.size && j < right.size) {
        if (left[i] <= right[j]) {
            result.add(left[i++])
        } else {
            result.add(right[j++])
        }
    }
    while (i < left.size) result.add(left[i++])
    while (j < right.size) result.add(right[j++])
    return result
}
