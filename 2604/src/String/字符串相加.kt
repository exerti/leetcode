// 大数四则运算（字符串表示的非负整数）

// 加法
fun addStrings(num1: String, num2: String): String {
    val sb = StringBuilder()
    var i = num1.lastIndex
    var j = num2.lastIndex
    var carry = 0
    while (i >= 0 || j >= 0 || carry > 0) {
        val x = if (i >= 0) num1[i--] - '0' else 0
        val y = if (j >= 0) num2[j--] - '0' else 0
        carry += x + y
        sb.append(carry % 10)
        carry /= 10
    }
    return sb.reverse().toString()
}

// 减法（num1 >= num2）
fun subtractStrings(num1: String, num2: String): String {
    val sb = StringBuilder()
    var i = num1.lastIndex
    var j = num2.lastIndex
    var borrow = 0
    while (i >= 0) {
        val x = num1[i--] - '0' - borrow
        val y = if (j >= 0) num2[j--] - '0' else 0
        val diff = if (x < y) { borrow = 1; x + 10 - y } else { borrow = 0; x - y }
        sb.append(diff)
    }
    while (sb.length > 1 && sb.last() == '0') sb.deleteCharAt(sb.lastIndex)
    return sb.reverse().toString()
}

// 乘法
fun multiplyStrings(num1: String, num2: String): String {
    if (num1 == "0" || num2 == "0") return "0"
    val m = num1.length
    val n = num2.length
    val arr = IntArray(m + n)
    for (i in (m - 1) downTo 0) {
        for (j in (n - 1) downTo 0) {
            val mul = (num1[i] - '0') * (num2[j] - '0')
            val sum = mul + arr[i + j + 1]
            arr[i + j] += sum / 10
            arr[i + j + 1] = sum % 10
        }
    }
    return arr.joinToString("").trimStart('0').ifEmpty { "0" }
}

// 除法
fun divideStrings(dividend: String, divisor: String): String {
    if (divisor == "0") throw ArithmeticException("Division by zero")
    if (dividend == "0" || smaller(dividend, divisor)) return "0"
    val sb = StringBuilder()
    var i = 0
    var cur = ""
    while (i < dividend.length) {
        cur += dividend[i]
        cur = cur.trimStart('0').ifEmpty { "0" }
        i++
        if (smaller(cur, divisor)) continue
        var q = 0
        while (!smaller(cur, divisor)) {
            cur = subtractStrings(cur, divisor)
            q++
        }
        sb.append(q)
    }
    return sb.toString().trimStart('0').ifEmpty { "0" }
}

// 取模
fun modStrings(dividend: String, divisor: String): String {
    if (divisor == "0") throw ArithmeticException("Division by zero")
    if (dividend == "0" || smaller(dividend, divisor)) return dividend
    var i = 0
    var cur = ""
    while (i < dividend.length) {
        cur += dividend[i]
        cur = cur.trimStart('0').ifEmpty { "0" }
        i++
        if (smaller(cur, divisor)) continue
        var q = 0
        while (!smaller(cur, divisor)) {
            cur = subtractStrings(cur, divisor)
            q++
        }
    }
    return cur.ifEmpty { "0" }
}

// 比较 a < b（均为非负整数字符串，不含前导零）
private fun smaller(a: String, b: String): Boolean {
    if (a.length != b.length) return a.length < b.length
    for (i in a.indices) {
        if (a[i] != b[i]) return a[i] < b[i]
    }
    return false // equal
}

fun main() {
    // 加法
    check(addStrings("11", "123") == "134")
    check(addStrings("456", "77") == "533")
    check(addStrings("0", "0") == "0")
    check(addStrings("999", "1") == "1000")

    // 减法
    check(subtractStrings("123", "11") == "112")
    check(subtractStrings("1000", "1") == "999")
    check(subtractStrings("5", "5") == "0")
    check(subtractStrings("500", "1") == "499")

    // 乘法
    check(multiplyStrings("2", "3") == "6")
    check(multiplyStrings("123", "456") == "56088")
    check(multiplyStrings("0", "123") == "0")
    check(multiplyStrings("999", "999") == "998001")

    // 除法
    check(divideStrings("10", "3") == "3")
    check(divideStrings("100", "7") == "14")
    check(divideStrings("0", "5") == "0")
    check(divideStrings("7", "2") == "3")

    // 取模
    check(modStrings("10", "3") == "1")
    check(modStrings("100", "7") == "2")
    check(modStrings("5", "8") == "5")

    println("All test cases passed!")
}
