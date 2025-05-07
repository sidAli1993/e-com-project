
fun main(){
    val s="00001"
    val lastArray=s.toCharArray()
    val last=lastArray[lastArray.size-1]
    if (last.isDigit() && last.digitToInt()==1){
        println(lastArray.size-1)
    }else{
        println(-1)
    }
}