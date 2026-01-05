package basic
// 자바와 달리 폴더구조와 페키지 명을 일치시키지 않아도 됨

fun main() {
    classScope()
}

/**
 * 패키지 스코프에서는
 * public (default) 어떤 패키지에서도 접근가능
 * internal 같은 모듈 내에서만 접근 가능
 * private 같은 파일내에서만 접근 가능
 * (protected 사용 안함)
 */

/**
 * 클래스 스코프에서는
 * public (default)
 * private 클래스 내부에서만 접근 가능
 * protected 클래스 자신과 상속 클래스에서 접근가능
 * (internal 사용 안함)
 */

fun lambda1() {
    // 고차함수를 파라미터로 넘기려면 :: 콜론 두개를 붙여줘야함
    b(::a)

    // 람다함수
    val lambdaFunc : (String)->Unit = { str: String -> println("$str 함수 lambda function")}
    val lambdaFunc2 = { str:String -> println("$str 함수 lambda function") }
    b(lambdaFunc)
}

// 고차함수
fun a(str: String){
    println("$str 함수 a")
}

// Unit -> 반환 값이 없음을 표시
fun b(function: (String) -> Unit) {
    function("b가 호출한")
}

fun lambdaStyles() {
    val multilineLambda = { str:String ->
        println("$str 람다함수")
        println("여러구문")
        println("사용 가능")
    }

    val noParamsLambda: () -> Unit = {
        println("파러미터가 없어요")
    }

    val oneParamLambda: (String) -> Unit = {
        println("$it 람다함수")
    }
}

fun classScope() {
    // apply 인스턴스 생성후 바로 자동으로 실행 {} -> 클래스 scope 임
    var a = Book("코틀린", 1000).apply {
        name = "[초특가]" + name
        discount()
    }

    // run 클래스 scope 영역으로 바로 실행함
    val result = a.run {
        println("상품명 : ${name}, 가격: ${price}원")
        name
    }

    with(a) {
        a.price += 1000
        a.name = a.name.substring(5)
    }

    var price = 5000;
    var name = "자바"
    a.run {
        println("상품명 : ${name}, 가격: ${price}원") // 함수 scope의 name, price
        println("상품명 : ${this.name}, 가격: ${this.price}원") // Book의 name, price
    }

    a.let {
        println("상품명 : ${name}, 가격: ${price}원") // 함수 scope의 name, price
        println("상품명 : ${it.name}, 가격: ${it.price}원") // Book의 name, price
    }

    var b = Book("스프링", 2000).also {
        it.name = "[한정판]" + name
        it.discount()
    }

    b.let {
        println("상품명 : ${it.name}, 가격: ${it.price}원") // Book의 name, price
    }
}

class Book(var name: String, var price: Int) {
    init {
        println("Book 생성됨")
    }

    fun discount() {
        price -= 200
        println("discount 실행됨")
    }
}