package adventofcode.y2021

import kotlin.system.measureTimeMillis

fun main() {
    val day = Day15()

    println("Example 1 answer: ${day.example1()}")
    println("Part 1 answer: ${day.puzzle1()}")
    println("Example 2 answer: ${day.example2()}")
    var part2: Int
    val time2Took = measureTimeMillis {
        part2 = day.puzzle2()
    }
    println("Part 2 answer: $part2 (${time2Took}ms)")
}

private class Day15 {
    fun example1() = parseInput(exampleInput, 1).bestPath()
    fun puzzle1() = parseInput(puzzleInput, 1).bestPath()
    fun example2() = parseInput(exampleInput, 5).bestPath()
    fun puzzle2() = parseInput(puzzleInput, 5).bestPath()


    fun parseInput(input: List<String>, mult: Int): Grid {
        val h = input.size
        val w = input.first().length

        val cellValues = input
            .flatMapIndexed { y, line ->
                line.split("").filter { it.isNotEmpty() }
                    .mapIndexedNotNull { x, digit ->
                        digit.toIntOrNull()?.let { Pair(Point(y, x), digit.toInt()) }
                    }
            }.toMap()


        return Grid(h * mult, w * mult) { point ->
            if (point.y < h && point.x < w) {
                cellValues[point]!!
            } else {
                val plus = (point.y / h) + (point.x / w)
                val destPoint = Point(point.y % h, point.x % w)
                val destVal = cellValues[destPoint]!!

                (destVal - 1 + plus) % 9 + 1
            }
        }
    }

    class Grid(val h: Int, val w: Int, val riskLevelAt: (Point) -> Int) {
        val points = (0 until h).flatMap { y -> (0 until w).map { x -> Point(y, x) } }
        val start = Point(0, 0)
        val goal = Point(h - 1, w - 1)

        fun bestPath(): Int {
            val bests = points.associateWith { if (it == start) 0 else Int.MAX_VALUE }.toMutableMap()
            fun Set<Point>.best(): Point = minByOrNull { bests[it]!! }!!

            val toCheck = mutableSetOf(start)

            while (toCheck.isNotEmpty()) {
                val node = toCheck.best()
                toCheck.remove(node)
                val currentNodeBest = bests[node]!!

                if (node == goal) {
                    return bests[node]!!
                }

                node.neighbors().forEach { neighbor ->
                    val costToVisit = currentNodeBest + riskLevelAt(neighbor)
                    if (costToVisit < bests[neighbor]!!) {
                        bests[neighbor] = costToVisit
                        toCheck.add(neighbor)
                    }
                }

            }
            error("didn't get to the end")
        }

        private fun Point.neighbors(): Set<Point> =
            listOf(up(), left(), right(), down())
                .filter { it.isValid() }
                .toSet()
        fun Point.isValid() = y in 0 until h && x in 0 until w


        fun printGrid() {
            (0 until h).forEach { y ->
                (0 until w).forEach { x ->
                    print("${riskLevelAt(Point(y, x))}")
                }
                print("\n")
            }
            print("\n")
        }
    }

    data class Point(val y: Int, val x: Int) {
        fun up() = Point(y - 1, x)
        fun right() = Point(y, x + 1)
        fun down() = Point(y + 1, x)
        fun left() = Point(y, x - 1)
    }
}


private fun <E> MutableSet<E>.pop(): E = first().also { this.remove(it) }


private val puzzleInput = getInput("Day15")
private val exampleInput = """
    1163751742
    1381373672
    2136511328
    3694931569
    7463417111
    1319128137
    1359912421
    3125421639
    1293138521
    2311944581
""".trimIndent().split("\n")
