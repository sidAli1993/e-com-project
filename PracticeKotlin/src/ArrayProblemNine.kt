fun main() {
    val arr = arrayOf(-7,1,5,2,-4,3,0)
    if (arr.size == 2) {
        println("there is no equilibrium point here because size is 2")
        return
    }
    if (arr.size == 1) {
        println("the equilibrium point is itself ${arr[0]}")
        return
    }
    val midIndex = arr.size / 2
    var lSum = 0
    var rSum = 0
    for (i in 0..<midIndex) {
        lSum += arr[i]
    }
    for (i in midIndex + 1..<arr.size) {
        rSum += arr[i]
    }
    if (lSum==rSum){
        println("brute force equilibrium index is $midIndex and value of index is ${arr[midIndex]}")
    }else{
        println(-1)
    }

//    better solution implementation
//        val arr = arrayOf(-7,1,5,2,-4,3,0)0
    var rightSum = 0
    var leftSum = 0
    rightSum = arr.sum()
    for (i in arr.indices) {//7,1,5,2
        rightSum -= arr[i]//7,6,1,-1
        if (leftSum==rightSum){//equal
            println(i)
            break
        }
        leftSum=arr.slice(0..i).sum()//-7,-6,-1,
    }
}