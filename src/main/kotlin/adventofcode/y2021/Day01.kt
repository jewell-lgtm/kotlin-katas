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
    val windows = input.mapIndexedNotNull  { index, _ ->
        if (index <= input.size - size) {
            input.subList(index, index + size)
        } else {
            null
        }
    }

    var result = 0
    var prev = windows.first().sum()

    windows.drop(1).forEach { window ->
        val sum = window.sum()
        if (sum > prev) {
            result += 1
        }
        prev = sum
    }

    return result
    

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





