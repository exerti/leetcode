package dfs

class SolutionB {
    fun maxAreaOfIsland(grid: Array<CharArray>): Int {
        if (grid.isEmpty() || grid[0].isEmpty()) return 0
        var row = grid.size
        var col = grid[0].size
        val visited = Array(row) { IntArray(col) { 0 } }
        var maxArea = 0
        for (i in 0 until row) {
            for (j in 0 until col) {
                if (visited[i][j] == 1 || grid[i][j] == '0') {
                    continue
                }
                var area = dfs(visited, grid, i, j, row, col)
                if (area > maxArea) {
                    maxArea = area
                }

            }
        }
        return maxArea

    }

    private fun dfs(
        visited: Array<IntArray>, // 错误 2：拼写错误 visted → visited
        grid: Array<CharArray>,

        x: Int, y: Int, row: Int, col: Int
    ): Int {
        if (x < 0 || x >= row || y < 0 || y >= col) {
            return 0
        }
        if (visited[x][y] == 1) {
            return 0
        }
        if (grid[x][y] == '0') {
            return 0
        }
        visited[x][y] = 1
        return 1 + dfs(visited, grid, x + 1, y, row, col)
        +dfs(visited, grid, x - 1, y, row, col)
        +dfs(visited, grid, x, y - 1, row, col)
        +dfs(visited, grid, x, y + 1, row, col)
    }
}

fun main() {

    var grid: Array<CharArray> = arrayOf(
        charArrayOf('0', '1', '0', '0', '0'),
        charArrayOf('0', '1', '0', '0', '0'),
        charArrayOf('0', '1', '0', '0', '0'),
        charArrayOf('0', '1', '0', '0', '0'),
    )

    println(SolutionB().maxAreaOfIsland(grid))
}