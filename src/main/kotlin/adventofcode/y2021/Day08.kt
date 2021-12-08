package adventofcode.y2021

fun main() {
    val day = Day19()

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
    fun puzzle2(): Int = decodeOutputValues(parseInput(puzzleInput))


    fun parseInput(input: List<String>): List<PuzzleInput> =
        input.map { str -> str.split("|").map { it.trim().split(" ") }.toPair() }

    private fun digitsUsingUniqueOutputValues(parseInput: List<PuzzleInput>): Int {
        val output = parseInput.flatMap { it.second }
        return output.mapNotNull { if (uniqueNoSegments.values.contains(it.length)) it else null }.size
    }

    fun decodeOutputValues(input: List<PuzzleInput>): Int =
        input.fold(0) { acc, (input, output) -> acc + decodeOutputValue(input, output) }

    fun decodeOutputValue(input: List<String>, output: List<String>): Int =
        decodeOutputDigits(input, output).joinToString("").toInt()

    private fun decodeOutputDigits(
        input: List<String>,
        output: List<String>,
    ) = decodedChars(configuration(input), output).map { decodeDigits[it]!! }

    fun configuration(input: List<String>): Map<Char, Char> {
        val cf = input.only { it.length == 2 }.toCharSet()
        val acf = input.only { it.length == 3 }.toCharSet()
        val bcdf = input.only { it.length == 4 }.toCharSet()
        val abcdefg = input.only { it.length == 7 }.toCharSet()

        val len6 = input.filter { it.length == 6 }.map { it.toCharSet() }
        // 6 segment numbers are all missing exactly one of c d or e
        val cde = len6.map { abcdefg - it }.reduce { first, second -> first.union(second) }

        val a = acf - cf
        val eg = (abcdefg - bcdf) - a
        val bfg = (abcdefg - cde) - a
        val bf = bfg - eg
        val cd = bcdf - bf

        val b = bf - cf
        val g = bfg - bf
        val e = eg - g
        val f = bf - b
        val c = cf - f
        val d = cd - c

        return mapOf(
            a.only() to 'a',
            b.only() to 'b',
            c.only() to 'c',
            d.only() to 'd',
            e.only() to 'e',
            f.only() to 'f',
            g.only() to 'g',
        )
    }

    fun decodedChars(config: Map<Char, Char>, originalOutput: List<String>): List<Set<Char>> =
        originalOutput.map {
            it.toList().map { char -> config[char]!! }.toSortedSet()
        }


    val normalConfig = mapOf(
        0 to "abcefg".toCharSet(), // 6 // missing d
        1 to "cf".toCharSet(), // 2
        2 to "acdeg".toCharSet(), // 5
        3 to "acdfg".toCharSet(), // 5
        4 to "bcdf".toCharSet(), // 4
        5 to "abdfg".toCharSet(), // 5
        6 to "abdefg".toCharSet(), // 6 // missing c
        7 to "acf".toCharSet(), // 3
        8 to "abcdefg".toCharSet(), // 7
        9 to "abcdfg".toCharSet() // 6 // missing e
    )

    val decodeDigits = mapOf(
          "abcefg".toCharSet() to 0,
          "cf".toCharSet() to 1,
          "acdeg".toCharSet() to 2,
          "acdfg".toCharSet() to 3,
          "bcdf".toCharSet() to 4,
          "abdfg".toCharSet() to 5,
          "abdefg".toCharSet() to 6,
          "acf".toCharSet() to 7,
          "abcdefg".toCharSet() to 8,
          "abcdfg".toCharSet() to 9,
    )

    val uniqueNoSegments = normalConfig.mapValues { it.value.size }.filterKeys { listOf(1, 4, 7, 8).contains(it) }
}

private fun <E> Set<E>.only(): E {
    if (size != 1) {
        error("$size != 1")
    }
    return first()
}

private fun <E> List<E>.only(function: (E) -> Boolean): E {
    val f = filter(function)
    if (f.size != 1) {
        error("f.size ${f.size}")
    }
    return f.first()
}


private fun <E> List<E>.toPair(): Pair<E, E> = Pair(this[0], this[1])
private fun String.toCharList(): List<Char> = map { it }
private fun String.toCharSet(): Set<Char> = toCharList().toSet()


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
