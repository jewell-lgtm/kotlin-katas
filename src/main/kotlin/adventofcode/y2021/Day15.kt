package adventofcode.y2021

fun main() {
    val day = Day15()
    println("Example 1 answer: ${day.example1()}")
    println("Part 1 answer: ${day.puzzle1()}")
    println("Example 2 answer: ${day.example2()}")
    println("Part 2 answer: ${day.puzzle2()}")


}

private class Day15 {
    fun example1() = parseInput(exampleInput).bestPath()
    fun puzzle1() = parseInput(puzzleInput).bestPath()

    fun example2() = exampleInput.size
    fun puzzle2() = puzzleInput.size


    fun parseInput(input: List<String>): Grid {
        val h = input.size
        val w = input.first().length
        val cells = input
            .flatMapIndexed { y, line ->

                line.split("").filter { it.isNotEmpty() }
                    .mapIndexedNotNull { x, digit ->
                        digit.toIntOrNull()?.let { Pair(Pair(y, x), digit.toInt()) }
                    }
            }
        return Grid(h, w, cells)
    }

    class Grid(val h: Int, val w: Int, cellDescriptor: List<Pair<Pair<Int, Int>, Int>>) {
        val cells: Map<Point, Int> =
            cellDescriptor.associate { pair -> Point(h, w, pair.first.first, pair.first.second) to pair.second }

        fun bestPath(): Int {
            val memo = mutableMapOf<Point, Int>()
            // fastest way to anywhere along the x or y is in a straight line
            var totalCost = 0
            (0 until h).map { Point(h, w, it, 0) }.forEach { point ->
                totalCost += cells[point]!!
                memo[point] = totalCost
            }
            totalCost = 0
            (0 until w).map { Point(h, w, 0, it) }.forEach { point ->
                totalCost += cells[point]!!
                memo[point] = totalCost
            }
            // best path to each point (from top left is best of u or l)
            (1 until h).forEach { y ->
                (1 until w).forEach { x ->
                    val point = Point(h, w, y, x)
                    memo[point] = minOf(memo[point.up()]!!, memo[point.left()]!!) + cells[point]!!
                }
            }

            return memo[Point(h, w, h - 1, w - 1)]!! - memo[Point(h, w, 0, 0)]!!
        }


        val head = Point(h, w, 0, 0)

        fun printGrid() {
            (0 until h).forEach { y ->
                (0 until w).forEach { x ->

                    print(" ${cells[Point(h, w, y, x)]} ")
                }
                print("\n")
            }
            print("\n")
        }


        fun i(x: Int, y: Int) = x + w * y
    }

    data class Point(val h: Int, val w: Int, val y: Int, val x: Int) {
        fun up(): Point = Point(h, w, y - 1, x)
        fun left(): Point = Point(h, w, y, x - 1)
    }


}


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
