package adventofcode.y2021

fun main() {
    val day = Day14()

    val example2 = day.example2()
    val puzzle2 = day.puzzle2()

    println("Example 1 answer: ${day.example1()}")
    println("Part 1 answer: ${day.puzzle1()}")
    println("Example 2 answer: $example2")
    println("Part 2 answer: $puzzle2")
}

private class Day14 {
    fun example1(): Int = run(parseInput(exampleInput), 10)
    fun puzzle1(): Int = run(parseInput(puzzleInput), 10)
    fun example2(): Int = run(parseInput(exampleInput), 40)
    fun puzzle2(): Int = run(parseInput(puzzleInput), 40)


    private fun run(input: PuzzleInput, generations: Int): Int {
        val rules = input.rules
        val substrate = input.substrate
        val charCount: MutableMap<Char, Int> = mutableMapOf()
        charCount.inc(substrate.toCountMap())

        repeat(generations) { count ->
            println("count $count")
            val added = substrate.mutateNext(rules)
            charCount.inc(added)
        }

        val result = charCount.mce() - charCount.lce()
        println("result ${result}")
        return result
    }


    fun parseInput(input: List<String>): PuzzleInput {
        val substrate = input.first()
        val rules = input.drop(2).map { Rule.from(it) }.toSet()
        return PuzzleInput(substrate.toMutableList(), rules)
    }

    data class PuzzleInput(val substrate: MutableList<Char>, val rules: Set<Rule>)
    data class Rule(val first: Char, val second: Char, val insert: Char) {
        fun match(a: Char, b: Char): Char? = if (a == first && b == second) insert else null

        companion object {
            fun from(str: String): Rule {
                val first = str[0]
                val second = str[1]
                val insert = str[str.length - 1]
                return Rule(first, second, insert)
            }
        }
    }

    private fun MutableList<Char>.mutateNext(rules: Set<Rule>): Map<Char, Int> {
        val result = mutableMapOf<Char, Int>()
        var i = size - 1
        while (i > 0) {
            val b = this[i]
            val a = this[i - 1]
            val insert = rules.firstNotNullOfOrNull { it.match(a, b) }
            if (insert != null) {
                result[insert] = result.getOrDefault(insert, 0) + 1
                add(i, insert)
            }
            i--
        }
        return result
    }


    private fun <K> MutableMap<K, Int>.inc(other: Map<K, Int>) {
        other.forEach { (key, value) ->
            this[key] = getOrDefault(key, 0) + value
        }
    }

    private fun <E> MutableList<E>.toCountMap(): Map<E, Int> =
        fold(mutableMapOf()) { acc, e -> acc.also { it[e] = acc.getOrDefault(e, 0) + 1 } }

    private fun <K> MutableMap<K, Int>.mce() =
        maxByOrNull { it.value }!!.value

    private fun <K> MutableMap<K, Int>.lce() =
        minByOrNull { it.value }!!.value


}


private val puzzleInput = getInput("Day14")
private val exampleInput = """
    NNCB

    CH -> B
    HH -> N
    CB -> H
    NH -> C
    HB -> C
    HC -> B
    HN -> C
    NN -> C
    BH -> H
    NC -> B
    NB -> B
    BN -> B
    BB -> N
    BC -> B
    CC -> N
    CN -> C
""".trimIndent().split("\n")