object Day2 {

    /**
     * Problem:
     * As you walk, the Elf shows you a small bag and some cubes which are either red, green, or blue. Each time you play this game, he will hide a secret number of cubes of each color in the bag, and your goal is to figure out information about the number of cubes.
     *
     * To get information, once a bag has been loaded with cubes, the Elf will reach into the bag, grab a handful of random cubes, show them to you, and then put them back in the bag. He'll do this a few times per game.
     *
     * You play several games and record the information from each game (your puzzle input). Each game is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).
     *
     * For example, the record of a few games might look like this:
     *
     * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
     * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
     * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
     * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
     * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
     * In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the third set is only 2 green cubes.
     *
     * The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
     *
     * In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded with that configuration. However, game 3 would have been impossible because at one point the Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have been possible, you get 8.
     *
     * Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
     *
     * File: day2.txt
     * Solution: 2486
     */
    fun part1(): Int {
        val input = readFile("day2.txt")
        val sum = input.sumOf { game ->
            checkGameValidity(game)
        }


        println("sum of all possible games is: $sum")
        return sum
    }

    /**
     * Problem:
     * The Elf says they've stopped producing snow because they aren't getting any water! He isn't sure why the water stopped; however, he can show you how to get to the water source to check it out for yourself. It's just up ahead!
     *
     * As you continue your walk, the Elf poses a second question: in each game you played, what is the fewest number of cubes of each color that could have been in the bag to make the game possible?
     *
     * Again consider the example games from earlier:
     *
     * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
     * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
     * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
     * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
     * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
     * In game 1, the game could have been played with as few as 4 red, 2 green, and 6 blue cubes. If any color had even one fewer cube, the game would have been impossible.
     * Game 2 could have been played with a minimum of 1 red, 3 green, and 4 blue cubes.
     * Game 3 must have been played with at least 20 red, 13 green, and 6 blue cubes.
     * Game 4 required at least 14 red, 3 green, and 15 blue cubes.
     * Game 5 needed no fewer than 6 red, 3 green, and 2 blue cubes in the bag.
     * The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together. The power of the minimum set of cubes in game 1 is 48. In games 2-5 it was 12, 1560, 630, and 36, respectively. Adding up these five powers produces the sum 2286.
     *
     * For each game, find the minimum set of cubes that must have been present. What is the sum of the power of these sets?
     *
     * File: day2.txt
     * Solution: 87984
     */
    fun part2(): Int {
        val games = readFile("day2.txt")
        val sum = games.sumOf { game ->
            getGameScore(game)
        }


        println("sum of all possible games is: $sum")
        return sum
    }

    /**
     * Returns the multiplied value of all the highest hand values.
     */
    private fun getGameScore(game: String): Int {
        // get the list of games
        val listOfPlays = game.substringAfter(":").split(";")
        val maxPossibleHands = mutableMapOf<String, Int>()

        listOfPlays.forEach { play ->
            val hands = play.getHands()
            hands.forEach { hand ->
                maxPossibleHands.compute(hand.color) { _, existingNum ->
                    existingNum?.coerceAtLeast(hand.num) ?: hand.num
                }
            }
        }
        return maxPossibleHands.values.fold(1) { acc, i -> acc * i }
    }

    /**
     * Returns gameId if game is valid else 0.
     */
    private fun checkGameValidity(game: String): Int {
        val gameId = game.findGameNumber() // get the game number
        // get the list of games
        val listOfPlays = game.substringAfter(":").split(";")

        listOfPlays.forEach { play ->
            play.getHands().forEach { hand ->
                if (hand.num > 14) return 0
                if (hand.num > 13 && hand.color == "red" || hand.num > 13 && hand.color == "green") return 0
                if (hand.num > 12 && hand.color == "red") return 0
            }
        }
        return gameId
    }

    data class Hand(val num: Int, val color: String)

    private fun String.getHands(): List<Hand> {
        return """(\d+)\s*([a-zA-Z]+)""".toRegex().findAll(this).map { match ->
            Hand(match.groupValues[1].toInt(), match.groupValues[2])
        }.toList()
    }

    private fun String.findGameNumber(): Int {
        return """(\d+):""".toRegex().find(this)!!.groupValues[1].toInt()
    }
}