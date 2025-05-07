
fun main(){
    val arr= arrayOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110)
    var lPos=0
    var rPos=arr.size-1
    val list= mutableListOf<Int>()

    while (rPos>=lPos){
        list.add(arr[rPos])
        if (rPos!=lPos){
            list.add(arr[lPos])
        }
        lPos++
        rPos--
    }
    println(list.joinToString(","))
}