import org.junit.Test
import util.Util.getInstance

class Day01 {

    @Test
    fun weeee() {
        val leftList: MutableList<Int> = mutableListOf()
        val rightList: MutableList<Int> = mutableListOf()
        val instance = getInstance("day01")
        for (line in instance) {
            val split = line.split("   ").map { it.toInt() }
            leftList.add(split[0])
            rightList.add(split[1])
        }

        val leftSorted = leftList.sorted()
        val rightSorted = rightList.sorted()

        var sum = 0
        for(i in leftSorted.indices) {
            sum += Math.abs(leftSorted[i] - rightSorted[i])
        }

        println(sum)

        val sum1 = leftSorted.sumOf { value ->
            value * rightSorted.count { i -> i == value }
        }

        println(sum1)
    }
}