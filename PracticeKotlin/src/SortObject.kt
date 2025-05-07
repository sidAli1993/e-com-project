object SortObject {

    fun sortArray(arr: Array<Int> = arrayOf(),n:Int): Array<Int> {
        for (i in 0..<(n - 1)) {
            for (j in (i + 1)..<n) {
                if (arr[i] > arr[j]) {
                    val swap = arr[i]
                    arr[i] = arr[j]
                    arr[j] = swap
                }
            }
        }
        return arr
    }

    fun sortArrayDesc(arr: Array<Int> = arrayOf(),n:Int): Array<Int> {
        for (i in 0..<(n - 1)) {
            for (j in (i + 1)..<n) {
                if (arr[i] < arr[j]) {
                    val swap = arr[i]
                    arr[i] = arr[j]
                    arr[j] = swap
                }
            }
        }
        return arr
    }

    private fun checkIfArrayIsAscOrDesc(arr: Array<Int> = arrayOf()): String {
        var isAscending = true
        var isDescending = true
        for (i in 0..<arr.size - 1) {
            if (arr[i] > arr[i + 1]) isAscending = false
            if (arr[i] < arr[i + 1]) isDescending = false
        }
        return when {
            isAscending -> "Ascending"
            isDescending -> "Descending"
            else -> "UnSorted"
        }
    }

    fun reverseArrayIfAscOrDesc(arr: Array<Int> = arrayOf(),n:Int): Array<Int> {
        val arrayType= checkIfArrayIsAscOrDesc(arr)
        var updatedArray= arrayOf<Int>()
        when(arrayType.lowercase()){
            "ascending"->{
                updatedArray=sortArrayDesc(arr,n)
            }
            "descending"->{
                updatedArray= sortArray(arr,n)
            }
            "unsorted"->{
                updatedArray= sortArrayDesc(arr,n)
            }
        }

        return updatedArray
    }

}