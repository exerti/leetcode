private fun <T> ArrayDeque<T>.push(v: T) = addLast(v)
private fun <T> ArrayDeque<T>.pop() = removeLast()

fun isValid(s: String): Boolean {
    val stack = ArrayDeque<Char>()
    for (c in s) {
        when (c) {
            '(' -> stack.addLast(')')
            '[' -> stack.addLast(']')
            '{' -> stack.addLast('}')
            else -> {
                if (stack.isEmpty() || stack.removeLast() != c) return false
            }
        }
    }
    return stack.isEmpty()
}

fun main() {
    check(isValid("()"))
    check(isValid("()[]{}"))
    check(isValid("{[]}"))
    check(isValid(""))

    check(!isValid("(]"))
    check(!isValid("([)]"))
    check(!isValid("("))
    check(!isValid(")"))
    check(!isValid("({"))

    println("All test cases passed!")
}



fun isValid(s: String): Boolean {
    val stack = ArrayDeque<Char>()
    for (c in s) {
        when (c) {
            '(' -> stack.addLast(')')
            '[' -> stack.addLast(']')
            '{' -> stack.addLast('}')
            else -> {
                if (stack.isEmpty() || stack.removeLast() != c) return false
            }
        }
    }
    return stack.isEmpty()
}