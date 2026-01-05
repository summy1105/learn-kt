package basic

fun main() {
    useEnum()
}

// nested & inner class
class Outer{
    var text = "Outer Class"

    class Nested{ // java static
        fun introduce() {
            println("Nested Class")
        }
    }

    inner class Inner{
        var text = "Inner Class"
        fun introduceInner() {
            println(text)
        }

        fun introduceOuter() {
            println(this@Outer.text)
        }
    }
}

fun useNestedAndInnerClass() {
    Outer.Nested().introduce()
    val outer = Outer()
    val inner = outer.Inner()

    inner.introduceInner()
    inner.introduceOuter()

    outer.text = "Changed Outer Class"
    inner.introduceInner()
    inner.introduceOuter()
}

// data class
// equals, hashcode, toString, copy, componentX -> 속성의 내용을 반환
class General(val name: String, val id: Int)
data class Data(val name: String, val id: Int)

fun useDataClass() {
    val general = General("보영", 212)
    println(general == General("보영", 212))
    println(general.hashCode())
    println(general)

    println()
    val data = Data("보영", 212)
    println(data == Data("보영", 212))
    println(data.hashCode())
    println(data)

    println(data.copy())
    println(data.copy(name = "아린"))
    println(data.copy(id = 618))

    println()
    val list = listOf(Data("보영", 212), Data("루다", 306), Data("아린", 618))

    // componentX
    for ((name, id) in list) {
        println("$name, $id")
    }
}

// enum
enum class State(val message: String){
    SING("노래를 부릅니다.") {
        override fun printMessage() {
            println(message)
        }
    },
    EAT("밥을 먹습니다."){
        override fun printMessage() {
            println("식당에서 $message")
        }
   },
    SLEEP("잠을 잡니다.") {
        override fun printMessage() {
            println("침대에서 $message")
        }
    },
    ;

    fun isSleeping() = this == State.SLEEP
    abstract fun printMessage();
}

fun useEnum() {
    val state = State.valueOf("SING")
    println(state)
    println(state.message)

    println()
    for(state in State.values()){
        println(state)
        state.printMessage()
    }
}