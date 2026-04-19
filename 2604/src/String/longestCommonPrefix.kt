

    fun longestCommonPrefix(strs: Array<String>): String {
     if(strs.isEmpty()) return ""
    val minLen = strs.minOf { it.length }
     for(i in 0 until minLen){
        if(strs.any{it[i]!=strs[0][i]}){
           return strs[0].substring(0, i)
        }
     }
     return strs[0].substring(0,minLen);
    }