
fun main(){
    val arr= arrayOf(2, 8, 1, 7, 5, 9)
    var isPassed=true
    for (i in 0..<arr.size-1){//0
        val fe=arr[i]//4
        val se=arr[i+1]//7
        if (i%2==0){
            if (fe>se){
                isPassed=false
                break
            }
        }else{
            if (fe<se){
                isPassed=false
                break
            }

        }
    }
    println(isPassed)
}