package bt_updated

import binary_tree.Node
import binary_tree.Queue

fun main(){
    create()
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
    val height=findHeight(root)
    println(height)
    val balanced= checkIfTreeBalanced(root)
    println("the tree is balanced: $balanced")
}

fun findHeight(node: Node):Int{
    val queue=MQueue<Node>()
    var height=0
    queue.enqueue(node)

    while (!queue.isEmpty()){
        for (i in 0..queue.size()){
            val cn=queue.dequeue()
            if (cn?.left!=null) queue.enqueue(cn.left!!)
            if (cn?.right!=null)queue.enqueue(cn.right!!)
        }
        height++
    }
    return height
}

fun checkIfTreeBalanced(node:Node):Boolean{
    val leftHeight= findHeight(node.left!!)
    val rightHeight= findHeight(node.right!!)
    return leftHeight-rightHeight <= 1
}