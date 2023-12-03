import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day2Test {

    @Test
    fun part1() {
        val response = Day2.part1()
        assertEquals(2486, response)
    }

    @Test
    fun part2() {
        val response = Day2.part2()
        assertEquals(87984, response)
    }
}