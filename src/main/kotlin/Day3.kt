object Day3 {

    fun part1(): Int {
        val input = readFile("day3.txt")
        val matrix = input.convertTo2dMatrix()

        // set of the coordinates of the first digit of every valid number
        val validNumberSet = mutableSetOf<Pair<Int, Int>>()

        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                val c = matrix[row][col]

                // reject not special char
                if (!(!c.isDigit() && c != '.')) {
                    continue
                }

                for (cr in row - 1..row + 1) {
                    for (cc in col - 1..col + 1) {
                        // reject bounds or if it's not a digit
                        if (cr < 0 || cr >= matrix.size || cc < 0 || cc >= matrix[cr].size || !matrix[cr][cc].isDigit()) {
                            continue
                        }
                        var colIndex = cc
                        while (colIndex > 0 && matrix[cr][colIndex - 1].isDigit()) {
                            colIndex -= 1
                        }
                        validNumberSet.add(Pair(cr, colIndex))
                    }
                }

            }
        }

        val sum = validNumberSet.sumOf {
            getNumber(matrix, it)
        }
        println("Gear ratio is $sum")
        return sum
    }

    fun part2(): Int {
        val input = readFile("day3.txt")
        val matrix = input.convertTo2dMatrix()

        var sum = 0
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                val c = matrix[row][col]

                // reject not special char
                if (c != '*') {
                    continue
                }
                val dualGearset = mutableSetOf<Pair<Int, Int>>()

                // find valid numbers
                for (cr in row - 1..row + 1) {
                    for (cc in col - 1..col + 1) {
                        // reject bounds or if it's not a digit
                        if (cr < 0 || cr >= matrix.size || cc < 0 || cc >= matrix[cr].size || !matrix[cr][cc].isDigit()) {
                            continue
                        }
                        var colIndex = cc
                        while (colIndex > 0 && matrix[cr][colIndex - 1].isDigit()) {
                            colIndex -= 1
                        }
                        dualGearset.add(Pair(cr, colIndex))
                    }
                }
                // process only when exactly 2 gears are adjacent to a *
                if (dualGearset.size == 2) {
                    sum += dualGearset.fold(1) { acc, pair -> acc * getNumber(matrix, pair) }
                }

            }
        }
        println("Gear ratio is $sum")
        return sum
    }

    private fun getNumber(matrix: Array<Array<Char>>, coords: Pair<Int, Int>): Int {
        var (r, c) = coords
        var num = "${matrix[r][c]}"
        while (c + 1 < matrix[r].size && matrix[r][c + 1].isDigit()) {
            c += 1
            num = "$num${matrix[r][c]}"
        }
        return num.toInt()
    }

    private fun List<String>.convertTo2dMatrix(): Array<Array<Char>> {
        val numRows = this.size
        val numCols = this[0].length
        return List(numRows) { row ->
            List(numCols) { col ->
                this[row][col]
            }.toTypedArray()
        }.toTypedArray()
    }
}