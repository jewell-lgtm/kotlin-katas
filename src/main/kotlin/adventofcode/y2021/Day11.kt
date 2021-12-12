package adventofcode.y2021

import assertEquals

fun main() {
    val day = Day11()
    println("Example 1 answer: ${day.example1()}")
    println("Part 1 answer: ${day.puzzle1()}")
    println("Example 2 answer: ${day.example2()}")
    println("Part 2 answer: ${day.puzzle2()}")


}

private class Day11 {
    fun example1(): Int {
        println(assertEquals(204, getResult(parseInput(exampleInput), 10).flashes))
        return getResult(parseInput(exampleInput), 100).flashes
    }
    fun puzzle1(): Int = getResult(parseInput(puzzleInput), 100).flashes
    fun example2(): Int = getResult(parseInput(exampleInput), 100).allFlash
    fun puzzle2(): Int = getResult(parseInput(puzzleInput), 100).allFlash


    data class Result(val flashes: Int, val allFlash: Int)
    private fun getResult(input: Grid, generations: Int): Result {
        var flashes = 0
        var g =-1
        for (generation in 0 until generations) {
//            println(generation)
//            input.printGrid()
            flashes += input.advance()
            if (input.all { it == 0}) {
                input.printGrid()
                error("g: $generation")
            }
//            println("")
//            println("")
            g = generation
        }
        while (!input.all { it == 0 }) {
            input.advance()
            g +=1
        }
        return Result(flashes, g + 1)
    }

    class Grid(val cells: List<List<Pair<Point, Int>>>) {

        val points = cells.flatMap { row -> row.map { it.first } }
        val values = cells.map { row -> row.map { it.second }.toMutableList() }

        fun advance(): Int {
            val result = mutableSetOf<Point>()
            points.forEach { point ->
                values[point.y][point.x] += 1
            }
            points.forEach { point -> if (!result.contains(point) && values[point.y][point.x] > 9) point.flash(result) }
            points.forEach { point ->
                if (values[point.y][point.x] > 9) values[point.y][point.x] = 0
            }
            return result.size
        }

        private fun Point.flash(didFlash: MutableSet<Point>): Set<Point> {
            if (!didFlash.contains(this)) {

                didFlash.add(this)
                neighboringPoints().forEach { neighbor ->
                    values[neighbor.y][neighbor.x] += 1
                    if (values[neighbor.y][neighbor.x] > 9) {
                        neighbor.flash(didFlash)
                    }
                }
            }

            return didFlash
        }


        fun printGrid() {
            cells.forEachIndexed { y, col ->
                col.forEachIndexed { x, _ ->
                    print(" ${values[y][x]} ")
                }
                print("\n")
            }
            print("\n")
        }

        fun all(function: (Int) -> Boolean): Boolean = values.all { it.all(function) }


    }


    data class Point(val h: Int, val w: Int, val y: Int, val x: Int) {

        fun neighboringPoints(): List<Point> =
            (-1..1).flatMap { dy ->
                (-1..1).mapNotNull { dx ->
                    if (dy == 0 && dx == 0) return@mapNotNull null
                    val newY = y + dy
                    val newX = x + dx
                    if (newY !in 0 until h || newX !in 0 until w) return@mapNotNull null

                    Point(h,
                          w,
                          newY,
                          newX
                    )

                }
            }

    }


    fun parseInput(input: List<String>): Grid = Grid(
        input.mapIndexed { y, row ->
            row.trim().mapIndexed { x, cell: Char ->
                Point(
                    input.size,
                    input.first().length,
                    y,
                    x
                ) to cell.toString().toInt()
            }
        }
    )


}


private val puzzleInput = getInput("Day11")
private val exampleInput = """
    5483143223
    2745854711
    5264556173
    6141336146
    6357385478
    4167524645
    2176841721
    6882881134
    4846848554
    5283751526
""".trimIndent().split("\n")
