
fun main(){
    val listPerson= listOf<Person>(
        Person(name = "Ali", 31,drivingLicense = true),
        Person(name = "Faaro", 3),
        Person(name = "Guggo", 4),
        Person(name = "Ainno", 1)
    )
//    return boolean
//    agr condition ksi bhi 1 record ke sath match kare gi to true return kare ga

    val personsWithLicense=listPerson.any { it.drivingLicense }
    println(personsWithLicense)
//    return boolean
//    agr condition ksi bhi record ke sath match na kare gi to true return kare ga
    val personWithLessAge=listPerson.none { it.age >40 }
    println(personWithLessAge)
//    return boolean
//    agr ye condition sb par true hue to true return kare ga nai to false
    val personUserNameNotAllowed=listPerson.all { it.age>0 }
    println(personUserNameNotAllowed)

//    add lists tto chunk
    val chunkedList=listPerson.chunked(3)
    println(chunkedList)
}

data class Person(
    val name:String,
    val age:Int,
    val drivingLicense:Boolean=false
)