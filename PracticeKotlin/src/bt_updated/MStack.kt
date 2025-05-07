package bt_updated

class MStack<T> {
    private val list= mutableListOf<T>()

    fun push(item:T){
        list.add(0,item)
    }

    fun pop():T?{
        return list.removeLastOrNull()
    }

    fun peek():T?{
        return list.lastOrNull()
    }

    fun isEmpty():Boolean = list.isEmpty()

    fun size():Int=list.size

}