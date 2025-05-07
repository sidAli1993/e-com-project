fun main() {
    val arr = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val target = 15
    for (i in arr.indices) {
        var sum = arr[i]
        var arrOfPositions = ""

        for (j in (i + 1)..<arr.size) {
            sum += arr[j]
            if (sum == target) {
                arrOfPositions = "[${(i + 1)} to ${(j + 1)}]"
                println("from position $arrOfPositions is equal to $target")
                return
            }
        }
        if (i == (arr.size - 1)) {
            arrOfPositions = "-1"
            println(arrOfPositions)
        }
    }


}