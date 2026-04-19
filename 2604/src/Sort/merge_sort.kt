package Sort

fun merge_sort(array: ArrayList<Int>): ArrayList<Int> {
    val size = array.size
    if (size <= 1) return array
    val mid = size / 2
    val left = merge_sort(ArrayList(array.subList(0, mid)))
    val right = merge_sort(ArrayList(array.subList(mid, size)))
    return merge(left, right);
}

fun merge(left: ArrayList<Int>, right: ArrayList<Int>): ArrayList<Int> {
    val array = ArrayList<Int>()
    var i = 0
    var j = 0
    while (i < left.size && j < right.size) {
        if (left[i] <= right[j]) {
            array.add(left[i++])
        } else {
            array.add(right[j++])
        }
    }
    while (i < left.size) {
        array.add(left[i++])
    }
    while (j < right.size) {
        array.add(right[j++])
    }
    return array

}

private fun print_array(array: ArrayList<Int>) {
    for (j in 0 until array.size) {
        print(array[j].toString() + "  ")
    }
}

fun main() {
    var array = ArrayList<Int>()
    array.add(5)
    array.add(6)
    array.add(7)
    array.add(1)
    array.add(2)
    array.add(3)
    array.add(4)
    array.add(7)
    array.add(1)
    array.add(2)
    array.add(3)

    val quick_sort = merge_sort(array);
    print_array(quick_sort)
}


fun sort_0407(array:ArrayList<Int>):ArrayList<Int>{
  var size = array.size;
  if(size<=1) return array;
  var mid = size/2
  var left =  sort_0407(ArrayList(array.subList(0,mid)))
  var right =  sort_0407(ArrayList(array.subList(mid,size)))
  return merge_0407(left,right)
}
fun merge_0407(left:ArrayList<Int>,right:ArrayList<Int>):ArrayList<Int>{
  val array = ArrayList<Int>()
  var i =0 
  var j = 0 
  while(i<left.size&&j<right.size){
     if (left[i] <= right[j]) {
            array.add(left[i++])
        } else {
            array.add(right[j++])
        }
  }
   while (i < left.size) {
        array.add(left[i++])
    }
    while (j < right.size) {
        array.add(right[j++])
    }

    return array
}