
fun main(){
    val arr= arrayOf(3,2,4,6,5)
    val a=arr[0]
    val b=arr[1]
    val value=a+b
    val sum=value*value

    for (i in 2..<arr.size){
        val element=arr[i]
        val ans=element*element
        if (ans==sum){
            println("the sum of c index is $i and the sum value is $sum and the element itself is ${arr[i]}")
            break
        }
    }
}