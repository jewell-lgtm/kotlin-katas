package adventofcode.y2021

fun main() {
    val day = Day07()

    println("Example 1 answer: ${day.example1()}")
    println("Part 1 answer: ${day.puzzle1()}")
    println("Example 2 answer: ${day.example2()}")
    println("Part 2 answer: ${day.puzzle2()}")
}

private class Day07 {
    fun example1(): Int = bestSpot(parseInput(exampleInput), Int::minus)
    fun puzzle1(): Int = bestSpot(parseInput(puzzleInput), Int::minus)
    fun example2(): Int = bestSpot(parseInput(exampleInput), calcFuel)
    fun puzzle2(): Int = bestSpot(parseInput(puzzleInput), calcFuel)

    fun parseInput(input: List<String>): List<Int> = input.first().split(",").map { it.toInt() }

    private fun bestSpot(input: List<Int>, distanceFn: DistanceFn): Int =
        (input.smallest()..input.largest()).minOf { input.totalDistanceFrom(it, distanceFn) }

    private fun List<Int>.totalDistanceFrom(target: Int, distanceFn: DistanceFn): Int =
        foldRight(0) { i, acc ->
            acc + distanceFn(maxOf(target, i), minOf(target, i))
        }

    private val memo = mutableMapOf<Int, Int>()
    private val calcFuel: DistanceFn = { big, small ->
        // works fine without implementing the tail recursive version
        fun calcFuel(i: Int): Int {
            if (!memo.containsKey(i)) {
                memo[i] = if (i < 2) i else calcFuel(i - 1) + i
            }
            return memo[i]!!
        }

        calcFuel(big - small)
    }


    private fun List<Int>.smallest(): Int = minOrNull() ?: first()
    private fun List<Int>.largest(): Int = maxOrNull() ?: first()
}

private typealias DistanceFn = (big: Int, small: Int) -> Int


private val puzzleInput = getInput("Day07")
private val exampleInput = """
    16,1,2,0,4,2,7,1,2,14
""".trimIndent().split("\n")
