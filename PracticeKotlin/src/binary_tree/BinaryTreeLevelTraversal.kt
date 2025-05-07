package binary_tree


fun main() {
    val binaryTreeLevelTraversal=BinaryTreeLevelTraversal()
    binaryTreeLevelTraversal.create()

}

class Queue<T> {
    private val list = mutableListOf<T>()

    fun enqueue(item: T) {
        list.add(item)
    }

    fun dequeue(): T? {
        return if (list.isNotEmpty()) list.removeAt(0) else null
    }

    fun peek(): T? {
        return list.firstOrNull()
    }

    fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    fun size(): Int = list.size
}

class BinaryTreeLevelTraversal {
    private fun displayLevelOrderTraversal(node:Node?):List<List<Int>>{
       val queue=Queue<Node>()
        val list= mutableListOf<List<Int>>()
        if (node==null) return list
        queue.enqueue(node)
        while (!queue.isEmpty()){
            val subList = mutableListOf<Int>()
            val levelNo=queue.size()
            println(levelNo)
            for (i in 0..<levelNo){
                queue.peek()?.let {
                    it.left?.let { l->
                        queue.enqueue(l)
                    }
                    it.right?.let { r->
                        queue.enqueue(r)
                    }
                }
                subList.add(queue.dequeue()?.root as Int)
            }
            list.add(subList)
        }
        println(list.joinToString(","))
        return list
    }

    fun create(){
        val root=Node(1)
        val a=Node(2)
        val b=Node(3)
        root.left=a
        root.right=b

        val c=Node(4)
        val d=Node(5)
        a.left=c
        a.right=d

        val e=Node(6)
        val f=Node(7)
        b.left=e
        b.right=f

        displayLevelOrderTraversal(root)
    }
}
