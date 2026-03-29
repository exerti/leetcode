package Sort.common


fun insert_sort(array: Array<Int>):Array<Int> {
    val size = array.size
  for (i in 1 until size){
      val willInsertValue = array[i];
      var sortedIdx = i-1;

      while (sortedIdx>=0&&array[sortedIdx] >willInsertValue){
              array[sortedIdx+1]=array[sortedIdx];
              sortedIdx--;
      }
      array[sortedIdx+1] = willInsertValue;

  }
    return array;
}


fun main() {
    val array = arrayOf(6,7,8,9,10,1,2,3,4,5,6,7,8,9,10)
    val sort = insert_sort(array);
    print_array(sort)
}