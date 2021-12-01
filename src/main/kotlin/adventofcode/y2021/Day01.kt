package adventofcode.y2021

fun main() {
    val exampleCount = largerThanPrevious(exampleInput)
    println("The example input is $exampleCount")

    val count = largerThanPrevious(input)
    println("Part one $count")

    val ex2Count = largerThanPrevious(exampleInput2, 3)
    println("Part2 example $ex2Count")

    val part2Count = largerThanPrevious(input, 3)
    println("Part 2 is $part2Count")
}

private fun largerThanPrevious(input: List<Int>, size: Int = 1): Int {
    val windows = input.window(size)

    return windows.drop(1).foldRightIndexed(0) { index, window, acc ->
        val prev = windows.getOrNull(index - 1)?.sum() ?: Int.MAX_VALUE
        if (window.sum() > prev) acc + 1 else acc
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





