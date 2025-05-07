
fun main(){
    val arr= arrayOf(5, 3, 8, 4, 2)
    var temp=0
    for (i in 1..<arr.size){
        val ce=arr[i]
        temp=ce
        for (j in (i-1) downTo 0){
            val le=arr[j]
            if (temp<le){
                arr[j+1]=le
                arr[j]=temp
            }
        }
    }

    println(arr.joinToString(","))
}
