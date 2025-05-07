package binary_tree

fun main(){
    val bt=BT()
    bt.create()
}
class Stack<T>{
    val stackList= mutableListOf<T>()

    fun push(value:T){
        stackList.add(value)
    }
    fun pop():T?{
        return stackList.removeLast()
    }
    fun peek():T?{
        return stackList.lastOrNull()
    }

    fun top():T?=stackList.lastOrNull()

    fun isEmpty():Boolean=stackList.isEmpty()

    fun size():Int=stackList.size
}

class BT{
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
//        levelOrder(root)
//        iteratePreOrder(root)
//        iteratePreOrder(root)
//        iterateLevelOrderTwo(root)
//        iterativeInOrderThree(root)
//        iteratePostOrderTwo(root)

//        preOrderIterativeTwo(root)
        levelOrderIterativeTraversalTwo(root)
    }

    private fun iteratePreOrder(root: Node){
        val stack=Stack<Node>()
        val list= mutableListOf<Int>()
        stack.push(root)
        while (!stack.isEmpty()){
            val node=stack.pop()
            list.add(node?.root as Int)
            if (node.right!=null){
                stack.push(node.right!!)
            }
            if (node.left!=null){
                stack.push(node.left!!)
            }
        }
        println("iterate pre order "+list.joinToString(","))
    }

    private fun iterativePreOrderTwo(node:Node?){
        if (node==null) return
        val stack=Stack<Node>()
        val list= mutableListOf<Int>()
        stack.push(node)
        while (!stack.isEmpty()){
            val root=stack.pop()
            list.add(root?.root as Int)
            if (root.left!=null){
                stack.push(root.left!!)
            }
            if (root.right!=null){
                stack.push(root.right!!)
            }
        }
        println(list.joinToString("-"))
    }

    private fun iterativeInOrderThree(node: Node){
        val stack=Stack<Node>()
        val list= mutableListOf<Any>()
        var cn:Node?=null
        cn=node
        while (true){
            if (cn!=null){
                stack.push(cn)
                cn=cn.left
            }else{
                if (stack.isEmpty()){
                    break
                }
                cn=stack.pop()
                list.add(cn?.root as Any)
                cn=cn.right
            }
        }
        println(list.joinToString(","))
    }

    fun levelOrderIterativeTraversalTwo(node: Node){
        val queue=Queue<Node>()
        val mainList= mutableListOf<List<Any>>()
        queue.enqueue(node)
        while (!queue.isEmpty()){
            val size=queue.size()
            val subList= mutableListOf<Any>()
            val root=queue.peek()
            for (i in 0..<size){
                if (root?.left!=null){
                    queue.enqueue(root.left!!)
                }
                if (root?.right!=null){
                    queue.enqueue(root.right!!)
                }
                subList.add(queue.dequeue()?.root as Any)
            }
            mainList.add(subList)
        }
        println(mainList.joinToString(","))
    }

    private fun preOrderIterativeTwo(node: Node){
        val stack=Stack<Node>()
        val list= mutableListOf<Any>()
        stack.push(node)
        while (!stack.isEmpty()){
            val root=stack.pop()
            if (root?.right!=null){
                stack.push(root.right!!)
            }
            if (root?.left!=null){
                stack.push(root.left!!)
            }
            list.add(root?.root as Any)
        }
        println(list.joinToString(","))
    }

    private fun iteratePostOrderTwo(root:Node){
        val st1=Stack<Node>()
        val st2=Stack<Node>()
        val list= mutableListOf<Any>()
        st1.push(root)
        while (!st1.isEmpty()){
            st2.push(st1.pop()!!)
            if (st2.peek()?.left!=null){
                st1.push(st2.peek()?.left!!)
            }
            if (st2.peek()?.right!=null){
                st1.push(st2.peek()?.right!!)
            }
        }
        while (!st2.isEmpty()){
            list.add(st2.pop()?.root as Any)
        }
        println(" iterative post order two: ${list.joinToString(",")}")
    }
    private fun iterateLevelOrderTwo(node:Node){
        val queue=Queue<Node>()
        val mainList= mutableListOf<List<Int>>()
        queue.enqueue(node)
        while (!queue.isEmpty()){
            val subList= mutableListOf<Int>()
            val level=queue.size()
            for (i in 0..<level){
                if (queue.peek()?.left!=null){
                    queue.enqueue(queue.peek()?.left!!)
                }
                if (queue.peek()?.right!=null){
                    queue.enqueue(queue.peek()?.right!!)
                }
                subList.add(queue.dequeue()?.root as Int)
            }
            mainList.add(subList)
        }
        println(mainList.joinToString(","))
    }



    private fun levelOrder(root:Node){
        val queue=Queue<Node>()
        val mainList= mutableListOf<List<Any>>()
        queue.enqueue(root)
        while (!queue.isEmpty()){
            val subList= mutableListOf<Any>()
            val size=queue.size()
            for (i in 0..<size){
                if ((queue.peek() as Node).left!=null){
                    queue.enqueue((queue.peek() as Node).left!!)
                }
                if ((queue.peek() as Node).right!=null){
                    queue.enqueue((queue.peek() as Node).right!!)
                }
                subList.add(queue.dequeue()!!.root)
            }
            mainList.add(subList)
        }
        println(mainList.joinToString(","))
    }
}