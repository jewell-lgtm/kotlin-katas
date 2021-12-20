package adventofcode.y2021

fun main() {
    println("Example 1 answer: ${Day20(exampleInput).part1()}")
    println("Part 1 answer: ${Day20(puzzleInput).part1()}")


}

private class Day20(val lines: List<String>) {
    fun part1(): Int {
        val roundOne =
            (-1 until inputImage.size + 1).map { y ->
                (-1 until inputImage.first().size + 1).map { x ->
                    Pixel(y, x).value(inputImage)
                }
            }

        val roundTwo =
            (-20 until roundOne.size + 20).map { y ->
                (-20 until roundOne.first().size + 20).map { x ->
                    Pixel(y, x).value(roundOne)
                }
            }

        var count = 0
        for (row in roundTwo) {
            for (col in row) {
                print(col.toPuzzleChar())
                if (col) {
                    count += 1
                }
            }
            print("\n")
        }


        return count
    }


    val algoLine = lines.first()
    val inputImage: List<List<Boolean>> =
        lines.drop(2).map { line -> line.split("").filter { it.isNotEmpty() }.map { it == "#" } }


    data class Pixel(val y: Int, val x: Int) {
        fun neighbors(): List<Pixel> =
            (-1..1).flatMap { dY ->
                (-1..1).map { dX ->
                    Pixel(this.y + dY, this.x + dX)
                }
            }
    }

    fun Pixel.value(input: List<List<Boolean>>): Boolean {
        val inputValues: List<Boolean> = neighbors().fold(listOf()) { acc, pixel ->
            acc + listOf(input.getOrNull(pixel.y)?.getOrNull(pixel.x) ?: false)
        }
        val inputStr = inputValues.joinToString("") { if (it) "1" else "0" }
        val inputDec = inputStr.toInt(2)
        return algoLine[inputDec] == '#'
    }


}

private fun Boolean.toPuzzleChar(): Char = if (this) '#' else '.'


private val puzzleInput = getInput("Day20")
private val exampleInput = """
    ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#

    #..#.
    #....
    ##..#
    ..#..
    ..###
""".trimIndent().split("\n")