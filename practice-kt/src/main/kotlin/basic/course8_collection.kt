package basic

fun main() {
    collectionFunction3()
}

fun useSet() {
    val set = mutableSetOf("귤", "바나나", "키위")

    for (item in set) {
        print("$item ")
    }

    println()
    set.add("자몽")
    println(set)

    set.remove("바나나")
    println(set)
    println(set.contains("귤"))
}

fun useMap() {
    val map = mutableMapOf("레드벨벳" to "음파음파", "트와이스" to "FANCY", "ITZY" to "ICY")

    for (entry in map) {
        println("${entry.key} : ${entry.value}")
    }

    map["오마이걸"] = "미미"
    map.remove("ITZY")
    println(map)

    println(map["레드벨벳"])
}

fun collectionFunction1() {
    val namedList = listOf("박수영", "김지수", "김다현", "신유나", "김지우")
    namedList.forEach { print("$it ") }
    println()

    println(namedList.filter { it.startsWith("김") })

    println(namedList.map { "이름 : $it" })
    println(namedList.any { it == "김지연" })
    println(namedList.all { it.length === 3 })
    println(namedList.none { it.startsWith("이") })

    println(namedList.first{ it.startsWith("김")})
    println(namedList.firstOrNull{ it.startsWith("이")})
    println(namedList.last{ it.startsWith("김")})
    println(namedList.count { it.contains("지") })
}

fun collectionFunction2() {
    data class Person(val name: String, val birthYear: Int)

    val personList = listOf(
        Person("유나", 1992),
        Person("조이", 1996),
        Person("츄", 1999),
        Person("유나", 2003),
    )

    println(personList.associateBy { it.birthYear })
    println(personList.groupBy { it.name })

    val (over98, under98) = personList.partition { it.birthYear > 1998 }
    println(over98)
    println(under98)
}

fun collectionFunction3() {
    val numbers = listOf(-3, 7, 2, -10, 1)

    println(numbers.flatMap { listOf(it * 10, it + 10) })
    println(numbers.getOrElse(1) {50})
    println(numbers.getOrElse(10){50})

    val names = listOf("A", "B", "C", "D")
    println(names zip numbers)
    println(numbers zip names)
}