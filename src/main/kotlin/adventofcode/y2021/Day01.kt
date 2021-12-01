package adventofcode.y2021

fun main() {
    val day01 = Day01()

    println("Part one example ${day01.exampleCount()}")
    println("Part one ${day01.part1Count()}")
    println("Part two example ${day01.example2Count()}")
    println("Part two ${day01.part2Count()}")
}

class Day01 {
    fun exampleCount(): Int = largerThanPrevious(exampleInput)
    fun part1Count(): Int = largerThanPrevious(input)
    fun example2Count(): Int = largerThanPrevious(exampleInput2, 3)
    fun part2Count(): Int = largerThanPrevious(exampleInput2, 3)

    private fun largerThanPrevious(input: List<Int>, size: Int = 1): Int {
        val windows = input.window(size)

        return windows.drop(1).foldRightIndexed(0) { index, window, acc ->
            val prev = windows.getOrNull(index - 1)?.sum() ?: Int.MAX_VALUE
            if (window.sum() > prev) acc + 1 else acc
        }
    }
}

private fun <E> List<E>.window(
    size: Int
) = mapIndexedNotNull { index, _ ->
    if (index <= this.size - size) {
        subList(index, index + size)
    } else {
        null
    }
}


private val input = getIntInput("Day01")

private val exampleInput = """199
200
208
210
200
207
240
269
260
263""".split("\n").map { it.toInt() }

private val exampleInput2 = """199
200
208
210
200
207
240
269
260
263""".split("\n").map { it.toInt() }





