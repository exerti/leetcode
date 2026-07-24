package new

/**
 * LFU 缓存 (LeetCode 460)
 * 设计一个满足 LFU (最不经常使用) 缓存约束的数据结构。
 *
 * 关键要求：get 和 put 都是 O(1)
 */

class LFUCache(val capacity: Int) {

    // =====================================================================
    // 数据结构设计引导
    // =====================================================================
    /**
     * ▎LRU vs LFU
     *   LRU: 淘汰"最久没被访问"的           → 时间维度（单线）
     *   LFU: 淘汰"访问次数最少"的           → 频率维度（二维）
     *   频率相同时，淘汰"最久未访问"的那个  → 同频内再套 LRU
     *
     * ▎LFU 需要什么数据结构？
     *   ① O(1) 查节点                → HashMap<Key, Node>
     *   ② O(1) 按频率找到节点集合    → HashMap<Freq, LinkedHashSet<Key>>
     *      — LinkedHashSet 保持插入顺序，同频率内按时间淘汰
     *   ③ O(1) 知道当前最小频率      → 维护一个 minFreq 变量
     *
     * ▎Node 需要存什么？
     *   LRU 的 Node: key + value + prev + next
     *   LFU 的 Node: key + value + freq (多了频率)
     *
     * ▎操作逻辑
     *   get(key):
     *     1. 不存在 → 返回 -1
     *     2. 存在 → 频率+1，从旧 freq 集合移到新 freq 集合，返回值
     *
     *   put(key, value):
     *     1. 已存在 → 更新值，频率+1（同 get 的步骤2）
     *     2. 不存在，没满 → 新建节点，freq=1，加入 freq=1 集合，minFreq=1
     *     3. 不存在，满了 → 淘汰 minFreq 中最旧的，再插入新节点
     *
     * ▎minFreq 如何维护？
     *   - 新节点插入时 → minFreq = 1
     *   - 某节点频率+1后，旧频率集合变空且旧频率==minFreq → minFreq++
     *
     * ▎手写推演 capacity=2
     *   put(1,1): node1{freq=1}, freqMap[1]={1}, minFreq=1
     *   put(2,2): node2{freq=1}, freqMap[1]={1,2}, minFreq=1
     *   get(1):   1 freq 1→2, freqMap[1]={2} 没空, minFreq still 1
     *             freqMap[2]={1}
     *   put(3,3): 满了→淘汰 minFreq=1 中最旧的(2)
     *             node3{freq=1}, freqMap[1]={3}, minFreq=1
     *   get(3):   3 freq 1→2, freqMap[1] 空了→minFreq=2!
     *             freqMap[2]={1,3}
     *   get(1):   1 freq 2→3, freqMap[2]={3} 没空, minFreq still 2
     *   get(3):   3 freq 2→3, freqMap[2] 空了→minFreq=3!
     */

    // TODO: 定义内部 Node 类 — key + value + freq
    // TODO: keyMap: HashMap<Int, Node> — O(1) 查找
    // TODO: freqMap: HashMap<Int, LinkedHashSet<Int>> — 频率 → 有序key集合
    // TODO: minFreq: Int — 当前最小频率

    fun get(key: Int): Int {
        TODO()
    }

    fun put(key: Int, value: Int) {
        TODO()
    }

    // 辅助方法提示：
    // private fun updateFreq(node) — 频率+1，从旧集合移到新集合，维护 minFreq
}

// =====================================================================
// 解法对比 — 填表
// =====================================================================
/**
 * ┌────────────────────┬──────────────────────────────────────────┐
 * │  LFU vs LRU        │  一句话对比                              │
 * ├────────────────────┼──────────────────────────────────────────┤
 * │  LFU 数据结构      │  keyMap + freqMap + minFreq             │
 * │  LRU 数据结构      │  keyMap + 双向链表 + 虚拟头尾            │
 * │  LFU 淘汰策略      │  频次低的先淘汰，同频按 LRU               │
 * │  LRU 淘汰策略      │  最久未访问的先淘汰                      │
 * │  共同点            │  都是 HashMap + 某种有序结构              │
 * │  差异              │  LFU 多一个频率维度 → 结构复杂一倍       │
 * └────────────────────┴──────────────────────────────────────────┘
 */
