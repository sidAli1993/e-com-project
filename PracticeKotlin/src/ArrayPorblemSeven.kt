
fun main(){
    val arr= arrayOf(2, 4, 1, 3, 5)
    var count=0
    for (i in arr.indices){
        val firstElement=arr[i]
        for (j in (i+1)..<arr.size){
            val innerElement=arr[j]
            if (innerElement<firstElement){
                count++
            }
        }
    }
    println(count)

}