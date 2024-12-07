import org.junit.Test
import util.Util.getInstance

class Day07 {

    @Test
    fun branchAndBound() {
        val instances = getInstance("day07")
            .map { it.split(":") }
            .map {
                OperatorPuzzle(it[0].toLong(), it[1].split(" ").filter { x -> x.isNotEmpty() }.map { y -> y.toLong() })
            }

        var counter  : Long = 0
        var allThaCouldBeTrue : Long = 0
        for(instance in instances) {
            val nbOfOps = getNumberOfConfigurations(instance.result, instance.values, 0)
            counter += nbOfOps
            if(nbOfOps > 0) {
                allThaCouldBeTrue += instance.result
            }
        }

        println(counter)
        println(allThaCouldBeTrue)

    }

    private fun getNumberOfConfigurations(goal: Long, values: List<Long>, current: Long): Long {
        if(current == goal && values.isEmpty()){
            return 1
        }
        if(values.isEmpty()) {
            return 0
        }
        //if(notPossible(goal, values, current)) {
        //    return 0
        //}
        val times = current * values.first()
        val plus = current + values.first()
        val concat = (current.toString() + values.first().toString()).toLong()
        return getNumberOfConfigurations(
            goal,
            values.drop(1),
            times
        ) +
        getNumberOfConfigurations(
            goal,
            values.drop(1),
            plus
        )+
        getNumberOfConfigurations(
            goal,
            values.drop(1),
            concat
        )
    }

    private fun notPossible(goal: Long, values: List<Long>, current: Long): Boolean {
        return current + values.sum() > goal ||
                (current != 0L && current * values
                    .reduce{a, b -> a*b} < goal)
    }
}

data class OperatorPuzzle(val result: Long, val values: List<Long>)
