package adventofcode.y2021

fun main() {
    val day02 = Day02()

    println("The example gives position ${day02.example1Position()} with product ${day02.example1Position().product()}")
    println("Part One gives position ${day02.part1Position()} with product ${day02.part1Position().product()}")



}

class Day02 {
    enum class Command {
        FORWARD, DOWN, UP
    }

    data class Position(val h: Int, val depth: Int) {
        operator fun plus(input: Pair<Command, Int>): Position {
            val (dir, amount) = input
            return when (dir) {
                Command.FORWARD -> Position(h + amount, depth)
                Command.DOWN -> Position(h, depth + amount)
                Command.UP -> Position(h, (depth - amount).coerceAtLeast(0))
            }
        }

        fun product(): Int  = h * depth
    }

    fun example1Position(): Position = move(parseInput(exampleInput1), Position(0, 0))
    fun part1Position(): Position = move(parseInput(puzzleInput), Position(0, 0))

    private tailrec fun move(input: List<Pair<Command, Int>>, position: Position): Position {
        if (input.isEmpty()) return position
        return move(input.drop(1), position + input.first())
    }

    private val regex = """(.+) (\d+)""".toRegex()
    private fun parseInput(input: List<String>): List<Pair<Command, Int>> = input.map {
        val (command, amountStr) = regex.find(it)?.destructured ?: error("no match for " + it)
        Pair(
            getCommand(command),
            amountStr.toInt()
        )
    }

    private fun getCommand(command: String): Command = when (command) {
        "forward" -> Command.FORWARD
        "down" -> Command.DOWN
        "up" -> Command.UP
        else -> error("Unrecognised command  $command")
    }


}

private val puzzleInput = getInput("Day02")

private val exampleInput1 = """
    forward 5
    down 5
    forward 8
    up 3
    down 8
    forward 2
""".trimIndent().split("\n")