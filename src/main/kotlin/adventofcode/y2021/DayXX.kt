package adventofcode.y2021

fun main() {
    val day = DayXX()
    println("Example 1 answer: ${day.example1()}")
    println("Part 1 answer: ${day.puzzle1()}")
    println("Example 2 answer: ${day.example2()}")
    println("Part 2 answer: ${day.puzzle2()}")



}

private class DayXX {
    fun example1 () = exampleInput.size
    fun puzzle1 () = puzzleInput.size
    fun example2 () = exampleInput.size
    fun puzzle2 () = puzzleInput.size
}

private val puzzleInput = getInput("Day01")
private val exampleInput = """
""".trimIndent().split("\n")