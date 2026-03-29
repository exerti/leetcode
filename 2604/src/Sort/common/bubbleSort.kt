package Sort.common

import Sort.quick_sort

fun Bubble(array: Array<Int>):Array<Int> {
    val size = array.size
    for(i in 0 until size-1) {
        for( j in 0 until size-1-i) {
            if(array[j] > array[j+1]) {
                val temp = array[j]
                array[j] = array[j+1]
                array[j+1] = temp
            }
        }
    }
    return array;
}

fun print_array(array: Array<Int>) {
    for(j in 0 until array.size){
        print(array[j].toString()+"  ")
    }
}
fun main() {
    val array = arrayOf(6,7,8,9,10,1,2,3,4,5,6,7,8,9,10)
    val quick_sort = Bubble(array);
    print_array(quick_sort)
}