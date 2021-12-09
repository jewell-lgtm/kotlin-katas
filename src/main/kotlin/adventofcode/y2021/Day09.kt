package adventofcode.y2021

fun main() {
    println("Example 1 answer: ${Day09(exampleInput).findLowPoints()}")
    println("Part 1 answer: ${Day09(puzzleInput).findLowPoints()}")
    println("Example 2 answer: ${Day09(exampleInput).biggestBasins()}")
    println("Part 2 answer: ${Day09(puzzleInput).biggestBasins()}")
}

private class Day09(input: List<String>) {
    val grid = parseInput(input)

    fun findLowPoints(): Int {
        val points = lowPoints()
        return points.size + points.sum()
    }

    fun biggestBasins(): Int {
        val points = lowPoints()
        val basins = points.map { basinSize(it) }

        return basins.sortedDescending().take(3).product()
    }


    private fun parseInput(input: List<String>): List<List<Int>> =
        input.map { s ->
            s.trim().split("").mapNotNull {
                it.trim().toIntOrNull()
            }
        }

    private fun lowPoints(): List<Cell> =
        grid.flatMapIndexed { y, col ->
            col.mapIndexedNotNull { x, value -> Cell(y, x, value).takeIf { it < it.neighbors().smallest() } }
        }


    private fun basinSize(point: Cell): Int {
        fun addCellsToBasin(cell: Cell, accumulator: MutableSet<Cell>): Set<Cell> {
            if (cell.value == 9) return accumulator // stop when you see a 9
            if (accumulator.contains(cell)) return accumulator // don't count cells twice
            accumulator.add(cell)

            if (cell.y - 1 >= 0) {
                val newY = cell.y - 1
                val newX = cell.x
                addCellsToBasin(Cell(newY, newX, grid[newY][newX]), accumulator)
            }

            if (cell.x - 1 >= 0) {
                val newY = cell.y
                val newX = cell.x - 1
                addCellsToBasin(Cell(newY, newX, grid[newY][newX]), accumulator)
            }

            if (cell.y + 1 < grid.size) {
                val newY = cell.y + 1
                val newX = cell.x
                addCellsToBasin(Cell(newY, newX, grid[newY][newX]), accumulator)
            }

            if (cell.x + 1 < grid[0].size) {
                val newY = cell.y
                val newX = cell.x + 1
                addCellsToBasin(Cell(newY, newX, grid[newY][newX]), accumulator)
            }

            return accumulator
        }


        return addCellsToBasin(point, mutableSetOf()).size

    }


    private fun List<Int>.product(): Int = fold(1) { acc, i -> acc * i }
    private fun List<Cell>.sum(): Int = sumOf { it.value }
    private fun List<Cell>.smallest(): Cell = minByOrNull { it.value }!!

    private fun List<List<Int>>.neighbors(x: Int, y: Int, exclude: Set<Pair<Int, Int>> = emptySet()): List<Cell> =
        listOf(
            -1 to 0,
            0 to 1,
            1 to 0,
            0 to -1
        ).mapNotNull { (dy, dx) ->
            val newX = x + dx
            val newY = y + dy

            if (!exclude.contains(Pair(y, x))) {
                this.getOrNull(newY)?.getOrNull(newX)?.let { Cell(y, x, it) }
            } else null
        }

    data class Cell(val y: Int, val x: Int, val value: Int) {
        operator fun compareTo(other: Cell): Int = value.compareTo(other.value)
    }

    private fun Cell.neighbors() = grid.neighbors(x, y)
}


private val puzzleInput = getInput("Day09")
private val exampleInput = """
    2199943210
    3987894921
    9856789892
    8767896789
    9899965678
""".trimIndent().split("\n")
