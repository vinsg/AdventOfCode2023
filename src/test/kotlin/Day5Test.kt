import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day5Test {

    @Test
    fun day5Part1() {
        val response = Day5.part1()
        assertEquals(226172555, response)
    }

    @Test
    fun day5Part2() {
        val response = Day5.part2()
        assertEquals(47909639, response)
    }
}