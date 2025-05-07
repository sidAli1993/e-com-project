package bt_updated

class MQueue<T> {
   private val list= mutableListOf<T>()

    fun enqueue(item:T){
        list.add(item)
    }

    fun dequeue():T?{
        return if (list.isNotEmpty()) list.removeAt(0) else null
    }

    fun peek():T?{
        return list.firstOrNull()
    }

    fun isEmpty():Boolean{
        return list.isEmpty()
    }

    fun size():Int=list.size
}