import kotlin.math.floor

object Day10 {

    data class Pipe(val char: Char) {

        var visited = false

        /*
        return the list of valid coords based on value
        | is a vertical pipe connecting north and south.
        - is a horizontal pipe connecting east and west.
        L is a 90-degree bend connecting north and east.
        J is a 90-degree bend connecting north and west.
        7 is a 90-degree bend connecting south and west.
        F is a 90-degree bend connecting south and east.
        . is ground; there is no pipe in this tile.
        S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
         */
        fun getValidMoves(row: Int, col: Int): List<Pair<Int, Int>> {
            return when (this.char) {
                '|' -> listOf(Pair(row + 1, col), Pair(row - 1, col))
                '-' -> listOf(Pair(row, col + 1), Pair(row, col - 1))
                'L' -> listOf(Pair(row, col + 1), Pair(row - 1, col))
                'J' -> listOf(Pair(row, col - 1), Pair(row - 1, col))
                '7' -> listOf(Pair(row, col - 1), Pair(row + 1, col))
                'F' -> listOf(Pair(row, col + 1), Pair(row + 1, col))
                '.' -> listOf(Pair(row, col))
                // try every combinations because the pipes go in all directions.
//                'S' -> listOf(Pair(row + 1, col), Pair(row - 1, col), Pair(row, col + 1), Pair(row, col - 1))
                'S' -> listOf(Pair(row, col - 1))
                else -> listOf(Pair(row, col))
            }
        }
    }

    // return the list of all the valid moves in x and y
    private fun getNextMove(grid: Array<Array<Pipe>>, r: Int, c: Int): Pair<Int, Int> {
        grid[r][c].getValidMoves(r, c).forEach { (nr, nc) ->
            if (nr < grid.size && nc < grid[nr].size && !grid[nr][nc].visited) {
//                println("new move: $nr $nc")
                return Pair(nr, nc)
            }
        }
        return Pair(-1, -1) // no more valid moves
    }


    private fun List<String>.convertTo2dMatrix(): Array<Array<Pipe>> {
        val numRows = this.size
        val numCols = this[0].length
        return List(numRows) { row ->
            List(numCols) { col ->
                Pipe(this[row][col])
            }.toTypedArray()
        }.toTypedArray()
    }

    private fun findStartingLocation(grid: Array<Array<Pipe>>): Pair<Int, Int> {
        for (r in grid.indices) {
            for (c in grid[r].indices) {
                if (grid[r][c].char == 'S') {
                    return Pair(r, c)
                }
            }
        }
        throw error("should return starting location")
    }

    // need to try all directions for S because they are 4 valid pipe loops starting at S
    fun part1(): Long {
        val input = readFile("day10.txt")
        var step = 0L
        var looped = false
        val grid = input.convertTo2dMatrix()
        // get starting location
        val startPos = findStartingLocation(grid)
        println("starting pos: $startPos")
        // set the start to visited
        grid[startPos.first][startPos.second].visited = true

        var currPos = startPos
        do {
            currPos = getNextMove(grid, currPos.first, currPos.second)
            step += 1
            println("step traveled $step, current position $currPos")
            if (currPos == Pair(-1, -1)) {
                looped = true
            } else {
                grid[currPos.first][currPos.second].visited = true
            }
        } while (!looped)

        val distance = (step + 1).floorDiv(2)
        println("distance is $distance")
        return distance
    }

    fun part2(): Long {
        val input = readFile("day10.txt")
        var looped = false
        val grid = input.convertTo2dMatrix()
        // get starting location
        val startPos = findStartingLocation(grid)
        println("starting pos: $startPos")
        // set the start to visited
        grid[startPos.first][startPos.second].visited = true

        var pipeMapPos = mutableMapOf<Pair<Int, Int>, Boolean>()
        var currPos = startPos
        pipeMapPos[currPos] = true
        do {
            currPos = getNextMove(grid, currPos.first, currPos.second)
            if (currPos == Pair(-1, -1)) {
                looped = true
            } else {
                pipeMapPos[currPos] = true
                grid[currPos.first][currPos.second].visited = true
            }
        } while (!looped)
        println(pipeMapPos)

        var total = 0L
        var activated: Boolean
        var c: Char
        for (row in grid.indices) {
            activated = false
            for (col in grid[row].indices) {
                c = '.'
                if (pipeMapPos[Pair(row, col)] == true) {
                    activated = !activated
                    c = 'q'
                }
                if (activated && pipeMapPos[Pair(row, col)] == null){
                    c = 'o'
                   total += 1
                }
                print(c)
            }
            println()
        }
        println("area inside the pipes is $total")
        return total
    }
}