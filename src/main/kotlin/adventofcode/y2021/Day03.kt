package adventofcode.y2021

fun main() {
    val day = Day03()
    println("Example 1 gamma epsilon product is ${day.example1()}")
    println("Part 1 gamma epsilon product is ${day.part1()}")
    println("Example 2 O2 CO2 product is ${day.example2()}")
    println("Part 2 O2 CO2 product is ${day.part2()}")
}

class Day03 {
    fun example1() = powerConsumption(exampleInput)
    fun part1() = powerConsumption(puzzleInput)
    fun example2() = lifeSupport(exampleInput)
    fun part2() = lifeSupport(puzzleInput)

    private fun powerConsumption(input: List<String>): Int {
        val gamma = input.sideways().map { it -> (it.count { it == '0' } > it.count { it == '1' }) }.map { it.toChar() }
            .joinToString("")
            .toInt(2)
        val epsilon =
            input.sideways().map { it -> (it.count { it == '0' } > it.count { it == '1' }) }.map { it.toChar() }
                .joinToString("").complement().toInt(2)

        return gamma * epsilon

    }

    private fun lifeSupport(input: List<String>): Int {
        tailrec fun o2Rating(remaining: Set<String>, i: Int): String {
            if (remaining.size == 1) return remaining.first()
            val ones = remaining.toList().map { it[i] }.count { it == '1' }
            val notones = remaining.size - ones

            return o2Rating(remaining.filter { it[i] == if (ones >= notones) '1' else '0' }.toSet(), i + 1)
        }

        tailrec fun co2Rating(remaining: Set<String>, i: Int): String {
            if (remaining.size == 1) return remaining.first()
            val ones = remaining.toList().map { it[i] }.count { it == '1' }
            val notones = remaining.size - ones

            return co2Rating(remaining.filter { it[i] == if (ones >= notones) '0' else '1' }.toSet(), i + 1)
        }

        val o2 = o2Rating(input.toSet(), 0).toInt(2)
        val co2 = co2Rating(input.toSet(), 0).toInt(2)

        return o2 * co2
    }

    private fun List<String>.sideways(): List<String> {
        val w = first().length
        return (0 until w).map { i -> map { it[i] }.joinToString("") }
    }

    private fun String.complement(): String {
        return map { if (it == '0') '1' else '0' }.joinToString("")
    }


}


private fun Boolean.toChar(): Char = if (this) '1' else '0'


private val exampleInput = """
    00100
    11110
    10110
    10111
    10101
    01111
    00111
    11100
    10000
    11001
    00010
    01010
""".trimIndent().split("\n")

private val puzzleInput = getInput("Day03")
