package Array.`88`

fun spiralOrder(matrix: Array<IntArray>): List<Int> {
    val m = matrix.size
    val n = matrix[0].size
    val result = mutableListOf<Int>()

    var top = 0
    var bottom = m - 1
    var left = 0
    var right = n - 1

    while (top <= bottom && left <= right) {
        // 左 → 右
        for (j in left..right) {
            result.add(matrix[top][j])
        }
        top++

        // 上 → 下
        for (i in top..bottom) {
            result.add(matrix[i][right])
        }
        right--

        // 右 → 左（防止只有一行的情况）
        if (top <= bottom) {
            for (j in right downTo left) {
                result.add(matrix[bottom][j])
            }
            bottom--
        }

        // 下 → 上（防止只有一列的情况）
        if (left <= right) {
            for (i in bottom downTo top) {
                result.add(matrix[i][left])
            }
            left++
        }
    }

    return result
}