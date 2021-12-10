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

    private fun parseInput(input: List<String>): List<CharArray> {
        return input.map { it.toCharArray() }
    }

    data class ValidatorResult(val syntaxScore: Int, val completeString: String)

    private fun syntaxScores(input: List<CharArray>): Int = input.sumOf { validatorResult(it).syntaxScore }

    private fun completeScores(input: List<CharArray>): Long =
        input.map { scoreString(validatorResult(it).completeString) }.sorted().middle()

    private fun CharArray.isCorrupted(): Boolean = validatorResult(this).syntaxScore != 0
    private fun CharArray.isNotCorrupted(): Boolean = !isCorrupted()


    val open = "([{<".toCharArray()
    val close = ")]}>".toCharArray()
    val mapL = open.zip(close).toMap()
    val mapR = close.zip(open).toMap()
    val syntaxErrorScore = listOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137).toMap()

    private fun validatorResult(input: CharArray): ValidatorResult {

        val brackets = Stack<Char>()
        var head: Char? = input[0]
        var tail = input.drop(1)
        if (!(open.contains(head!!))) error("unexpected first char $head")

        while (head != null) {
            when {
                open.contains(head) -> brackets.push(head)
                close.contains(head) -> {
                    val matchOpen = mapR[head]!!
                    if (brackets.peek() == matchOpen) {
                        brackets.pop()
                    } else {
                        if (syntaxErrorScore.containsKey(head)) {
                            val thisScore = syntaxErrorScore[head]!!
                            return ValidatorResult(thisScore, "")
                        } else {
                            error("$head not in ${syntaxErrorScore.keys}")
                        }
                    }
                }
                else -> TODO("unknown $head")
            }
            head = tail.getOrNull(0)
            tail = tail.drop(1)
        }

        val str = StringBuilder().apply {
            while (brackets.isNotEmpty()) {
                val bracket = brackets.pop()
                val closeBracket = mapL[bracket]
                append(closeBracket)
            }
        }

        return ValidatorResult(0, str.toString())
    }


    val scores = listOf(')' to 1, ']' to 2, '}' to 3, '>' to 4).toMap()
    private fun scoreString(str: String) =
        str.fold(0.toLong()) { acc, char -> (acc * 5) + scores[char]!! }

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
