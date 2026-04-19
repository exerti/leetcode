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
}