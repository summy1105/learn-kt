package basic

fun main() {
}

// Singleton pattern 을 언어차원에서 지원함 -> 인스턴스 생성 X
// 선언시 바로 생성
object Counter{
    init {
        println("object init")
    }
    var count = 0

    fun countUp() {
        count++
    }

    fun clear() {
        count = 0
    }
}

fun normalObject() {
    println(Counter.count)

    Counter.countUp()
    Counter.countUp()
    println(Counter.count)

    Counter.clear()
    println(Counter.count)
}

// companion object -> class내 공용 속성 및 함수 사용 -> java static 모음?
class FoodPoll(var name: String){
    companion object{
        var total = 0
        fun printTotal() {
            println("total = $total")
        }
    }

    var count = 0
    fun vote() {
        total++
        count++
    }
    fun print() {
        println("$name = $count")
    }
}

fun companionObject() {
    var a = FoodPoll("짜장")
    var b = FoodPoll("짬뽕")

    a.vote()
    a.vote()

    b.vote()
    b.vote()
    b.vote()

    a.print()
    b.print()
    FoodPoll.printTotal()
}

// 옵저버 패턴
interface EventListener {
    fun onEvent(count: Int)
}

class CountExecutor(var listener: EventListener){
    fun count() {
        for (i in 1..100) {
            if (i % 5 == 0)  listener.onEvent(i)
        }
    }
}

class EventPrinter : EventListener {
    override fun onEvent(count: Int) {
        print("${count}-")
    }

    fun start() {
        val count = CountExecutor(this)
        count.count()
    }
}

fun runObserverPattern() {
    EventPrinterSecond().start()
}

class EventPrinterSecond{
    fun start() {
        // 익명객체 사용
        val count = CountExecutor(object : EventListener {
            override fun onEvent(count: Int) {
                print("${count}=")
            }
        })
        count.count()
    }
}

fun observerAndAnonymous() {
    EventPrinter().start()
}

// up, down casting
open class Drink{
    val name = "음료"
    open fun drink() {
        println("${name}을 마십니다.")
    }
}

class Cola : Drink() {
    val type = "콜라"
    override fun drink() {
        println("${name}중에 ${type}를 마십니다.")
    }
    fun washDishes() {
        println("${type}컵 설거지를 합니다.")
    }
}

fun casting() {
    var a = Drink()
    a.drink()

    var b :Drink = Cola()
    b.drink()
    if (b is Cola) { // is는 조건문 안 영역에서 down casting 됨
        b.washDishes()
    }

    var c = b as Cola // as 를 사용하면 변수 자체도 down casting 됨
    c.washDishes()
    b.washDishes()
}

// generic
open class A{
    open fun shout() {
        println("A가 소리침")
    }
}

class B : A() {
    override fun shout() {
        println("B가 소리침")
    }
}

class C : A() {
    override fun shout() {
        println("B가 소리침")
    }
}

class UsingGeneric<T : A>(private val t : T){
    fun doShouting() {
        t.shout()
    }
}

fun <T : A> doShouting(t: T) {
    t.shout()
}

