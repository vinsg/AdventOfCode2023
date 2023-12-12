import kotlin.math.abs

object Day9 {

    // oh dear recursion

    /*
10 13 16 21 30 45   68
  3  3  5  9  15  23
   0   2  4  6  8
     2  2  2  2
       0  0
 */

    fun part1(): Long {
        val input = readFile("day9.txt")
        val list = input.map { line -> line.split(" ").map { it.toLong() } }
        val result = list.sumOf { predictHistory(it.toMutableList()) }
        println("The result is $result")

        return result
    }

    private fun predictHistory(list: MutableList<Long>): Long {
        // base case
        if (list.all { it == 0L }) {
            return 0
        }
        println(list)
        return list.last() + predictHistory(getDiffList(list).toMutableList())
    }

    /*
    5  10  13  16  21  30  45
      5   3   3   5   9  15
        -2   0   2   4   6
            2   2   2   2
              0   0   0
     */
    private fun predictHistoryBackwards(list: MutableList<Long>): Long {
        // base case
        if (list.all { it == 0L }) {
            return 0
        }
        println(list)
        return list.first() - predictHistoryBackwards(getDiffList(list).toMutableList())
    }

    private fun getDiffList(list: List<Long>): List<Long> {
        return list.zipWithNext { a, b -> b - a }
    }

    fun part2(): Long {
        val input = readFile("day9.txt")
        val list = input.map { line -> line.split(" ").map { it.toLong() } }

        val result = list.sumOf { predictHistoryBackwards(it.toMutableList()) }
        println("The result is $result")

        return result
    }
}

