
fun main(){
    val listNum= listOf(1,2,3,4,5,6,7,8)
    val sum=listNum.sum()
    println(sum)
    val average=listNum.average()
    println(average)
    val accumulator=listNum.reduce { acc, value ->
        acc *value
    }
    println(accumulator)

    val fruits= listOf("apple","cherry","banana","orange")
    val fruitsFolds=fruits.fold(0){acc,value->
        acc+value.length
    }
    println(fruitsFolds)

}