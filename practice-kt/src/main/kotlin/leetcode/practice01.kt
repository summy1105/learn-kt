import java.util.Stack

fun main() {
//    val result = twoSum(intArrayOf(3,2,4), 6)
//    println(result.contentToString())
//    println(isPalindrome(10))
//    println(romanToInt("III"))
//    println(longestCommonPrefix(arrayOf("dog","racecar","car")))
//    val array = intArrayOf(1,1,2,2,2,2,3)
//    val result = removeDuplicates(array)
//    println("${array.contentToString()} $result")

//    val nums = intArrayOf(3, 2, 2, 3)
//    val result = removeElement(nums, 3)
//    println("${nums.contentToString()} $result")

//    println(strStr("leetcode", "co"))
}

fun lengthOfLastWord(s: String): Int {
    val filter = s.split(" ").filter { it.isNotEmpty() }
    return filter.last().length
}

fun searchInsert(nums: IntArray, target: Int): Int {
    val result = nums.indexOfFirst { it >= target }
    return if( result<0) nums.size else result
}

fun strStr(haystack: String, needle: String): Int {
    return haystack.indexOf(needle)
}

fun removeElement(nums: IntArray, `val`: Int): Int {
    var arrLength = nums.size
    var idx = 0;
    for( i in 0 until nums.size){
        if(`val` != nums[i])
            nums[idx++] = nums[i];
        else
            arrLength--;
    }
    return arrLength
}

fun removeDuplicates(nums: IntArray): Int {
    var arrLength = nums.size
    var before = nums[0]
    var idx = 1;
    for( i in 1 until nums.size){
        if(before != nums[i]){
            before = nums[i];
            nums[idx++] = nums[i];
        }else{
            arrLength--;
        }
    }
    return arrLength
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}
fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
    var root = ListNode(-101)
    var resultHeader = root
    var header1 = list1
    var header2 = list2

    while( header1 != null && header2 != null){
        if(header1.`val`<= header2.`val`){
            resultHeader.next = header1
            header1 = header1.next
        } else {
            resultHeader.next = header2
            header2 = header2.next
        }
        resultHeader = resultHeader.next!!
    }
    resultHeader.next = header1?: header2
    return root.next
}

val openBracket = charArrayOf('(', '{', '[')
val closeBracket = charArrayOf(')', '}', ']')
fun isValid(s: String): Boolean {
    val stack = Stack<Char>()
    for (ch in s){
        if(openBracket.contains(ch))
            stack.push(ch)
        else {
            if (stack.isEmpty()) return false
            val pop = stack.pop();
            if(openBracket.indexOf(pop) != closeBracket.indexOf(ch))
                return false
        }
    }
    return stack.isEmpty()
}

fun longestCommonPrefix(strs: Array<String>): String {
    var prefix = strs[0]
    strs.filter { prefix != it }.forEach {
        val sb = StringBuilder()
        for( i in 0 until Math.min(prefix.length, it.length))
            if(prefix[i] == it[i]) sb.append(it[i])
            else break
        prefix = sb.toString()
    }
    return prefix
}

fun romanToInt(s: String): Int {
    val romansNumberMap : Map<Char, Int> = mapOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000);
    val romanChars = s.toCharArray()
    var tokenBuilder = StringBuilder()
    var tokenNumber = 0
    var result = 0;
    for(i in 0 until romanChars.size){
        val ch = romanChars[i]
        if(romansNumberMap[tokenBuilder.lastOrNull()?:ch]?:0 > romansNumberMap[ch]?:0){
            result += tokenNumber
            tokenNumber = romansNumberMap[ch]?:0
            tokenBuilder= StringBuilder().append(ch)
        }else if(romansNumberMap[tokenBuilder.lastOrNull()?:ch]?:0 < romansNumberMap[ch]?:0 ){
            result += (romansNumberMap[ch]?:0) - tokenNumber
            tokenNumber = 0
            tokenBuilder= StringBuilder()
        }else{
            tokenNumber += romansNumberMap[romanChars[i]]?:0
            tokenBuilder.append(ch)
        }
    }
    result+=tokenNumber
    return result
}

fun isPalindrome(x: Int): Boolean {
    val number = x.toString()
    for(i in 0 .. number.length/2){
        if(number.get(i) != number.get(number.length-1-i))
            return false
    }
    return true
}

fun twoSum(nums: IntArray, target: Int): IntArray {
    for(i in 0 until nums.size){
        for(j in i+1 until nums.size){
            if(nums[i]+nums[j] == target)
                return intArrayOf(i,j);
        }
    }
    return intArrayOf(0);
}