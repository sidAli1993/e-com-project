package linked_list

fun main(){
    val linkedList:LinkedList= LinkedList()
    linkedList.insert(2)
    linkedList.insert("2")
    linkedList.insert("ABC")
    linkedList.insert("DEF")
    linkedList.insert(2.8)

//    linkedList.printNode()
    linkedList.printHead()
    linkedList.printTail()
}

data class Node(
    val value: Any,
    var node: Node?=null
)

public class LinkedList{
    private var head: Node?=null
    private var tail: Node?=null
    fun insert(value: Any){
        if (head==null){
            head= linked_list.Node(value)
            tail= linked_list.Node(value)
        }else{
            val newNode= linked_list.Node(value)
            newNode.node=head
            head=newNode
        }
    }

    fun printHead(){
        println(head?.value)
    }
    fun printTail(){
        println(tail?.value)
    }

    fun printNode(){
        var currentNode=head
        while (currentNode!=null){
            println(currentNode.value)
            currentNode=currentNode.node
        }
    }

 }

