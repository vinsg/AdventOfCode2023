object Day4 {
    fun part1(): Int {

        val input = readFile("day4.txt")
        val winningSum = input.sumOf { game ->
            var gameTotal = 0
            val winningNumbers = getWinningNumbers(game)
            val playedNumbers = getPlayedNumbers(game)
            playedNumbers.forEach { number ->
                if (winningNumbers.contains(number)) {
                    when (gameTotal) {
                        0 -> gameTotal = 1
                        1 -> gameTotal = 2
                        else -> gameTotal += gameTotal
                    }
                }
            }
            gameTotal
        }

        println("winning sum is: $winningSum")
        return winningSum
    }

    fun part2(): Int {
        data class Game(val id: Int, val winningNumbers: List<Int>, val playedNumbers: List<Int>, var count: Int) {
            fun getWinningSet(): List<Int> {
                return this.playedNumbers.filter { winningNumbers.contains(it) }
            }
        }

        val input = readFile("day4.txt").map { str ->
            val gameId = str.substringBefore(":").split(" ").filterNot { it.isEmpty() }[1].toInt()
            val winningNumbers = getWinningNumbers(str)
            val playedNumbers = getPlayedNumbers(str)

            Game(gameId, winningNumbers, playedNumbers, 1)
        }
        var winningSum = 0

        input.forEachIndexed { i, game ->
            winningSum += game.count

            // add winnings to the future counts
            val winnings = game.getWinningSet().count()
            for (c in 1..<winnings + 1) {
                input[i + c].count += game.count
            }
        }
        println("winning sum is: $winningSum")
        return winningSum
    }

    private fun getPlayedNumbers(str: String) =
        str.substringAfter("|").trim().split(" ").filterNot { it.isEmpty() }.map { it.toInt() }

    private fun getWinningNumbers(str: String) =
        str.substringAfter(":").substringBefore("|").trim().split(" ").filterNot { it.isEmpty() }.map { it.toInt() }

}