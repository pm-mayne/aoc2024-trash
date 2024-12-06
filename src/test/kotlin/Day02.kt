import org.junit.Test
import util.Util.getInstance

class Day02 {

    @Test
    fun weeeeeeee() {
        val instance = getInstance("day02")

        val reports = instance
            .map {
                it.split(" ")
                .map { i -> i.toInt() }
            }

        val count = reports.count { it.isSafe() }

        println(count)

        val count2 = reports.count { it.couldBeSafe() }

        println(count2)
    }
}

private fun List<Int>.couldBeSafe(): Boolean {
    return this.isSafe() ||
            this.couldBeSafeIfOnly()
}

private fun List<Int>.couldBeSafeIfOnly(): Boolean {
    for(i in this.indices) {
        val listWithoutI: MutableList<Int> = this.toMutableList()
        listWithoutI.removeAt(i)
        if(listWithoutI.isSafe()) {
            return true
        }
    }
    return false
}

private fun List<Int>.isSafe(): Boolean {
    var ascending : Boolean? = null
    for(i in 1..<this.size) {
        val before = this[i-1]
        val after = this[i]
        val diff = after - before

        if(ascending == null) {
            ascending = diff > 0
        }

        if(diff > 0 && !ascending) {
            return false
        }

        if(diff < 0 && ascending) {
            return false
        }

        if(Math.abs(diff) > 3 || diff == 0) {
            return false
        }

    }
    return true

}
