fun main(){
    val listOfNames= listOf("Ali","Guggo","Faaro","Aino")
    val listOfSubNames= listOf("A","G","F","A")

    val zippedLists= listOfNames.zip(listOfSubNames){name,subname->
        name.uppercase() to subname.lowercase()
    }

    println(zippedLists)

    val pair=zippedLists.unzip()
    val namesList=pair.first
    val subNamesList=pair.second
    println(namesList)
    println(subNamesList)

}