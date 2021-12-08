package adventofcode.y2021

import assertEquals

fun main() {
    val day = Day19()

    println(
        assertEquals(
            mapOf(
                'a' to 'd',
                'e' to 'b',
                'a' to 'c',
                'f' to 'd',
                'g' to 'e',
                'b' to 'f',
                'c' to 'g',
            ),
            day.configuration(
                listOf(
                    "acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb ab"
                )
            )
        )
    )
    println("Example 1 answer: ${day.example1()}")
    println("Part 1 answer: ${day.puzzle1()}")
    println("Example 2 answer: ${day.example2()}")
    println("Part 2 answer: ${day.puzzle2()}")


}
private typealias PuzzleInput = Pair<List<String>, List<String>>

private class Day19 {
    fun example1(): Int = digitsUsingUniqueOutputValues(parseInput(exampleInput))
    fun puzzle1(): Int = digitsUsingUniqueOutputValues(parseInput(puzzleInput))
    fun example2(): Int = decodeOutputValues(parseInput(exampleInput))
    fun puzzle2(): Int = -1

    fun parseInput(input: List<String>): List<PuzzleInput> =
        input.map { str -> str.split("|").map { it.trim().split(" ") }.toPair() }

    private fun digitsUsingUniqueOutputValues(parseInput: List<PuzzleInput>): Int {
        val output = parseInput.flatMap { it.second }
        return output.mapNotNull { if (uniqueNoSegments.values.contains(it.length)) it else null }.size
    }

    fun decodeOutputValues(input: List<PuzzleInput>): Int =
        input.fold(0) { acc, (input, output) -> acc + decodedOutputValue(input, output).sum() }

    fun decodedOutputValue(input: List<String>, output: List<String>): List<Int> {
        val result = mutableMapOf<Char, Int?>(
            'a' to null,
            'b' to null,
            'c' to null,
            'd' to null,
            'f' to null,
            'g' to null,
        )
        return listOf()
    }

    fun configuration(input: List<String>): Map<Char, Char> {
        val iinput = listOf("ab")

        return mapOf()
    }

    private fun possibleConfiguration(possible: Map<Char, Set<Char>>): Set<Char> {
        return setOf()
    }

    val normalConfig = mapOf(
        0 to "abcefg",
        1 to "cf",
        2 to "acdeg",
        3 to "acdfg",
        4 to "bcdf",
        5 to "abdfg",
        6 to "abdefg",
        7 to "acf",
        8 to "abcdefg",
        9 to "abcdfg"
    )

    val uniqueNoSegments = normalConfig.mapValues { it.value.length }.filterKeys { listOf(1, 4, 7, 8).contains(it) }
}


private fun <E> List<E>.toPair(): Pair<E, E> = Pair(this[0], this[1])

private val puzzleInput = getInput("Day08")
private val exampleInput = """
    be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
    edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
    fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
    fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
    aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
    fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
    dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
    bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
    egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
    gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
""".trimIndent().split("\n")