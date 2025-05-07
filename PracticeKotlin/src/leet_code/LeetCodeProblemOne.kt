package leet_code

fun main() {
    var arr = arrayOf(2, 3, 4, 6)

    var tuple = 0
    for (i in arr.indices) {
        val fe=arr[i]
        for (j in (i + 1)..<arr.size) {
            val se=arr[j]
            for (k in arr.indices) {
                val te=arr[k]
                for (l in (k + 1)..<arr.size) {
                    if (i!=k && i!=l && j!=l && j!=k){
                        val fo=arr[l]

                        val p1=fe*se
                        val p2=te*fo
                        if (p1==p2){
                            tuple++
                        }
                    }
                }
            }
        }
    }
    val possibleOutputs=tuple*8
    println(possibleOutputs)
}