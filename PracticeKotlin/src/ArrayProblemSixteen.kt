
fun main(){
    val arr= arrayOf(4, 2)
    var count=0
    for (i in arr.indices){
        val fE=arr[i]
        val sE=arr[if (i<arr.size-1) i+1 else i ]
        if (fE<sE){
            count+=sE-fE
        }
    }
    println(count)
}