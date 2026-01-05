package basic

fun main() {
}

// named arguments
fun deliveryItem(name: String, count: Int = 1, destination: String = "집") {
    println("${name}, ${count}개를 ${destination}에 배달하였습니다.")
}

fun namedArguments() {
    deliveryItem("짬뽕")
    deliveryItem("선물", destination = "친구집")
}

// valuable arguments
// vararg 의 파람은 맨 마지막에.
fun sum(vararg numbers: Int) : Int {
    var sum = 0
    for (n in numbers) {
        sum += n
    }
    return sum
}

fun valuableArguments() {
    val sum = sum(1, 2, 3, 4, 5)
    println(sum)
}

// infix -> 연산자처럼 쓸수 있음
// Int 클래스일때
infix fun Int.multiply(x: Int): Int = this * x

// Class에서 infix를 정의할 때
class Computer(var type:String){
    infix fun changeTypeTo(newType:String) : String {
        this.type = newType
        return this.type
    };
}

fun useInfixFunction() {
    println(6 multiply 4)
    println(6.multiply(4))

    val testCom = Computer("laptop")
    println(testCom changeTypeTo "Desktop")
}
