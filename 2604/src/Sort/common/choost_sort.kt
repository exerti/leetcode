package Sort.common

import kotlin.math.min

//往后边找最小的
fun choost_sort(array: Array<Int>):Array<Int> {
    val size = array.size
    for(i in 0 until size-1) {
        var  minIdx = i;
        for(j in i until size) {
            if(array[j] < array[minIdx]) {
                minIdx =j;
            }
        }
        if(minIdx!=i){
            val temp = array[i];
            array[i] = array[minIdx];
            array[minIdx] = temp;
        }
    }
    return array;
}


private fun print_array(array: Array<Int>) {
    for(j in 0 until array.size){
        print(array[j].toString()+"  ")
    }
}

fun main() {
    val array = arrayOf(6,7,8,9,10,1,2,3,4,5,6,7,8,9,10)
    val sort = choost_sort(array);
    print_array(sort)
}