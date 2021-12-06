package adventofcode.y2021

fun main() {
    val day = Day06()
//    exampleLists.split("\n").forEachIndexed { index, s ->
//        println("${18 - index} ${day.parseInput(listOf(s))}")
//    }

    println("Example 1 answer: ${day.example1()}")
    println("Example 1a answer: ${day.example1a()}")
    println("Part 1 answer: ${day.puzzle1()}")
    println("Example 2 answer: ${day.example2()}")
    println("Part 2 answer: ${day.puzzle2()}")


}

private class Day06 {
    fun example1(): Long = parseInput(exampleInput).after(18).total()
    fun example1a(): Long = parseInput(exampleInput).after(80).total()
    fun puzzle1(): Long = parseInput(puzzleInput).after(80).total()
    fun example2(): Long = parseInput(exampleInput).after(256).total()
    fun puzzle2(): Long = parseInput(puzzleInput).after(256).total()


    fun parseInput(list: List<String>): Map<Long, Long> =
        list.first().split(",").map { it.trim().toLong() }.foldRight(mutableMapOf<Long, Long>()) { i, acc ->
            acc[i] = acc.getOrDefault(i, 0) + 1
            acc
        }.toSortedMap()


    private fun Map<Long, Long>.after(generations: Long): Map<Long, Long> {
        tailrec fun recursionForTheSakeOfIt(map: MutableMap<Long, Long>, generations: Long): Map<Long, Long> {
            if (generations == 0.toLong()) return map


            (-1..8).forEach { i ->
                map[i.toLong()] = map.getOrDefault((i + 1).toLong(), 0.toLong())
            }
            val respawns = map[-1]!!
            map[8] = respawns
            map[6] = map[6]!! + respawns
            map[-1] = 0

            return recursionForTheSakeOfIt(map, generations - 1)
        }

        return recursionForTheSakeOfIt(toMutableMap(), generations)
    }


    private fun Map<Long, Long>.total(): Long =
        values.sum()


}

private val puzzleInput = getInput("Day06")
private val exampleInput = """
    3,4,3,1,2
""".trimIndent().split("\n")


val exampleLists = """
    3,4,3,1,2
    2,3,2,0,1
    1,2,1,6,0,8
    0,1,0,5,6,7,8
    6,0,6,4,5,6,7,8,8
    5,6,5,3,4,5,6,7,7,8
    4,5,4,2,3,4,5,6,6,7
    3,4,3,1,2,3,4,5,5,6
    2,3,2,0,1,2,3,4,4,5
    1,2,1,6,0,1,2,3,3,4,8
    0,1,0,5,6,0,1,2,2,3,7,8
    6,0,6,4,5,6,0,1,1,2,6,7,8,8,8
    5,6,5,3,4,5,6,0,0,1,5,6,7,7,7,8,8
    4,5,4,2,3,4,5,6,6,0,4,5,6,6,6,7,7,8,8
    3,4,3,1,2,3,4,5,5,6,3,4,5,5,5,6,6,7,7,8
    2,3,2,0,1,2,3,4,4,5,2,3,4,4,4,5,5,6,6,7
    1,2,1,6,0,1,2,3,3,4,1,2,3,3,3,4,4,5,5,6,8
    0,1,0,5,6,0,1,2,2,3,0,1,2,2,2,3,3,4,4,5,7,8
    6,0,6,4,5,6,0,1,1,2,6,0,1,1,1,2,2,3,3,4,6,7,8,8,8,8
""".trimIndent()