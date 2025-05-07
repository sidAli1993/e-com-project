import kotlin.math.min

fun main(){

    val arr= arrayOf(2,1,5,4,7,6)

    for (i in 0..<arr.size-1){
        for (j in (i+1)..<arr.size){
            if (arr[i]>arr[j]){
                val swap=arr[i]
                arr[i]=arr[j]
                arr[j]=swap
            }
        }
    }
    println(arr.joinToString(","))
    val arr2= arrayOf(2,1,5,4,7,6)
    val sortedArray=SortObject.sortArray(arr2,arr2.size)
    println(sortedArray.joinToString(","))
    val sortDesc=SortObject.sortArrayDesc(arr2,arr2.size)
    println(sortDesc.joinToString(","))
}