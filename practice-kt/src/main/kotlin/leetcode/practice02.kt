package leetcode

fun main() {
    println(plusOne(intArrayOf(9)).toList())
}

fun plusOne(digits: IntArray): IntArray {
    var carry = 1
    if(digits.all { it == 9 }){
        val result = IntArray(digits.size + 1)
        result[0] = 1
        return result
    }

    for (idx in digits.size - 1 downTo 0) {
        var num = digits[idx] + carry
        if (num == 10) {
            digits[idx] = 0
            carry = 1
        } else {
            digits[idx] = num
            carry = 0
        }
    }
    return digits
}