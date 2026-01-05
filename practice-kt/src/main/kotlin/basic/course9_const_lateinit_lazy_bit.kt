package basic

fun main() {
    bitwiseOperation()
}
// const 는 기본 자료형만 String 포함
const val PRIMITIVE_TYPE = "String, Int, Long, Float, Double, Byte, Boolean, Char, Short"

class Sample {
//    const val TEST = "1" // 물론 안됨
    companion object{
        const val CONST_A = 1234
    }
}

class FoodCourt{
    companion object{
        const val FOOD_CREAM_PASTA = "Cream Pasta"
        const val FOOD_STEAK = "Steak"
        const val FOOD_PIZZA= "Pizza"
    }

    fun searchPrice(foodName : String) : Int{
        return when (foodName) {
            FOOD_CREAM_PASTA -> 13_000
            FOOD_STEAK -> 30_000
            FOOD_PIZZA -> 15_000
            else -> 0
        }
    }
}

fun useConstClass() {
    val foodCourt = FoodCourt()

    val price = foodCourt.searchPrice("Steak")
    println("${FoodCourt.FOOD_STEAK}는 ${price}원 입니다.")

    println("${FoodCourt.FOOD_PIZZA}는 ${foodCourt.searchPrice(FoodCourt.FOOD_PIZZA)}원 입니다.")
}

// late init -> primitive type  은 사용 안됨
class LateInitSample {
    private lateinit var text: String
    fun getLateInitText() : String{
        return if (::text.isInitialized) {
            text
        } else {
            "default "
        }
    }

    fun setLateInitText(text: String): Unit {
        this.text = text
    }
}

fun useLateInitSample() {
    val lateInitSample = LateInitSample()
    println(lateInitSample.getLateInitText())

    lateInitSample.setLateInitText("new value")
    println(lateInitSample.getLateInitText())
}

// lazy
fun useLazy() {
    val number : Int by lazy {
        println("initialize number")
        7
    }

    println("start code")
    println(number)
    println(number)
}

// bitwise operation
fun bitwiseOperation() {
    var bitData: Int = 0b10000
    bitData = bitData or(1 shl 2)
    // 0b10000 or 0b100
    println(bitData.toString(2))

    val result = bitData and (1 shl 4)
    println(result.toString(2))
    println(result shr 4)

    bitData = bitData and ((1 shl 4).inv())
    // 0b10100 and 0b01111
    println(bitData.toString(2))

    println((bitData xor 0b10100).toString(2))
}