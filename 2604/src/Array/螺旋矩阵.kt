    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        val row = matrix.size
        val col = matrix[0].size
        var top = 0
        var bottom = row - 1
        var left = 0
        var right = col - 1
        val result = mutableListOf<Int>()
        while (top <= bottom && left <= right) {
            for (i in left..right) result.add(matrix[top][i])
            top++

            for (i in top..bottom) result.add(matrix[i][right])
            right--

            if (top <= bottom) {
                for (i in right downTo left) result.add(matrix[bottom][i])
                bottom--
            }

            if (left <= right) {
                for (i in bottom downTo top) result.add(matrix[i][left])
                left++
            }
        }
        return result
    }




class Solution0720{
    
    fun spiralOrder(matrix: Array<IntArray>): List<Int>{
        val row = matrix.size
        val col = matrix[0].size
        var top = 0 
        var bottom = row-1
        var left = 0 
        var right = col-1
        val result = mutableListOf<Int>()
        while(top<=bottom&&left<=right){
            for(k in left..right) result.add(matrix[top][k])
            top++
            for(k in top..bottom) result.add(matrix[k][right])
            right--
            if(top<=bottom){
                
             for(k in right downTo left) result.add(matrix[bottom][k])
               bottom--
            }
           
            if(left<=right){
                 for(k in bottom downTo top) result.add(matrix[k][0])
                 left++
            }
            
        }
        return result
    }
}