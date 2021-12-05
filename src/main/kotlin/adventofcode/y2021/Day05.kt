package adventofcode.y2021

import kotlin.math.sign

fun main() {
    val day = Day05()
    println("Example 1 answer: ${day.example1()}")
    println("Part 1 answer: ${day.puzzle1()}")
    println("Example 2 answer: ${day.example2()}")
    println("Part 2 answer: ${day.puzzle2()}")
}

private class Day05 {
    fun example1(): Int = count2OrMore(parseLines(exampleInput).horizontalVertical())
    fun puzzle1(): Int = count2OrMore(parseLines(puzzleInput).horizontalVertical())
    fun example2(): Int = count2OrMore(parseLines(exampleInput))
    fun puzzle2(): Int = count2OrMore(parseLines(puzzleInput))


    private fun List<Line>.horizontalVertical() = filter { it.a.x == it.b.x || it.a.y == it.b.y }

    private fun count2OrMore(lines: List<Line>): Int {
        val counts = lines.flatMap { it.points() }.foldRight(mutableMapOf<Point, Int>()) { point, result ->
            result[point] = result.getOrDefault(point, 0) + 1
            result
        }

        return counts.values.count { it > 1 }
    }

    private fun parseLines(input: List<String>): List<Line> = input.map { lineStr ->
        val (a, b) = lineStr.split(" -> ").map { pointStr ->
            val (x, y) = pointStr.split(",").map { it.trim().toInt() }
            Point(x, y)
        }
        Line(a, b)
    }

    data class Point(val x: Int, val y: Int)
    data class Line(val a: Point, val b: Point) {
        fun points(): List<Point> {

            tailrec fun points(acc: Set<Point>, curr: Point, dest: Point): Set<Point> {
                if (curr == dest) return acc

                val dx = (dest.x - curr.x).sign
                val dy = (dest.y - curr.y).sign

                return points(acc + listOf(curr), Point(curr.x + dx, curr.y + dy), dest)
            }

            return points(setOf(a, b), a, b).toList()
        }
    }
}


private val puzzleInput = getInput("Day05")
private val exampleInput = """
    0,9 -> 5,9
    8,0 -> 0,8
    9,4 -> 3,4
    2,2 -> 2,1
    7,0 -> 7,4
    6,4 -> 2,0
    0,9 -> 2,9
    3,4 -> 1,4
    0,0 -> 8,8
    5,5 -> 8,2
""".trimIndent().split("\n")