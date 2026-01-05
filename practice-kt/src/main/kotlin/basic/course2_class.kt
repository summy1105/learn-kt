package basic

fun main() {
    // 코틀린은 클래스명과 파일명이 일치하지 않아도 됨.
}

fun classBasic() {
    var a = Person("박보영", 1990)
    var b = Person("장원영", 2004)
    a.introduce()
    b.introduce()

    var c = Person("이루다")
    var d = Person("차은우")
    var e = Person("류수정")
}

class Person(var name: String, val birthYear: Int){
    init {
        println("${this.birthYear}, ${this.name}")
    }

    // 보조생성자
    constructor(name: String) : this(name, 1997){
        println("보조 생성자가 사용됨 name:${this.name}")
    }


    fun introduce(){
        println("안녕하세요. ${birthYear}년생 ${name}입니다.")
    }
}


fun classInheritance() {
    var a = Animal("별이", 5, "개")
    var b = Dog("별이", 5)

    a.introduce()
    b.introduce()
    b.bark()

    var c = Cat("루이", 1)
    c.meow()
    c.eat()

    var d = Tiger()
    d.eat()
}

// open class -> 상속가능하게 해줌
open class Animal(var name: String, var age: Int, var type: String){
    fun introduce() {
        println("저는 ${type} ${name}이고, ${age}살 입니다.")
    }

    // open fun -> 오보라이딩 가능하게 해줌
    open fun eat() {
        println("음식을 먹습니다.")
    }
}

class Dog(name: String, age: Int) : Animal(name, age, "개") {
    fun bark() {
        println("멍멍")
    }
}

class Cat(name: String, age: Int) : Animal(name, age, "고양이") {
    fun meow() {
        println("야옹야옹")
    }
}

class Tiger() : Animal("백두", 10, "호랑이") {
    override fun eat() {
        println("고기를 먹습니다.")
    }
}

fun abstractClassInheritance() {
    var r = Rabbit()
    r.eat()
    r.sniff()
}

abstract class AnimalAbstract(){
    abstract fun eat()
    fun sniff() {
        println("킁킁")
    }
}

class Rabbit : AnimalAbstract() {
    override fun eat() {
        println("당근을 먹습니다.")
    }
}

fun interfaceImplement() {
    var d = DogNew()

    d.run()
    d.eat()
}

interface Runner {
    fun run()
}

interface Eater {
    fun eat() {
        println("음식을 먹습니다.")
    }
}

class DogNew : Runner, Eater {
    override fun run() {
        println("우다다다 뜁니다.")
    }

    override fun eat() {
        println("허겁지겁 먹습니다.")
    }
}