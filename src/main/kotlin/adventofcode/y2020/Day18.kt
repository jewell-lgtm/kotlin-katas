package adventofcode.y2020

import assertEquals
import java.io.File

fun main() {
    val day = Day18()
    println("Example 1a answer: ${day.examplePart1()}")
    println("Example 1b answer: ${day.examplePart1b()}")
    println("Part 1 sum: ${day.part1()}")
}

class Day18 {
    fun examplePart1() = listOf(
        assertEquals(26, chomp("2 * 3 + (4 * 5)")),
        assertEquals(437, chomp("5 + (8 * 3 + 9 + 3 * 4 * 3)")),
        assertEquals(12240, chomp("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")),
        assertEquals(13632, chomp("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")),

        )


    fun examplePart1b() = assertEquals(chomp("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"), 13632)
    fun part1() = puzzleInput.foldRight(0.toLong()) { str, acc -> acc + chomp(str) }

    private val operation = """^(-?\d+) ([+*]) (-?\d+)""".toRegex()
    private val digits = """-?\d+""".toRegex()
    private fun chomp(untrimmed: String): Long {
        val input = untrimmed.trim()
        if (digits.matches(input)) return input.toLong()
        if (input.contains('(')) {
            val r = input.indexOfFirst { it == ')' }
            val l = input.substring(0..r).indexOfLast { it == '(' }
            val before = input.substring(0 until l).trim()
            val middle = input.substring((l + 1) until r).trim()
            val after = input.substring(r + 1).trim()
            val newMiddle = chomp(middle)

            return chomp(listOf(before, newMiddle, after).joinToString(" ") { it.toString().trim() })
        }
        if (operation.find(input) != null) {
            val replaced = operation.find(input)!!.destructured.let { (a, o, b) ->
                when (o) {
                    "+" -> input.replaceFirst(operation, (a.toLong() + b.toLong()).toString())
                    "*" -> input.replaceFirst(operation, (a.toLong() * b.toLong()).toString())
                    else -> TODO(o)
                }
            }
            return chomp(replaced.trim())
        }
        error(input)

    }


}


private const val exampleInput = "1 + (2 * 3) + (4 * (5 + 6))"


private val puzzleInput = File("aoc-input/day18.txt").readLines()
