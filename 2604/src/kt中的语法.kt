import java.util.*

fun main() {
    // ==============================
    // 1. 数组
    // ==============================
    println("===== 1. 数组 =====")
    // 整数数组
    val intArray = intArrayOf(1, 2, 3, 4, 5)
    println("第一个元素：${intArray[0]}")

    // 字符串数组
    val strArray = arrayOf("Java", "Kotlin", "Android")
    println("字符串数组第二个：${strArray[1]}")


    // ==============================
    // 2. 栈 Stack（先进后出）
    // ==============================
    println("\n===== 2. 栈 =====")
    val stack = ArrayDeque<Int>()

    // 入栈
    stack.addLast(1)
    stack.addLast(2)
    stack.addLast(3)
    println("入栈后：$stack")

    // 查看栈顶（不删除）
    val top = stack.lastOrNull()
    println("栈顶元素：$top")

    // 出栈（删除并返回）
    val pop1 = stack.removeLast()
    println("出栈元素：$pop1")
    println("出栈后：$stack")

    val pop2 = stack.removeLast()
    println("再次出栈：$pop2")
    println("最终栈：$stack")


    // ==============================
    // 3. 队列（先进先出）
    // ==============================
    println("\n===== 3. 队列 =====")
    val queue = ArrayDeque<Int>()

    // 入队
    queue.add(10)
    queue.add(20)
    queue.add(30)
    println("入队后：$queue")

    // 查看队首
    val first = queue.firstOrNull()
    println("队首元素：$first")

    // 出队
    val out1 = queue.removeFirst()
    println("出队元素：$out1")
    println("出队后：$queue")


    // ==============================
    // 4. 哈希表（键值对）
    // ==============================
    println("\n===== 4. 哈希表 =====")
    val map = HashMap<Int, String>()

    // 插入
    map[1] = "张三"
    map[2] = "李四"
    map[3] = "王五"

    // 获取
    println("key=2 对应的值：${map[2]}")

    // 遍历
    for ((k, v) in map) {
        println("key:$k, value:$v")
    }

    // 删除
    map.remove(2)
    println("删除 key=2 后：$map")


    // ==============================
    // 5. LinkedList
    // ==============================
    println("\n===== 5. LinkedList =====")
    val list = LinkedList<Int>()

    list.add(1)
    list.add(2)
    list.addFirst(0)
    list.addLast(3)

    println("list：$list")
    println("第一个元素：${list.first}")
    println("最后一个元素：${list.last}")

    list.removeFirst()
    list.removeLast()
    println("删除首尾后：$list")

    // ==============================
    // 6. 空运算（?. ?: !! as?）
    // ==============================
    println("\n===== 6. 空运算 =====")
    var nullableStr: String? = null

    // ?. 安全调用：如果为 null 就跳过
    println("安全调用：${nullableStr?.length}") // null

    // ?: Elvis 运算符：如果为 null 就取默认值
    val len = nullableStr?.length ?: 0
    println("Elvis 默认值：$len") // 0

    // !! 强制解包：确定不为 null 时使用（为 null 会抛 NPE）
    nullableStr = "hello"
    println("强制解包：${nullableStr!!.length}") // 5

    // as? 安全转换：转换失败返回 null
    val obj: Any = 123
    println("安全转 String：${obj as? String}") // null
    println("安全转 Int：${obj as? Int}") // 123

    // ==============================
    // 7. sorted / sortedBy 排序
    // ==============================
    println("\n===== 7. sorted 排序 =====")
    val nums = arrayOf(3, 1, 4, 1, 5)

    // sorted() 自然升序，返回新 List
    val sortedNums = nums.sorted()
    println("sorted：$sortedNums")

    // sortedDescending() 降序
    val descNums = nums.sortedDescending()
    println("sortedDescending：$descNums")

    // sortedBy {} 按某个字段排序，返回新 List
    val intervals = arrayOf(intArrayOf(1, 4), intArrayOf(2, 3), intArrayOf(0, 5))
    val sortedByLeft = intervals.sortedBy { it[0] }
    println("sortedBy 左端点：${sortedByLeft.map { it.toList() }}")

    // sortedByDescending {} 按字段降序
    val sortedByRightDesc = intervals.sortedByDescending { it[1] }
    println("sortedByDescending 右端点：${sortedByRightDesc.map { it.toList() }}")

    // sortBy {} 原地排序（修改自身）
    val mutableNums = mutableListOf(3, 1, 2)
    mutableNums.sortBy { it }
    println("sortBy 原地排序：$mutableNums")

    // sortedWith + compareBy 多条件排序
    val items = listOf("ab", "abc", "a", "abcd")
    val byLength = items.sortedWith(compareBy({ it.length }, { it }))
    println("sortedWith 按长度再按字母：$byLength")

    // ==============================
    // 8. IntArray / Array 常用操作
    // ==============================
    println("\n===== 8. IntArray & Array =====")
    // toList / toTypedArray / toIntArray
    val intArr = intArrayOf(1, 2, 3)
    val strArr = arrayOf("a", "b", "c")

    println("IntArray toList：${intArr.toList()}")
    println("List toIntArray：${listOf(4, 5, 6).toIntArray().toList()}")
    println("Array toList：${strArr.toList()}")
    println("List toTypedArray：${listOf("x", "y").toTypedArray().toList()}")

    // first / last / firstOrNull / lastOrNull
    println("first：${intArr.first()}, last：${intArr.last()}")
    val emptyArr = intArrayOf()
    println("firstOrNull 空数组：${emptyArr.firstOrNull()}") // null
}