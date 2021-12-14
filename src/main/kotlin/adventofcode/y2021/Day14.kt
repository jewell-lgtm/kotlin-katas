package adventofcode.y2021

fun main() {
    val day = Day14()

    println("Example 1: ${day.example1()}")
    println("Part 1 answer: ${day.puzzle1()}")
    println("Example 2 answer: ${day.example2()}")
    println("Part 2 answer: ${day.puzzle2()}")
}

private class Day14 {
    fun example1(): Long = run(parseInput(exampleInput), 10)
    fun puzzle1(): Long = run(parseInput(puzzleInput), 10)
    fun example2(): Long = run(parseInput(exampleInput), 40)
    fun puzzle2(): Long = run(parseInput(puzzleInput), 40)


    private fun run(input: PuzzleInput, generations: Int): Long {
        val rules = input.rules
        val substrate = input.substrate.toMutableList()
        val result = substrate.toMutableCountMap()
        val memo = mutableMapOf<Triple<Int, Char, Char>, Map<Char, Long>>()

        while (substrate.size > 1) {
            val start = substrate.removeFirst()
            val next = substrate[0]
            result.accumulate(
                charsBetween(memo, generations, rules, start, next)
            )
        }


        return result.mce() - result.lce()
    }


    private fun charsBetween(
        memo: MutableMap<Triple<Int, Char, Char>, Map<Char, Long>>,
        maxDepth: Int,
        rules: Set<Rule>,
        start: Char,
        end: Char,
    ): Map<Char, Long> {

        fun charsBetween(depth: Int, start: Char, end: Char, acc: MutableMap<Char, Long>): Map<Char, Long> {
            if (depth == maxDepth) return acc
            val memoKey = Triple(depth, start, end)
            if (!memo.containsKey(memoKey)) {
                val inserted = rules.firstNotNullOf { it.match(start, end) }

                memo[memoKey] = mutableMapOf(inserted to 1L)
                    .accumulate(
                        charsBetween(depth + 1, start, inserted, acc)
                    ).accumulate(
                        charsBetween(depth + 1, inserted, end, acc)
                    )
            }
            return memo[memoKey]!!
        }


        return charsBetween(0, start, end, mutableMapOf())
    }


    fun parseInput(input: List<String>): PuzzleInput {
        val substrate = input.first()
        val rules = input.drop(2).map { Rule.from(it) }.toSet()
        return PuzzleInput(substrate.toMutableList(), rules)
    }

    data class PuzzleInput(val substrate: List<Char>, val rules: Set<Rule>)
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

    // first implementation
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

    private fun <E> List<E>.toMutableCountMap(): MutableMap<E, Long> = toCountMap().toMutableMap()

    private fun <E> List<E>.toCountMap(): Map<E, Long> =
        fold(mutableMapOf()) { acc, e -> acc.also { it[e] = acc.getOrDefault(e, 0) + 1 } }

    private fun <K> MutableMap<K, Long>.mce() =
        maxByOrNull { it.value }!!.value

    private fun <K> MutableMap<K, Long>.lce() =
        minByOrNull { it.value }!!.value

    private fun <K> MutableMap<K, Long>.accumulate(other: Map<K, Long>): MutableMap<K, Long> {
        other.forEach { (key, value) -> this[key] = this.getOrDefault(key, 0) + value }
        return this
    }

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
