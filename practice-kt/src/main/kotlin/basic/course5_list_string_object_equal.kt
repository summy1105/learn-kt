package basic

fun main() {
}

fun list1() {
    val fruits = listOf("사과", "딸기", "배")
    println(fruits[1])

    for (fruit in fruits) {
        print("${fruit}:")
    }

    println()

    val numbers = mutableListOf(6, 3, 1)
    println(numbers)

    numbers.add(4)
    println(numbers)

    numbers.add(2, 8)
    println(numbers)

    numbers.removeAt(1)
    println(numbers)

    numbers.shuffle()
    println(numbers)

    numbers.sort()
    println(numbers)
}

fun string1() {

    val test1 = "Test.Kotlin.String"
    println(test1.length)
    println(test1.toLowerCase())
    println(test1.lowercase()) // 메소드 한단계 호출 줄여짐
    println(test1.toUpperCase())
    println(test1.uppercase())
    println(test1.substring(5..10))

    val test2 = test1.split(".")
    println(test2)

    println(test2.joinToString())//", " -> separator
    println(test2.joinToString(""))
    println(test2.joinToString("-"))

    val nullString: String? = null
    val emptyString = ""
    val blankString = " "
    val normalString = "A"

    println(nullString.isNullOrEmpty())
    println(emptyString.isNullOrEmpty())
    println(blankString.isNullOrEmpty())
    println(normalString.isNullOrEmpty())

    println(nullString.isNullOrBlank())
    println(emptyString.isNullOrBlank())
    println(blankString.isNullOrBlank())
    println(normalString.isNullOrBlank())
}

fun handelNullString() {
    var str: String? = null

    println(str?.uppercase()) // null safe 연산자사용
    println(str?: "default".uppercase())// elvis 연산자
//    println(str!!.uppercase()) // non-null assertion 연산자 > exception!

    str?.run {
        println(uppercase())
        println(lowercase())
    }

    var str2: String = "Kotlin Exam"
    str2.run {
        println(uppercase())
        println(lowercase())
    }
}

// 객체 equal 연산
class Product(val name: String, val price: Int) {
    override fun equals(other: Any?): Boolean {
        if (other is Product) {
            return other.name == name && other.price == price
        }
        return false
    }
}

fun contentAndObjectEquals() {
    var a = Product("콜라", 1000)
    var b = Product("콜라", 1000)
    var c = a
    var d = Product("사이다", 1000)

    println(a == b)
    println(a === b)
    println()
    println(a == c)
    println(a === c)
    println()
    println(a == d)
    println(a === d)
}
