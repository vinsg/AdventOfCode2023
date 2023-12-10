object Day6 {

    data class Race(val time: Long, val distance: Long) {
        fun byHolding(holding: Long): Long {
            return holding * (time - holding)
        }
    }

    fun part1(): Long {
        val input = readFile("day6.txt")
        val races = (input[0].substringAfter(": ").split(" ").filterNot { it.isEmpty() }
            .map { it.toLong() } zip input[1].substringAfter(":").split(" ").filterNot { it.isBlank() }
            .map { it.toLong() }).map {
            Race(it.first, it.second)
        }
        println(races)


        val result = races.map { race ->
            var r = 0L
            for (i in 1..<race.time) {
                if (race.byHolding(i) > race.distance) {
                    r += 1
                }
            }
            println("race $race has $r solutions")
            r
        }.fold(1L) { acc, i -> acc * i }

        println("result is $result")
        return result
    }

    fun part2(): Long {
        val race = Race(time = 42686985, 284100511221341)
        println("the race: $race")

        var result = 0
        for (i in 1..<race.time) {
            if (race.byHolding(i) > race.distance) {
                result += 1
            }
            if (i % 10000 == 0L) println(i)
        }

        println("result is $result")
        return 0
    }

}