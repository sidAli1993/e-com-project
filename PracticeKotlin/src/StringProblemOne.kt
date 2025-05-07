
fun main(){
    val str=" i like this program very much "
    val reversedStr=str.reversed()
    println(reversedStr)
    var ans=""
    for (i in reversedStr.indices){
        var word=""
        while (i<reversedStr.length && reversedStr[i].toString()!=" "){
            word+=reversedStr[i]
        }
        if (word.isNotEmpty() && word!=" "){
            if (ans.isEmpty()){
                ans=word.reversed()
            }else{
                ans+=" ${word.reversed()}"
            }
        }
    }

}