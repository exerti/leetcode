package other

fun compareVersion(version1: String, version2: String): Int {
    val parts1 = version1.split(".").map { it.toInt() }
    val parts2 = version2.split(".").map { it.toInt() }
    val maxLen = maxOf(parts1.size, parts2.size)

    for (i in 0 until maxLen) {
        val v1 = if (i < parts1.size) parts1[i] else 0
        val v2 = if (i < parts2.size) parts2[i] else 0
        if (v1 < v2) return -1
        if (v1 > v2) return 1
    }
    return 0
}