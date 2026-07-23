package new

/**
 * 用栈实现队列 (LeetCode 232)
 *
 * 核心 trick：两个栈倒一次，顺序就反转了
 *
 * Kotlin 栈的写法：
 *   Java 的 Stack 类已过时，Kotlin 推荐用 ArrayDeque<Int>()
 *   栈操作：addLast(e) = push    removeLast() = pop    last() = peek
 *   队列操作：addLast(e) = offer  removeFirst() = poll  first() = peek
 *
 *   所以 ArrayDeque 既可当栈用，也可当队列用，取决于你调哪个方法
 *
 * 手写推演：
 *   push 1 → push 2 → push 3 → pop() → push 4 → pop() → pop() → pop()
 *
 *   操作       stackIn      stackOut      说明
 *   ──────────────────────────────────────────────────────
 *   push 1     [1]          []
 *   push 2     [1,2]        []
 *   push 3     [1,2,3]      []
 *   pop()      []           [3,2,1]       stackOut 空 → 倒入; pop → 1
 *   push 4     [4]          [3,2]         只管入
 *   pop()      [4]          [3,2]         不空直接 pop → 2
 *   pop()      [4]          [3]           不空直接 pop → 3
 *   pop()      []           [4]           空 → 倒入 [4]; pop → 4
 *
 *   结果: 1,2,3,4 ✓  FIFO 生效
 */

class MyQueue {

    private val stackIn = ArrayDeque<Int>()
    private val stackOut = ArrayDeque<Int>()

    fun push(x: Int) {
        stackIn.addLast(x)
    }

    fun pop(): Int {
        dumpIfNeeded()
        return stackOut.removeLast()
    }

    fun peek(): Int {
        dumpIfNeeded()
        return stackOut.last()
    }

    fun empty(): Boolean {
        return stackIn.isEmpty() && stackOut.isEmpty()
    }

    // 只在 stackOut 空时才倒，保证 FIFO 顺序
    private fun dumpIfNeeded() {
        if (stackOut.isEmpty()) {
            while (stackIn.isNotEmpty()) {
                stackOut.addLast(stackIn.removeLast())
            }
        }
    }

}

// =====================================================================
// 思考题
// =====================================================================
/**
 * ▎变体1：用队列实现栈 (LeetCode 225)
 *   和这题的区别在哪里？一个队列够吗？
 *   每次 push 后把队列前面的元素全部取出重新入队 → 新元素跑到队头
 *
 * ▎变体2：均摊复杂度
 *   push: O(1)
 *   pop:  均摊 O(1) — 每个元素最多从 stackIn 移到 stackOut 一次
 *
 * ▎变体3：ArrayDeque 为什么比 Stack 好？
 *   Stack 继承 Vector，同步开销大；ArrayDeque 无锁，性能更好
 */
