
fun main() {
    val arr = arrayOf(1, 2, 3, 5,4)
    val k=3
    val reversedArray= SortObject.reverseArrayIfAscOrDesc(arr = arr, n = k)
    println(reversedArray.joinToString(","))
}