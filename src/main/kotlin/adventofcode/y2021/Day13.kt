package adventofcode.y2021

fun main() {
    println("Example 1 answer: ${foldOnY().size}")
    println("Part 1 answer: ${foldOnX().size}")
    println("Part 2 answer: ${folded(Day13(puzzleInput)).let { it.printGrid(); "^" }}")
}

private fun foldOnX() = Day13(puzzleInput)
    .let { parsed ->
        parsed.dots.foldX(parsed.xFold)
    }

private fun foldOnY() = Day13(exampleInput)
    .let { parsed ->
        parsed.dots.foldY(parsed.yFold)
    }

private fun folded(input: Day13): Set<Day13.Point> =
    input.folds.fold(input.dots) { acc, (dir, at) -> dir.foldGrid(acc, at) }


private class Day13(input: List<String>) {
    val dots = parseDots(input)
    val yFold = parseFold("fold along y=", input.first { it.contains("fold along y=") })
    val xFold = parseFold("fold along x=", input.first { it.contains("fold along x=") })

    val folds = parseFolds(input)


    private fun parseDots(input: List<String>): Set<Point> {
        return input.joinToString("\n")
            .split("\n\n")
            .first()
            .split("\n")
            .map { line ->
                line.split(",")
                    .map { digit -> digit.toInt() }
                    .let { i -> Point(i[0], i[1]) }
            }.toSet()
    }


    private fun parseFolds(input: List<String>): List<Pair<Dir, Int>> {
        return input.mapNotNull { line ->
            if (line.contains("x")) {
                Pair(Dir.X, parseFold("fold along x=", line))
            } else if (line.contains("y")) {
                Pair(Dir.Y, parseFold("fold along y=", line))
            } else null
        }
    }

    private fun parseFold(delimiter: String, str: String): Int {
        return str.split(delimiter)[1].toInt()
    }

    data class Point(val x: Int, val y: Int) {
        fun reflectY(at: Int): Point = Point(x, at - (y - at))
        fun reflectX(at: Int): Point = Point(at - (x - at), y)
    }

    enum class Dir {
        X, Y
    }

}

private fun Day13.Dir.foldGrid(grid: Set<Day13.Point>, at: Int): Set<Day13.Point> =
    if (this == Day13.Dir.X) {
        grid.foldX(at)
    } else {
        grid.foldY(at)
    }


private fun Set<Day13.Point>.foldY(at: Int): Set<Day13.Point> =
    fold(mutableSetOf()) { acc, point ->
        acc.also {
            it.add(
                if (point.y < at) {
                    point
                } else {
                    point.reflectY(at)
                }
            )
        }
    }

private fun Set<Day13.Point>.foldX(at: Int): Set<Day13.Point> =
    fold(mutableSetOf()) { acc, point ->
        acc.also {
            it.add(
                if (point.x < at) {
                    point
                } else {
                    point.reflectX(at)
                }
            )
        }
    }

private fun Set<Day13.Point>.printGrid() {
    val maxY: Int = maxByOrNull { it.y }?.y ?: 0
    val maxX: Int = maxByOrNull { it.x }?.x ?: 0
    (0..maxY).forEach { y ->
        (0..maxX).forEach { x ->
            print(if (contains(Day13.Point(x, y))) '█' else ' ')
        }
        print("\n")
    }
}


private val puzzleInput = getInput("Day13")

private val exampleInput = """
    6,10
    0,14
    9,10
    0,3
    10,4
    4,11
    6,0
    6,12
    4,1
    0,13
    10,12
    3,4
    3,0
    8,4
    1,10
    2,14
    8,10
    9,0

    fold along y=7
    fold along x=5
""".trimIndent().split("\n")

