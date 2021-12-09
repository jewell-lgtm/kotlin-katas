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
        input.map {
            it.trim().split("").mapNotNull {
                it.trim().toIntOrNull()
            }
        }

    private fun lowPoints(): List<Cell> =
        grid.flatMapIndexed { y, col ->
            col.mapIndexedNotNull { x, value ->
                val cell = Cell(y, x, value)
                val neighbors = grid.neighbors(x, y)
                if (cell < neighbors.smallest()) {
                    cell
                } else {
                    null
                }
            }
        }


    private fun basinSize(point: Cell): Int {
        val s = mutableSetOf<Cell>()
        checkNeighbors(point, s)

        return s.size
    }

    fun checkNeighbors(cell: Cell, checked: MutableSet<Cell>) {
        if (cell.value == 9) return
        if (checked.contains(cell)) return
        checked.add(cell)

        if (cell.y - 1 >= 0) {
            val newY = cell.y - 1
            val newX = cell.x
            checkNeighbors(Cell(newY, newX, grid[newY][newX]), checked)
        }
        if (cell.x - 1 >= 0) {
            val newY = cell.y
            val newX = cell.x - 1
            checkNeighbors(Cell(newY, newX, grid[newY][newX]), checked)
        }
        if (cell.y + 1 < grid.size) {
            val newY = cell.y + 1
            val newX = cell.x
            checkNeighbors(Cell(newY, newX, grid[newY][newX]), checked)
        }
        if (cell.x + 1 < grid[0].size) {
            val newY = cell.y
            val newX = cell.x + 1
            checkNeighbors(Cell(newY, newX, grid[newY][newX]), checked)
        }
    }


    private fun MutableSet<Cell>.location() =
        map { it.location() }.toSet()
}

private fun List<Int>.product(): Int = fold(1) { acc, i -> acc * i }


private fun List<Cell>.sum(): Int = sumOf { it.value }


private fun List<Cell>.smallest(): Cell = minByOrNull { it.value }!!

private fun List<List<Int>>.neighbors(cell: Cell, not: Set<Pair<Int, Int>> = emptySet()): List<Cell> {
    return neighbors(cell.x, cell.y, not)
}

private fun List<List<Int>>.neighbors(x: Int, y: Int, not: Set<Pair<Int, Int>> = emptySet()): List<Cell> {
    return listOf(-1, 0, 1).flatMap { dy ->
        listOf(-1, 0, 1).mapNotNull { dx ->
            val newX = x + dx
            val newY = y + dy
            val cellIsValid = (dy != 0 || dx != 0) && (dy == 0 || dx == 0) && newX > -1 && newY > -1
            if (cellIsValid && !not.contains(Pair(y, x))) {
                this.getOrNull(newY)?.getOrNull(newX)?.let { Cell(y, x, it) }
            } else {
                null
            }
        }
    }
}

private data class Cell(val y: Int, val x: Int, val value: Int) {
    operator fun compareTo(other: Cell): Int = value.compareTo(other.value)
    fun location(): Pair<Int, Int> = y to x
}

private val puzzleInput = getInput("Day09")
private val exampleInput = """
    2199943210
    3987894921
    9856789892
    8767896789
    9899965678
""".trimIndent().split("\n")