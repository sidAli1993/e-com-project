fun main() {

    val arr = arrayOf(-2,-4,-8,100,-200,180)
    var totalSum=0
    for (i in arr.indices){
        totalSum+=arr[i]
        if (totalSum<0){
            totalSum=0
        }
    }
    println(totalSum)

//    to handle negative cases second way
    var maxSum=arr[0]
    var currentSum=arr[0]

    for (i in 1..<arr.size){
        currentSum= maxOf(arr[i],currentSum+arr[i])

        maxSum= maxOf(maxSum,currentSum)
    }
    println(maxSum)

//    third way to handle ass aspects of kadan'e algo
    var cSum=arr[0]
    var mSum=arr[0]

    for (i in 1..<arr.size){
        cSum=if (arr[i] > (cSum+arr[i])) arr[i] else cSum+arr[i]
        mSum=if (mSum>cSum) mSum else cSum
    }
    println(mSum)
}