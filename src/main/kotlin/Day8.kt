import kotlin.math.abs

object Day8 {

    data class Node(val left: String, val right: String)

    fun part1(): Long {
        val input = readFile("day8.txt")
        val instructions = input[0]
        val map = mutableMapOf<String, Node>()
        input.drop(2).forEach { line ->
            val key = line.substringBefore(" ")
            val (left, right) = line.substringAfter("(").substringBefore(")").split(", ")
            map[key] = Node(left, right)
        }
        println(instructions)
        map.forEach { println(it) }

        var step = 0L
        var isArrived = false
        var position = "AAA"
        do {
            for (move in instructions) {
                position = if (move == 'L') {
                    map[position]!!.left
                } else {
                    map[position]!!.right
                }
                step += 1
                if (position == "ZZZ") {
                    isArrived = true
                    break
                }
            }
        } while (!isArrived)

        println("arrived in $step steps")
        return step
    }

    fun part2(): Long {
        val input = readFile("day8.txt")
        val instructions = input[0]
        val map = mutableMapOf<String, Node>()
        input.drop(2).forEach { line ->
            val key = line.substringBefore(" ")
            val (left, right) = line.substringAfter("(").substringBefore(")").split(", ")
            map[key] = Node(left, right)
        }
        println(instructions)
        map.forEach { println(it) }

        val positionList = map.filterKeys { it.endsWith("A") }.keys.toMutableList()

        val results = positionList.map {
            var step = 0L
            var isArrived = false
            var position = it
            do {
                for (move in instructions) {
                    position = if (move == 'L') {
                        map[position]!!.left
                    } else {
                        map[position]!!.right
                    }
                    step += 1
                    if (position.endsWith("Z")) {
                        isArrived = true
                        break
                    }
                }
            } while (!isArrived)
            step
        }
        println("resulting walks: $results")
        println("arrived in ${results.fold(1L) { acc, l -> acc * l }} steps")
        val response = results.reduce { acc, number ->
            lcm(acc, number)
        }
        println(response)
        return results.min()
    }

    private fun List<String>.isArrived(): Boolean {
        return this.all { it.endsWith("Z") }
    }

    fun lcm(a: Long, b: Long): Long {
        return if (a == 0L || b == 0L) 0 else abs(a * b) / gcd(a, b)
    }

    fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }
}