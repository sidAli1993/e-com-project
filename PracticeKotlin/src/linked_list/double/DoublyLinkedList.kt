package linked_list.double


fun main(){

    val linkedList:DoublyLinkedList= DoublyLinkedList()
    linkedList.insert(2)
    linkedList.insert(4)
    linkedList.insert(5)
    linkedList.insert(7)

    linkedList.printAllNodes()
    linkedList.printHead()
    linkedList.printTail()
    linkedList.printCompleteNode()

    val headFind=linkedList.findFromHead(5)
    val tailFind=linkedList.findFromTail(9)
    println("head find $headFind")

    println("tail find $tailFind")
}

data class Node(
    val value:Any,
    var next: Node?=null,
    var prev:Node?=null
){
    override fun toString(): String {
        return "prev value is ${prev?.value ?: "**"} ------> current value is $value ----------------> next value is ${next?.value ?: "**"}"
    }
}

class DoublyLinkedList{
    private var head:Node?=null
    private var tail:Node?=null

    fun insert(value:Any){
        if (head==null){
            head=Node(value)
            tail=Node(value,null,head)
        }else{
            val newNode=Node(value)
            newNode.next=head
            head?.prev=newNode
            head=newNode
        }
    }

    fun printAllNodes(){
        var currentNode=head

        while (currentNode!=null){
            println(currentNode)
            currentNode=currentNode.next
        }
    }
    fun printCompleteNode(){
        println(head.toString())
    }

    fun printHead(){
        println(head?.value)
    }
    fun printTail(){
        tail?.value
    }

    fun findFromHead(value:Any):Node?{
        var current= head
        if (current?.value==value){
            return current
        }
        while (current!=null){
            if (current.value==value)
                break
            else
                current=current.next
        }
        return current
    }

    fun findFromTail(value: Any):Node?{
        var current=tail
        if (current?.value==value){
            return current
        }
        while (current!=null && current!=head){
            if (current.value==value){
                break
            }else{
                current=current.prev
            }
        }
        return current
    }

    fun deleteLinkedList(value: Any){

    }
}