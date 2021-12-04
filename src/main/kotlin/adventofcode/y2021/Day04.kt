package adventofcode.y2021

fun main() {
    val day = Day04()

    println("Example 1 ${day.example1()}")
    println("Part 1 ${day.puzzle1()}")

}

class Day04 {
    fun example1(): Int = getScore(parseInput(exampleInput))
    fun puzzle1(): Int = getScore(parseInput(puzzleInput))

    private fun getScore(input: Pair<List<Int>, List<Board>>): Int {
        val (toCall, boards) = input
        var i = 0

        tailrec fun getWinningBoard(lastCalled: Int, toCall: List<Int>): Pair<Int, Board> {
            i += 1
            if (i > 100) error("too many steps")
            boards.firstOrNull { it.hasWon() }?.let { return lastCalled to it }
            val call = toCall.first()
            boards.call(call)
            return getWinningBoard(call, toCall.drop(1))
        }

        val (lastCalled, board) = getWinningBoard(-1, toCall)



        return board.score(lastCalled)
    }

    private fun parseInput(input: List<String>): Pair<List<Int>, List<Board>> {
        val numbers = input.first().split(",").map { it.trim().toInt() }
        val windows = input.drop(2).joinToString("\n").split("""\n\n""".toRegex()).map { it.split("\n") }
        val boards = windows.map { Board.create(it) }

        return Pair(numbers, boards)
    }

    data class Board(val numbers: List<List<Int>>) {
        private val called: List<MutableList<Boolean>> = numbers.map { row -> row.map { false }.toMutableList() }

        fun hasWon(): Boolean {
            return rows().any { it.allTrue() } || cols().any { it.allTrue() }
        }

        private fun rows(): List<List<Boolean>> =
            called

        private fun cols(): List<List<Boolean>> {
            val w = numbers.first().size
            return (0 until w).map { i -> called.map { it[i] } }
        }


        fun call(number: Int) {
            numbers.forEachIndexed { indexY, cols ->
                cols.forEachIndexed { indexX, i ->
                    if (i == number) {
                        called[indexY][indexX] = true
                    }
                }
            }
        }

        fun score(lastCalled: Int): Int {
            val uncalled = called.flatMapIndexed { indexY, row ->
                row.mapIndexedNotNull { indexX, wasCalled -> if (!wasCalled) numbers[indexY][indexX] else null }
            }
            return uncalled.sum() * lastCalled
        }


        companion object {
            fun create(input: List<String>) =
                Board(input.map { row ->
                    row.trim().split("""\s+""".toRegex()).map { col ->
                        col.trim().toInt()
                    }
                })
        }
    }

    private fun List<Board>.call(number: Int) {
        forEach { it.call(number) }
    }


}

private fun List<Boolean>.allTrue(): Boolean =
    none { !it }


private val puzzleInput = getInput("Day04")

private val exampleInput = """
7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7
""".trimIndent().split("\n")