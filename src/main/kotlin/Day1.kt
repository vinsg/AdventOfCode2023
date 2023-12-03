object Day1 {
    /**
     * Problem:
     * The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value that the Elves now need to recover. On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
     *
     * For example:
     * 1abc2
     * pqr3stu8vwx
     * a1b2c3d4e5f
     * treb7uchet
     * In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.
     *
     * Consider your entire calibration document. What is the sum of all of the calibration values?
     *
     *
     * File: day1.txt
     * Solution: 55090
     */
    fun part1() {
        val sum = readFile("day1.txt").sumOf { line ->
            val list = line.filter {
                it.isDigit()
            }.toList()
            "${list.first()}${list.last()}".toInt()
        }
        println("Sum of all the digits: $sum")
    }

    /**
     * Problem:
     * Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".
     *
     * Equipped with this new information, you now need to find the real first and last digit on each line. For example:
     *
     * two1nine
     * eightwothree
     * abcone2threexyz
     * xtwone3four
     * 4nineeightseven2
     * zoneight234
     * 7pqrstsixteen
     * In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. Adding these together produces 281.
     *
     * What is the sum of all of the calibration values?
     * File: day1.txt
     * Solution: 54845
     */
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
