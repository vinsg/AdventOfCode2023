import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day3Test {

    @Test
    fun part1() {
        val response = Day3.part1()
        assertEquals(546563, response)
    }

    @Test
    fun part2() {
        val response = Day3.part2()
        assertEquals(91031374, response)
    }
}