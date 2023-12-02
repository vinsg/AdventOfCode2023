class Day1 {
    fun part1() {
        val sum = readFile("day1.txt").sumOf { line ->
            val list = line.filter {
                it.isDigit()
            }.toList()
            "${list.first()}${list.last()}".toInt()
        }
        println("Sum of all the digits: $sum")
    }

    fun part2() {
        val sum = readFile("day1.txt").sumOf { line ->
            val list = line
                .replace("one", "o1e", true)
                .replace("two", "t2o", true)
                .replace("three", "t3e", true)
                .replace("four", "f4r", true)
                .replace("five", "f5e", true)
                .replace("six", "s6x", true)
                .replace("seven", "s7n", true)
                .replace("eight", "e8t", true)
                .replace("nine", "n9e", true)
                .filter {
                    it.isDigit()
                }.toList()
            "${list.first()}${list.last()}".toInt()
        }
        println("Sum of all the digits: $sum")
    }
}
