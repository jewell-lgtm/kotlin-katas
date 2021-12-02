package adventofcode.y2021

fun main() {
    val day02 = Day02()

    println("Example 1 gives position ${day02.example1Position()} with product ${day02.example1Position().product()}")
    println("Part 1 gives position ${day02.part1Position()} with product ${day02.part1Position().product()}")
    println("Example 2 gives position ${day02.example2Position()} with product ${day02.example2Position().product()}")
    println("Part 2 gives position ${day02.part2Position()} with product ${day02.part2Position().product()}")
}

class Day02 {
    fun example1Position() = move(parseInput(exampleInput1), Position2D(0, 0))
    fun part1Position() = move(parseInput(puzzleInput), Position2D(0, 0))
    fun example2Position() = move(parseInput(exampleInput2), PositionAim(0, 0, 0))
    fun part2Position() = move(parseInput(puzzleInput), PositionAim(0, 0, 0))

    enum class Command {
        FORWARD, DOWN, UP
    }

    interface Position {
        operator fun plus(input: Pair<Command, Int>): Position
        fun product(): Int
    }

    data class Position2D(val h: Int, val depth: Int) : Position {
        override operator fun plus(input: Pair<Command, Int>): Position2D {
            val (dir, amount) = input
            return when (dir) {
                Command.FORWARD -> Position2D(h + amount, depth)
                Command.DOWN -> Position2D(h, depth + amount)
                Command.UP -> Position2D(h, (depth - amount).coerceAtLeast(0))
            }
        }

        override fun product(): Int = h * depth
    }

    data class PositionAim(val h: Int, val depth: Int, val aim: Int) : Position {
        override operator fun plus(input: Pair<Command, Int>): PositionAim {
            val (dir, amount) = input
            return when (dir) {
                Command.FORWARD -> PositionAim(h + amount, depth + (aim * amount), aim)
                Command.DOWN -> PositionAim(h, depth, aim + amount)
                Command.UP -> PositionAim(h, depth, aim - amount)
            }
        }

        override fun product(): Int = h * depth
    }

    private fun move(input: List<Pair<Command, Int>>, position: Position) =
        input.foldRight(position) { pair, acc -> acc + pair }


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

private val exampleInput2 = """
    forward 5
    down 5
    forward 8
    up 3
    down 8
    forward 2
""".trimIndent().split("\n")