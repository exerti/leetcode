fun shellSort(arr: ArrayList<Int>): ArrayList<Int> {
    val len = arr.size
    var gap = len / 2
    while (gap > 0) {
        println("--- gap = $gap ---")
        for (i in gap until len) {
            val temp = arr[i]
            var j = i
            while (j >= gap && arr[j - gap] > temp) {
                arr[j] = arr[j - gap]
                j -= gap
            }
            arr[j] = temp
            println("  插入 $temp 后: $arr")
        }
        gap /= 2
    }
    return arr
}

fun main() {
    // 原始数组: [8, 3, 7, 1, 5, 2, 9, 4]
    // gap=4: 分成4组 [8,5] [3,2] [7,9] [1,4] 各自排序
    // gap=2: 分成2组 [5,2,8,9] [1,3,4,7] 各自排序  (下标偶数一组，奇数一组)
    // gap=1: 普通插入排序，此时已基本有序
    val arr = arrayListOf(8, 3, 7, 1, 5, 2, 9, 4)
    println("排序前: $arr")
    shellSort(arr)
    println("排序后: $arr")
}



/**
这段是带间隔的插入排序，逐行拆解：
                                                                                
  for (i in gap until len)                                                      
  从下标 gap 开始遍历，i 是当前要插入的元素位置。                               
                                                                                
  ---                                                                           
  val temp = arr[i]                                                             
  var j = i                                                 
  把当前元素暂存到 temp，j 是比较用的指针，从 i 往左跳。

  ---                                                                           
  while (j >= gap && arr[j - gap] > temp) {
      arr[j] = arr[j - gap]                                                     
      j -= gap                                                                  
  }                                                                             
  这是核心：往左跳着比较（步长是 gap，不是1）                                   
                                                                                
  - arr[j - gap] 是左边间隔 gap 的元素                                          
  - 如果它比 temp 大，就把它右移 gap 位覆盖 arr[j]                              
  - j -= gap 继续向左跳                                                         
                                                                                
  举例，gap=4，数组 [8, 3, 7, 1, 5, ...]，当 i=4，temp=5：                      
  j=4, arr[0]=8 > 5 → arr[4]=8, j=0                                             
  j=0, 不满足 j>=gap，退出                                                      
  arr[0] = 5                                                                    
  结果: [5, 3, 7, 1, 8, ...]                                                    
                                                                                
  ---                                                                           
  arr[j] = temp                                             
  找到合适位置后，把暂存的 temp 放回去。                                        
                                                                                
  ---                                                                           
  一句话总结： 普通插入排序每次左移1步，这里左移 gap                            
  步——本质完全一样，只是步长变大了，所以能快速把元素移动到大致正确的位置。      
                                        
                                         */