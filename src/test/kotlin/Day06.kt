import org.junit.Test
import util.Util.getInstance
import util.Util.toStrMatrix

private fun List<List<String>>.toAllVariants(): List<List<List<String>>> {
    val result : MutableList<List<List<String>>> = mutableListOf()
    for(i in this.indices) {
        for(j in this[i].indices){
            if(this[i][j] == ".") {
                var toto : MutableList<List<String>> = this.toMutableList()
                val totoI = this[i].toMutableList()
                totoI[j] = "#"
                toto[i] = totoI
                result.add(toto)
            }
        }
    }
    return result
}

class Day06 {

    @Test
    fun guardInfinity() {
        val instances = getInstance("day06").toStrMatrix().toAllVariants()

        var countInstances = 0
        var countLoopy = 0
        for(instance in instances) {
            countInstances++
            println("Instance n° $countInstances")
            var guardPosition: GuardPosition = getGuardInitialPosition(instance)
            val visited : MutableSet<GuardPosition> = mutableSetOf()
            while(!guardPosition.isOutOfMatrix(instance) && !visited.contains(guardPosition)) {
                visited.add(guardPosition)
                guardPosition = toNextGuardPosition(guardPosition, instance)
            }
            if(!guardPosition.isOutOfMatrix(instance)) {
                countLoopy++
                println("is loopy!")
            }
        }

        println("Loopy count: $countLoopy")

    }

    @Test
    fun guardLOL() {
        val instance = getInstance("day06").toStrMatrix()

        var guardPosition: GuardPosition = getGuardInitialPosition(instance)

        var visited : MutableSet<GuardPosition> = mutableSetOf()

        var positionsToMakeCycle : MutableSet<Pair<Int, Int>> = mutableSetOf()

        while(!guardPosition.isOutOfMatrix(instance)) {
            visited.add(guardPosition)
            val haveBeenThereFacingRight : Boolean = haveBeenThereFacingRight(visited, guardPosition)
            if(haveBeenThereFacingRight) {
                val positionInFront = getPositionInFront(guardPosition.guardFacing, guardPosition.i, guardPosition.j)
                positionsToMakeCycle.add(positionInFront.i to positionInFront.j)
            }
            guardPosition = toNextGuardPosition(guardPosition, instance)
        }

        println(positionsToMakeCycle.size)

    }

    private fun haveBeenThereFacingRight(visited: MutableSet<GuardPosition>, guardPosition: GuardPosition): Boolean {
        return visited.contains(guardPosition.copy(guardFacing = guardPosition.guardFacing.turnRight()))
    }

    private fun toNextGuardPosition(
        guardPosition: GuardPosition,
        instance: List<List<String>>
    ): GuardPosition {
        val i = guardPosition.i
        val j = guardPosition.j
        val face = guardPosition.guardFacing
        val inFrontOfGuard = getPositionInFront(face, i, j)
        if (inFrontOfGuard.isOutOfMatrix(instance)) {
            return inFrontOfGuard
        }
        else if (instance[inFrontOfGuard.i][inFrontOfGuard.j] != "#") {
            return inFrontOfGuard
        } else { // obstacle !!!
            val newPosition = guardPosition.copy(guardFacing = face.turnRight())
            return newPosition
        }
    }

    private fun getPositionInFront(face: FACE, i: Int, j: Int) = when (face) {
        FACE.UP -> GuardPosition(i - 1, j, face)
        FACE.DOWN -> GuardPosition(i + 1, j, face)
        FACE.LEFT -> GuardPosition(i, j - 1, face)
        FACE.RIGHT -> GuardPosition(i, j + 1, face)
    }

    private fun getGuardInitialPosition(instance: List<List<String>>): GuardPosition {
        for(i in instance.indices) {
            for(j in instance.indices) {
                if(instance[i][j] == "^") {
                    return GuardPosition(i, j, FACE.UP)
                }
            }
        }
        return GuardPosition(-1, -1, FACE.UP)
    }

    enum class FACE {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        fun turnRight() : FACE {
            return when(this) {
                UP -> RIGHT
                DOWN -> LEFT
                LEFT -> UP
                RIGHT -> DOWN
            }
        }
    }
}

data class GuardPosition(val i: Int, val j: Int, val guardFacing : Day06.FACE) {
    fun isOutOfMatrix(matrix : List<List<String>>): Boolean {
        return i < 0 || i >= matrix.size || j < 0 || j >= matrix[i].size
    }

}
