
fun main(){
    val arr= arrayOf(2, 1, 5, 3, 1, 0, 4)
    var firstLeft=0
    var firstRight=0
    val leftArray= mutableListOf<Int>()
    val rightArray= mutableListOf<Int>()
    val totalCount=0

    for (i in arr.indices){
        val currentElement=arr[i]
        if (firstLeft>currentElement){
            leftArray.add(firstLeft)
        }else{
            firstLeft=currentElement
            leftArray.add(firstLeft)
        }
    }

    for (i in arr.size-1 downTo 0){
        val currentElement=arr[i]
        if (firstRight>currentElement){
            rightArray.add(firstRight)
        }else{
            firstRight=currentElement
            rightArray.add(firstRight)
        }
    }
    println(leftArray.joinToString(","))
    println(rightArray.joinToString(","))

    var count=0
    for (i in arr.indices){
        val oV=arr[i]
        val lV=leftArray[i]
        val rV=rightArray[i]
        if (lV<rV){
            val sum=lV-oV
            count+=sum
        }else{
            val sum=rV-oV
            count+=sum
        }
    }
    println(count)
}