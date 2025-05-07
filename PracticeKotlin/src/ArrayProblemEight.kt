
fun main(){
    val arr= arrayOf(0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1)
//    arr.sort()
//    println(arr.joinToString(","))

    var countZero=0
    var countOne=0
    var countTwo=0

    for (i in arr){
        when(i){
            0->countZero++
            1->countOne++
            2->countTwo++
        }
    }
    for (i in 0..<countZero){
        arr[i]=0
    }
    for (i in countZero..<(countZero+countOne)){
        arr[i]=1
    }
    for (i in (countZero+countOne)..<arr.size){
        arr[i]=2
    }

    println(arr.joinToString(","))
}