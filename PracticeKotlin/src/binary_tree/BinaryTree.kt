package binary_tree

fun main(){
    val createTree=CreateTree()
    createTree.create()
}

data class Node(
    val root:Any,
    var left:Node?=null,
    var right:Node?=null
)

class CreateTree(){
    private fun displayPreOrder(node:Node?){
        if (node==null) return
        println("${node.root} --> ${node.left?.root ?: "null"} and to ${node.right?.root ?: "null"}")
        displayPreOrder(node.left)
        displayPreOrder(node.right)
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
        b.right=e
        displayPreOrder(root)
    }
}