package adventofcode.y2021

import kotlin.math.sqrt

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
    fun example2(): Int = exampleInput.size
    fun puzzle2(): Int = puzzleInput.size

    private fun List<Line>.horizontalVertical() = filter { it.a.x == it.b.y || it.a.y == it.b.y }

    private fun count2OrMore(lines: List<Line>): Int {
        val assoc = grid(lines).associateWith { point ->
            lines.count { line -> line.intersects(point) }
        }
        return assoc.count { it.value > 1 }
    }

    private fun grid(lines: List<Line>) = (0..lines.maxX()).flatMap { y ->
        (0..lines.maxY()).map { x ->
            Point(x, y)
        }
    }

    private fun parseLines(input: List<String>): List<Line> = input.map { lineStr ->
        val (a, b) = lineStr.split(" -> ").map { pointStr ->
            val (x, y) = pointStr.split(",").map { it.trim().toInt() }
            Point(x, y)
        }
        Line(a, b)
    }

    data class Point(val x: Int, val y: Int)
    data class Line(val a: Point, val b: Point)

    private fun List<Line>.maxX(): Int = foldRight(-1) { line, acc -> maxOf(acc, maxOf(line.a.x, line.b.x)) }
    private fun List<Line>.maxY(): Int = foldRight(-1) { line, acc -> maxOf(acc, maxOf(line.a.y, line.b.y)) }

    private fun Line.intersects(point: Point): Boolean {
//        if (point.x < minOf(a.x, b.x)) return false
//        if (point.y < minOf(a.y, b.y)) return false
//        if (point.x > maxOf(a.x, b.x)) return false
//        if (point.y > maxOf(a.y, b.y)) return false
        return point.distance(a) + point.distance(b) == a.distance(b)
    }

    private fun Point.distance(other: Point): Double {
        val x1 = this.x.toDouble()
        val x2 = other.x.toDouble()
        val y1 = this.y.toDouble()
        val y2 = other.y.toDouble()
        return sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)))
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