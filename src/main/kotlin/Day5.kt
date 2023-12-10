import kotlin.math.absoluteValue

object Day5 {
    data class Mapping(val start: Long, val dest: Long, val size: Long) {
        fun mappedValue(value: Long): Long? {
            return if (value in start..<start + size) {
                dest + (value - start)
            } else {
                null
            }
        }
    }

    data class MappingsGroup(val source: String, val destination: String, val mappings: List<Mapping>) {
        fun mappedValue(value: Long): Long {
            return mappings.firstNotNullOfOrNull { it.mappedValue(value) } ?: value
        }
    }

    // map in reverse and return the location for the first seed that exists.
    fun part2(): Long {
        val input = readFile("day5.txt")
        val seeds = input[0].substringAfter(": ").split(" ").chunked(2).map {
            val (start, size) = it
            start.toLong()..start.toLong() + size.toLong()
        }
        println("seeds: $seeds")

        // mapped but reversed
        val mappingGroups = input.drop(2).fold(mutableListOf(mutableListOf<String>())) { acc, s ->
            if (s.isBlank()) {
                acc.add(mutableListOf())
            } else {
                acc.last().add(s)
            }
            acc
        }.map { group ->
            val (source, _, destination) = group.first().split("-", " ")

            val mapping = group.drop(1).map { s ->
                val (dest, start, size) = s.split(" ").map { it.toLong() }
                Mapping(start = dest, dest = start, size)
            }.reversed()
            MappingsGroup(source = destination, destination = source, mapping)
        }.reversed()
        println(mappingGroups)

        var location = 0L

        // todo run in parallel
        while (true) {
            location += 1
            if (location % 100000 == 0L) println("location tested: $location")
            if (seeds.any { range ->
                    val seed = mappingGroups.fold(location) { acc, mapping ->
                        mapping.mappedValue(acc)
                    }
                    range.contains(seed)
                }) break
        }

        println("the lowest location is $location")
        return location
    }


    // blindly map every seed
    fun part1(): Long {
        val input = readFile("day5.txt")
        val seeds = input[0].substringAfter(": ").split(" ").map { it.toLong() }
        println("seeds: $seeds")
        val mappingGroups = input.drop(2).fold(mutableListOf(mutableListOf<String>())) { acc, s ->
            if (s.isBlank()) {
                acc.add(mutableListOf())
            } else {
                acc.last().add(s)
            }
            acc
        }.map { group ->
            val (source, _, destination) = group.first().split("-", " ")

            val mapping = group.drop(1).map { s ->
                val (dest, start, size) = s.split(" ").map { it.toLong() }
                Mapping(start, dest, size)
            }
            MappingsGroup(source, destination, mapping)
        }
        println(mappingGroups)

        val resultList = seeds.map { seed ->
            mappingGroups.fold(seed) { acc, mapping ->
                mapping.mappedValue(acc)
            }
        }.toList()


        return resultList.min()
    }
}