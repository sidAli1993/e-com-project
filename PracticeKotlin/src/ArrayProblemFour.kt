import kotlin.math.max

fun main() {
    val arr = arrayOf(5, 3, 2, 1, 6, 8,7,0)
    arr.sort()
    println(arr.joinToString(","))
    val maxNum=arr.maxOrNull() ?: 0
    println(maxNum)
    val sum=maxNum *(maxNum+1)/2
    println(sum)
    val missing=sum-arr.sum()
    println(missing)


//    by XOR technique single missing number
    var allXor = 0

// XOR all numbers from 1 to n (where n = arr.size + 1)
    var i = 0
    while (i <= arr.size ) {  // Should be arr.size + 1
        allXor = allXor xor i
        i++
    }

    println("XOR from 1 to N: $allXor")

// XOR all elements in the array
    for (num in arr) {
        allXor = allXor xor num
    }
    println(allXor)
}