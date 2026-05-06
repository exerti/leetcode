package LRU

class ManualLruCache<K, V>(private val capacity: Int) {
    //双向链表+哈希表
    // 双向链表节点
    //1、内部结点
    private data class Node<K, V>(
        val key: K?,
        var value: V?,
        var prev: Node<K, V>?,
        var next: Node<K, V>?
    )
    // 哈希表：O(1) 查找节点
    private val map = HashMap<K, Node<K, V>>()
    // 虚拟头节点、虚拟尾节点（简化边界判断）
    private val head: Node<K, V> = Node(null, null, null, null)
    private val tail: Node<K, V> = Node(null, null, null, null)
    //初始化
    init {
        head.next = tail
        tail.prev = head
    }
    //外部方法， get  set
    fun get(key: K): V? {
        val node = map[key]
        if (node == null) return null
        moveToHead(node)
        return node.value
    }
    
    fun set(key: K, value: V) {
        val existingNode = map[key]
        if (existingNode != null) {
            // 更新已存在的键
            existingNode.value = value
            moveToHead(existingNode)
        } else {
            // 添加新节点
            val newNode = Node(key, value, null, null)
            map[key] = newNode
            addToHead(newNode)
            // 检查容量，超出则删除最久未使用的节点
            if (map.size > capacity) {
                val removed = removeTail()
                removed?.key?.let { map.remove(it) }
            }
        }
    }

    // 删除结点、添加到头部、移动到头部、删除尾部
    private fun removeNode(node: Node<K, V>) {
         node.prev?.next = node.next
         node.next?.prev = node.prev
    }

    private fun addToHead(node: Node<K, V>) {
        node.prev = head
        node.next = head.next
        node.next?.prev = node
        head.next = node
    }

    private fun moveToHead(node: Node<K, V>) {
        removeNode(node)
        addToHead(node)
    }

    private fun removeTail(): Node<K, V>? {
        val tailNode = tail.prev
        if (tailNode === head) return null
        tailNode?.let { removeNode(it) }
        return tailNode
    }
}



class  TLRU<K,V>(private let capacity:Int) {

    private data class TNode<K,V> {
        val key: K?
        var value: V?
        var pre: TNode<K,V>?
        var next: TNode<K,V>?
    }

    var hashMap =  HashMap<K, Node<K, V>>()

    var head :TNode<K,V> =TNode(null,null,null,null)
    var tail :TNode<K,V> =TNode(null,null,null,null)

    init{
        head.next = tail
        tail.pre = head
    }


    fun get(key:K):V?{
       var node=  hashmap[key]
       if(node==null)  return null
       moveToHead(node)
       return node.value
    }

    fun set(key:K,value:V){
        val existingNode = map[key]
        if(existingNode){
           moveToHead(node)
           map[key]=value
        }else{
            var newNode = TNode(key,value,null,null)
            map[key] = newNode
            addToHead(newNode)
            if(hashmap.size>capacity){
                val removed = removeTail()
                removed?.key?.let { map.remove(it) }
            }
        }

    }

    fun removeNode(key:K){

    }
    fun addToHead(){

    }

    fun moveToHead(){

    }

    fun deleteTail(){
     
    }

    

    
}