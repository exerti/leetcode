package dfs


class Solution {
    fun numIslands(grid: Array<CharArray>): Int {
        if (grid.isEmpty() || grid[0].isEmpty()) return 0
        var row = grid.size
        var col = grid[0].size
        val visited = Array(row) { IntArray(col) { 0 } }
        var count = 0;
        for (i in 0 until row) {
            for (j in 0 until col) {
                if (visited[i][j] == 1 || grid[i][j] == '0') {
                    continue
                }
                count++
                dfs(visited, grid, i, j, row, col)

            }
        }
        return count

    }

    private fun dfs(
        visited: Array<IntArray>, // 错误 2：拼写错误 visted → visited
        grid: Array<CharArray>,

        x: Int, y: Int, row: Int, col: Int
    ) {
        if (x < 0 || x >= row || y < 0 || y >= col) {
            return
        }
        if (visited[x][y] == 1) {
            return
        }
        if (grid[x][y] == '0') {
            return
        }
        visited[x][y] = 1
        dfs(visited, grid, x + 1, y, row, col)
        dfs(visited, grid, x - 1, y, row, col)
        dfs(visited, grid, x, y - 1, row, col)
        dfs(visited, grid, x, y + 1, row, col)
    }
}

/**
 * 输入：grid = [
 *   ['1','1','1','1','0'],
 *   ['1','1','0','1','0'],
 *   ['1','1','0','0','0'],
 *   ['0','0','0','0','0']
 * ]
 */


/**
 * 1、边界条件
 * 2、初始换一个染色的 同等大小的数组
 * 3、 初始化dfs的遍历
 * 4、 dfs
 */

fun getNumFromIslands(grid: Array<CharArray>): Int {

    if (grid.isEmpty() || grid[0].isEmpty()) return 0
    var row = grid.size
    var col = grid[0].size
    val visited = Array(row) { IntArray(col) { 0 } }
    var count = 0

    for( i in 0 until row) {
        for (j in 0 until col) {
            if (visited[i][j] == 1|| grid[i][j] == '0') {
                continue
            }
            dfs(visited,grid, i, j, row, col)
        }
    }
    return  count

}

fun dfs(visited: Array<IntArray>,grid: Array<CharArray>, x: Int, y: Int, row: Int, col: Int) {
    if (x < 0 || x >= row || y < 0 || y >= col) {
        return
    }
    if (visited[x][y] == 1) {
        return
    }
    if (grid[x][y] == '0') {
        return
    }
    visited[x][y] = 1
    dfs(visited, grid, x + 1, y, row, col)
    dfs(visited, grid, x - 1, y, row, col)
    dfs(visited, grid, x, y - 1, row, col)
    dfs(visited, grid, x, y + 1, row, col)

}