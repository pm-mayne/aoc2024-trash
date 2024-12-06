import org.junit.Test
import util.Util.getInstance

val rules: MutableList<PrecedenceRule> = mutableListOf()

class Day05 {

    @Test
    fun part1() {
        val instance = getInstance("day05")
        val updates: MutableList<Update> = mutableListOf()
        for (line in instance) {
            if (line.contains("|")) {
                val precedence = line.split("|").map { it.toInt() }
                rules.add(PrecedenceRule(UpdateEntry(precedence[0]), UpdateEntry(precedence[1])))
            }
        }
        for (line in instance) {
            if (line.contains(",")) {
                val update = line.split(",").map { UpdateEntry(it.toInt()) }
                updates.add(Update(update))
            }
        }
        val sumOf = updates.filter { u -> u.isValid() }.sumOf { u -> u.getMiddleValue() }
        println(sumOf)

        val invalidUpdates = updates.filter { u -> !u.isValid() }

        val sumOf1 = invalidUpdates.map { Update(it.update.sorted()) }.sumOf { it.getMiddleValue() }

        println(sumOf1)
    }

}

data class Update(val update: List<UpdateEntry>) {

    fun isValid(): Boolean {
        for (rule in rules) {
            if (update.contains(rule.isBefore) && update.contains(rule.isAfter)) {
                if (this.update.indexOf(rule.isBefore) > this.update.indexOf(rule.isAfter)) {
                    return false
                }
            }

        }
        return true
    }

    fun getMiddleValue(): Int {
        return this.update[this.update.size / 2].i
    }
}

data class UpdateEntry(val i: Int) : Comparable<UpdateEntry> {
    override fun compareTo(other: UpdateEntry): Int {
        if(this.i == other.i) {
            return 0
        }
        val rule = rules.find {
            (it.isAfter.i == i && it.isBefore.i == other.i) ||
                    (it.isBefore.i == i && it.isAfter.i == other.i)
        }
        return if(rule!!.isBefore.i == this.i) {
            -1
        } else {
            1
        }
    }

}

data class PrecedenceRule(val isBefore: UpdateEntry, val isAfter: UpdateEntry)
