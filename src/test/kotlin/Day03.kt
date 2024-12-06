import org.junit.Test
import util.Util.getInstance

class Day03 {

    @Test
    fun jmlesregex() {
        val instance = getInstance("day03").joinToString("\n")
        val pattern = "mul\\(\\d*,\\d*\\)"
        val patternWithDoesDonts = "mul\\(\\d*,\\d*\\)|do\\(\\)|don't\\(\\)"

        val regex = Regex(patternWithDoesDonts, RegexOption.MULTILINE)

        var result = regex.find(instance)

        var sum : Long = 0
        var total = 0
        var enable = true
        while(result != null) {
            total += 1
            val value = result.value
            println("value: $value")
            if(value == "do()") {
                enable = true
            }
            else if (value == "don't()") {
                enable = false
            }
            else {
                val substring = value.substring(4, endIndex = value.length - 1)
                //println(substring)
                val valuesToMul = substring.split(",").map { it.toInt() }
                val multiplicationResult = valuesToMul[0] * valuesToMul[1]
                //println(multiplicationResult)
                if(enable) sum += multiplicationResult
            }

            result = result.next()
        }
        println("total elements: $total")
        println(sum)
    }
}