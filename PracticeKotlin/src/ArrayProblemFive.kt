
fun main(){
    val arr1= arrayOf(1, 5, 9, 10, 15, 20)
    val arr2= arrayOf(2, 3, 8, 13)
    val merged=arr1+arr2
    merged.sort()
    println(merged)

    println("first array ${merged.slice(0..<(arr1.size)).joinToString(",")}")

    println("second array ${merged.slice((arr1.size) ..< merged.size).joinToString(",")}")
}