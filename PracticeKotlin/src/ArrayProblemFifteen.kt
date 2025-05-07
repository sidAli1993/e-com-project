import kotlin.math.min

fun main(){
    val arr= arrayOf(3, 4, 1, 9, 56)
    val sortedArray=SortObject.sortArray(arr,arr.size)
    println(sortedArray.joinToString(","))
    val m=5
    var difference=0
    var count=0
    for (i in(m-1)..<arr.size){
        val SE=arr[count]
        val FE=arr[i]
        val minValue=FE-SE
        if (difference==0){
            difference=minValue
        }
        if (minValue<difference){
            difference=minValue
        }
        count++
    }
    println(difference)
}