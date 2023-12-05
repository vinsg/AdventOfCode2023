import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day4Test {

    @Test
    fun part1Test() {
        val response = Day4.part1()
        assertEquals(24706, response)
    }

    @Test
    fun part2Test() {
        val response = Day4.part2()
        assertEquals(13114317, response)
    }
}