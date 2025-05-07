package leet_code

fun main(){
    val arr1= arrayOf(1,2,3,0,0,0)
    val arr2= arrayOf(2,5,6)
    val m=3
    val n=3

    for (i in 0..<m){
        val element=arr2[i]
        if (element<arr1[0]){
            val swap=arr1[0]
            arr1[0]=element
            arr1[1]=swap
        }else{
            var j=0
            while (element>arr1[j] && j<m){
                j++
            }

            val swap=arr1[j]
            arr1[j]=element
            arr1[j+1]=swap
        }
    }
    println(arr1.joinToString(","))
}