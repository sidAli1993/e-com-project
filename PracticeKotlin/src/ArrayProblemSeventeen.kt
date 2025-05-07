
fun main(){
    val arr= arrayOf(4,4,3,5,7,6)

    for (i in 1..<arr.size-1){//1
        val valueToMatch=arr[i]//2
        var isPassed=true
        for (j in (i+1)..<arr.size){//2,3
            val cValue=arr[j]//5,7
            if (valueToMatch>cValue){
                isPassed=false
                break
            }
        }
        if (isPassed){
            for (k in (i-1) downTo 0){//0,
                val cValue=arr[k]//4
                if (valueToMatch<cValue){//2<4
                    isPassed=false
                    break
                }
            }
        }
        if (isPassed){ println(arr[i])} else println(-1)
    }
}