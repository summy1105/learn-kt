package basic

fun main() {
}

// 변수와 자료형
fun variableAndDataType(){
    var a : Int = 123
    println(a)

    var b: Int? = null

    var intValue: Int = 1234
    var longValue: Long = 1234L
    var intValueByHex: Int = 0x1af
    var intValueBinary: Int = 0b101010
    var doubleValue: Double = 123.5// 소수점은 기본으로 double
    var doubleValueWithExp: Double = 123.5e10
    var floatValue: Float = 123.5f

    // 코틀린은 내부적으로 유니코드 인코딩중에 한 방식인 UTF-16BE character 하나가 2byte 사용
    var characterValue: Char = 'a'
    var koreanCharacterValue: Char = '가'
    // 특수문자
    var specialCharacters = arrayOf<Char>('\t', '\b', '\r', '\n', '\'', '\"', '\\', '\$', '\u1234')

    for(specialCharacter in specialCharacters) {
        var result: String = "$characterValue$specialCharacter$koreanCharacterValue"
        println(result)
        println()
    }

    var booleanValue: Boolean = true

    var stringValue = "one line string test"
    var multilineStringValue = """multiline
        |string
        |test
    """.trimMargin()
    println(multilineStringValue)
}

// 형변환, 배열
fun dataTypeCastingAndArray() {

    // 코틀린은 암시적 형변환을 지원하지 않음
    var a: Int = 544321
    var b: Long = a.toLong()

    var intArr = arrayOf(1, 2, 3, 4, 5)
    var nullArr = arrayOfNulls<Int>(5) // null로 채워진 배열
    intArr[2] = 8
    println(intArr[4])
    println(nullArr[2]) // "null" 이라고 출력됨

    var stringValue: String = nullArr[2].toString() // "null"이라는 값이됨
    println(stringValue)
}

// 타입추론, 함수
fun typeInferenceAndFunction() {
    var a = 1234
    var b = 1234L

    var c = 12.45  //double
    var d = 12.45f //float

    var e = 0xABCD
    var f = 0b010101

    var g = true
    var h = 'c'

    var add = fun (a:Int, b:Int, c:Int) : Int{
        return a + b + c
    }
    println(add(1, 2, 3))

    add = fun(a: Int, b: Int, c: Int) = a + b + c // 반환형 생략가능 -> 하지말자
    println(add(2, 3, 4))
}

// 조건문 when
fun whenConditionalStatements(){
    val doWhen = fun(a: Any) {
        val result = when (a) {
            1 -> "정수 1입니다."
            "Test" -> "Any는 ${a}입니다."
            is Long -> "Long 타입입니다."
            !is String -> "String 타입이 아닙니다."
            else -> "어떤 조건도 만족하지 않습니다."
        }
        println(result)
    }
    doWhen(1)
    doWhen("Test")
    doWhen(12L)
    doWhen(2.12159)
    doWhen("Kotlin")
}

// 반복문 for 여러가지 형태
fun forLoop() {
    for (i in 0..9) print(i)

    for (i in 0 until 9) print(i)

    for (i in 0..9 step 3) print(i)

    for (i in 9 downTo 0) print(i)

    for (i in 9 downTo 0 step 3) print(i)

    for (c in 'z' downTo 'a' step 2) print(c)

    var list = listOf("a", "b", "c")
    for (item in list) {
        print("$item ")
    }

    list = listOf("a", "b", "c")
    for ((index, value) in list.withIndex()) {
        print("$index: $value \t")
    }

    val arr = arrayOf("x", "y", "z")
    for (i in arr.indices) {
        print("index $i = ${arr[i]} \t")
    }
}

// 다중반복문 흐름제어
fun controlFlowInNestedLoops() {
    loop@for (i in 1..10) {
        for (j in 1..10) {
            if (i == 1 && j == 2) break@loop
            println("i:$i, j:$j")
        }
    }
}
