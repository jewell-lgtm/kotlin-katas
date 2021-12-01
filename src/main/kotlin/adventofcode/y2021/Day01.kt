package adventofcode.y2021

fun main() {
    val exampleInput = PuzzleInput(exampleInput.map { it.toInt() })
    val exampleCount = largerThanPrevious(exampleInput)
    println("The example input is $exampleCount")


    val input = PuzzleInput(input.map { it.toInt() })
    val count = largerThanPrevious(input)
    println("Part one $count")
    
    
    val example2Input = PuzzleInput(exampleInput2.map { it.toInt() })
    val ex2Count = largerThanPrevious(example2Input, 3)

    println("Part2 example $ex2Count")

    val part2Count = largerThanPrevious(input, 3)

    println("Part 2 is $part2Count")
}

private fun largerThanPrevious(input: PuzzleInput, size: Int = 1): Int {
    val windows = input.lines.mapIndexedNotNull  { index, _ ->  
        if (index <= input.lines.size - size) {
            input.lines.subList(index, index + size)
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


private val input = getInput("Day01")

private val exampleInput = """199
200
208
210
200
207
240
269
260
263""".split("\n")

private val exampleInput2 = """199
200
208
210
200
207
240
269
260
263""".split("\n")

private data class PuzzleInput(val lines: List<Int>)





