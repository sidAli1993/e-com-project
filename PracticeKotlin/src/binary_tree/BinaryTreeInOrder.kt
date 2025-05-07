package binary_tree

fun main(){

    val tree=BinaryInOrder()
    tree.create()

}

class BinaryInOrder{

    private fun displayPreOrder(root:Node?){
        if (root==null)
            return

        displayPreOrder(root.left)
        println(root.root)
        displayPreOrder(root.right)
    }

    private fun displayPostOrder(root:Node?){
        if (root==null)
            return

        displayPostOrder(root.left)
        displayPostOrder(root.right)
        println(root.root)
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

        displayPreOrder(root)
        displayPostOrder(root)
    }
}
