
fun main(){

    val arr= arrayOf(5, 10, 15, 20, 25)
    var result=""
    var count=0
    for (i in arr.indices){
        val firstElement=arr[i]
        for (j in (i+1)..<arr.size){
            if (j!=arr.size-1){
                val secondElement=arr[j]
                val thirdElement=arr[j+1]
                val sum=firstElement+secondElement
                if (sum==thirdElement){
                    result+="$firstElement + $secondElement is equal to $thirdElement\n"
                    count+=1
                }
            }
        }
    }
    println(count)
    println(result)
}