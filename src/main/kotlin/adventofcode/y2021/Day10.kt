package adventofcode.y2021

import java.util.*

fun main() {
    val day = Day10()
    println("Example 1 answer: ${day.example1()}")
    println("Part 1 answer: ${day.puzzle1()}")
    println("Example 2 answer: ${day.example2()}")
    println("Part 2 answer: ${day.puzzle2()}")
}

private class Day10 {
    fun example1(): Int = syntaxScores(parseInput(exampleInput))
    fun puzzle1(): Int = syntaxScores(parseInput(puzzleInput))
    fun example2(): Long = completeScores(parseInput(exampleInput2).filter { it.isNotCorrupted() })
    fun puzzle2(): Long = completeScores(parseInput(puzzleInput).filter { it.isNotCorrupted() })

    private fun parseInput(input: List<String>): List<CharArray> = input.map { it.toCharArray() }

    private fun syntaxScores(input: List<CharArray>): Int = input.sumOf { validatorResult(it).syntaxScore }
    private fun completeScores(input: List<CharArray>): Long =
        input.map { (validatorResult(it).stringScore()) }.sorted().middle()

    private fun CharArray.isCorrupted(): Boolean = validatorResult(this).syntaxScore != 0
    private fun CharArray.isNotCorrupted(): Boolean = !isCorrupted()


    val open = "([{<".toCharArray()
    val close = ")]}>".toCharArray()
    val mapL = open.zip(close).toMap()
    val mapR = close.zip(open).toMap()
    val invalidCharScore = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val incompleteScore = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

    private fun validatorResult(input: CharArray): ValidatorResult {
        val brackets = Stack<Char>()
        var head: Char? = input[0]
        var tail = input.drop(1)

        while (head != null) {
            when {
                open.contains(head) -> brackets.push(head)
                close.contains(head) -> {
                    val matchOpen = mapR[head]!!
                    if (brackets.peek() == matchOpen) {
                        brackets.pop()
                    } else {
                        return ValidatorResult(invalidCharScore[head]!!, "")
                    }
                }
                else -> TODO("unknown $head")
            }
            head = tail.getOrNull(0)
            tail = tail.drop(1)
        }

        return ValidatorResult(0, brackets.autoCompleteString())
    }

    private fun Stack<Char>.autoCompleteString(): String {
        val str = StringBuilder().apply {
            while (canPop()) {
                append(mapL[pop()])
            }
        }
        return str.toString()
    }

    // isNotEmpty is ambiguous in .apply context
    private fun Stack<Char>.canPop() = isNotEmpty()


    data class ValidatorResult(val syntaxScore: Int, val autoCompleteString: String) {
        companion object {

        }
    }

    private fun ValidatorResult.stringScore(): Long {
        return autoCompleteString.fold(0L) { acc, char -> (acc * 5) + incompleteScore[char]!! }
    }
}


private fun <E> List<E>.middle(): E {
    if (size % 2 == 0) error("there is no middle of $size elements")
    return this[size / 2]
}


private val puzzleInput = getInput("Day10")
private val exampleInput = """
    [({(<(())[]>[[{[]{<()<>>
    [(()[<>])]({[<{<<[]>>(
    {([(<{}[<>[]}>{[]{[(<()>
    (((({<>}<{<{<>}{[]{[]{}
    [[<[([]))<([[{}[[()]]]
    [{[{({}]{}}([{[{{{}}([]
    {<[[]]>}<{[{[{[]{()[[[]
    [<(<(<(<{}))><([]([]()
    <{([([[(<>()){}]>(<<{{
    <{([{{}}[<[[[<>{}]]]>[]]
""".trimIndent().split("\n")

private val exampleInput2 = """
    [({(<(())[]>[[{[]{<()<>>
    [(()[<>])]({[<{<<[]>>(
    (((({<>}<{<{<>}{[]{[]{}
    {<[[]]>}<{[{[{[]{()[[[]
    <{([{{}}[<[[[<>{}]]]>[]]
""".trimIndent().split("\n")
