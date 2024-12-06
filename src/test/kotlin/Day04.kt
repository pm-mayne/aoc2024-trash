import org.junit.Test
import util.Util.getInstance

class Day04 {

    @Test
    fun XMASBitch() {
        val lines: List<String> = getInstance("day04")
        val matrix: List<List<String>> = getInstance("day04").map { it.split("").filter { s -> s.isNotEmpty() } }
        val columns: MutableList<String> = mutableListOf()
        val diagonals: MutableList<String> = mutableListOf()

        val diagIndices: MutableList<MutableList<Pair<Int, Int>>> = mutableListOf()

        //fill columns
        for (i in lines[0].indices) {
            columns.add(lines.map { it[i] }.joinToString(""))
        }

        //fill diags
        //left to right
        //line diags
        for (iLine in matrix.indices) {
            val diagIndex :MutableList<Pair<Int, Int>> = mutableListOf()
            var diagI = ""
            var i = iLine
            var j = 0
            while (i < matrix.size && j < matrix[i].size) {
                diagI += matrix[i][j]
                diagIndex.add(i to j)
                i = i + 1
                j = j + 1
            }
            diagIndices.add(diagIndex)
            diagonals.add(diagI)
        }
        //col diags
        for (iCol in 1..<matrix[0].size) {
            var diagJ = ""
            val diagIndex :MutableList<Pair<Int, Int>> = mutableListOf()
            var i = 0
            var j = iCol
            while (i < matrix.size && j < matrix[i].size) {
                diagJ += matrix[i][j]
                diagIndex.add(i to j)
                i = i + 1
                j = j + 1
            }
            diagIndices.add(diagIndex)
            diagonals.add(diagJ)
        }

        //right to left
        //line diags
        for (iLine in matrix.indices) {
            val diagIndex :MutableList<Pair<Int, Int>> = mutableListOf()
            var diagI = ""
            var i = iLine
            var j = 0
            while (i >= 0 && i < matrix.size && j < matrix[i].size) {
                diagI += matrix[i][j]
                diagIndex.add(i to j)
                i = i - 1
                j = j + 1
            }
            diagIndices.add(diagIndex)
            diagonals.add(diagI)
        }
        //col diags
        for (iCol in 1..<matrix[0].size) {
            var diagJ = ""
            val diagIndex :MutableList<Pair<Int, Int>> = mutableListOf()
            var i = matrix.size - 1
            var j = iCol
            while (i >= 0 && i < matrix.size && j < matrix[i].size) {
                diagJ += matrix[i][j]
                diagIndex.add(i to j)
                i = i - 1
                j = j + 1
            }
            diagIndices.add(diagIndex)
            diagonals.add(diagJ)
        }

        val allDirections = diagonals + columns + lines
        val pattern = "XMAS"
        val pattern2 = "SAMX"
        val regex = Regex(pattern, RegexOption.MULTILINE)
        val regex2 = Regex(pattern2, RegexOption.MULTILINE)
        val bigluml = allDirections.joinToString("\n")
        //println(bigluml)
        val result = regex.findAll(allDirections.joinToString("\n"))
        val result2 = regex2.findAll(allDirections.joinToString("\n"))
        println(result.count() + result2.count())

        var countCross = 0
        for(i in matrix.indices) {
            for(j in matrix[0].indices) {
                if(matrix[i][j] == "A") {
                    val topLeft = getMatrixIJ(matrix, i - 1, j - 1)
                    val bottomRight = getMatrixIJ(matrix, i + 1, j + 1)

                    val topRight = getMatrixIJ(matrix, i - 1, j + 1)
                    val bottomLeft = getMatrixIJ(matrix, i + 1, j - 1)
                    if(topLeft != bottomRight && "SM".contains(topLeft) && "SM".contains(bottomRight)) {
                        if(topRight != bottomLeft && "SM".contains(topRight) && "SM".contains(bottomLeft)) {
                            countCross++
                            //println("${matrix[i - 1][j - 1]}${matrix[i - 1][j]}${matrix[i - 1][j + 1]}")
                            //println("${matrix[i][j - 1]}${matrix[i][j]}${matrix[i][j + 1]}")
                            //println("${matrix[i + 1][j - 1]}${matrix[i + 1][j]}${matrix[i + 1][j + 1]}")
                            //println("-----")
                        }
                    }
                }
            }
        }

        print(countCross)

    }

    private fun getMatrixIJ(matrix: List<List<String>>, i: Int, j: Int): String {
        if(i >= matrix.size || i < 0 || j >= matrix[i].size || j < 0) {
            return " "
        }
        return matrix[i][j]
    }
}