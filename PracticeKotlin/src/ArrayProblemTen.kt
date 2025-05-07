import kotlin.math.max

fun main(){
    val arr= arrayOf(900, 940, 950, 1100, 1500, 1800)
    val dep= arrayOf(910, 1200, 1120, 1130, 1900, 2000)
    arr.sort()
    dep.sort()
    var maxCount=0
    var i=0
    while (i<arr.size){
        var count=0
        val DT=dep[i]
        count++
        if (count>maxCount){
            maxCount=count
        }
        while (i<arr.size-1 && DT>arr[i+1]){
            count++
            i++
            if (count>maxCount){
                maxCount=count
            }
        }
        i++
    }

    println(maxCount)
}