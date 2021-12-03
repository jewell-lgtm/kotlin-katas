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

    private fun parseInput(input: List<String>): List<Command> = input.map { str ->
        str.split(" ")
            .let { (command, amountStr) -> Command.fromStrings(command, amountStr) }
    }

    private fun move(input: List<Command>, position: Position) =
        input.foldRight(position) { pair, acc -> acc + pair }

    data class Command(val direction: Direction, val amount: Int) {
        companion object {
            fun fromStrings(dir: String, amount: String): Command {
                val direction = when (dir) {
                    "forward" -> Direction.FORWARD
                    "down" -> Direction.DOWN
                    "up" -> Direction.UP
                    else -> error("Unrecognised command  $dir")
                }
                return Command(direction, amount.toInt())
            }

        }
    }

    enum class Direction {
        FORWARD, DOWN, UP
    }

    interface Position {
        operator fun plus(command: Command): Position
        fun product(): Int
    }

    private data class Position2D(val h: Int, val depth: Int) : Position {
        override operator fun plus(command: Command): Position2D =
            when (command.direction) {
                Direction.FORWARD -> Position2D(h + command.amount, depth)
                Direction.DOWN -> Position2D(h, depth + command.amount)
                Direction.UP -> Position2D(h, (depth - command.amount).coerceAtLeast(0))
            }

        override fun product(): Int = h * depth
    }

    private data class PositionAim(val h: Int, val depth: Int, val aim: Int) : Position {
        override operator fun plus(command: Command): PositionAim {
            return when (command.direction) {
                Direction.FORWARD -> PositionAim(h + command.amount, depth + (aim * command.amount), aim)
                Direction.DOWN -> PositionAim(h, depth, aim + command.amount)
                Direction.UP -> PositionAim(h, depth, aim - command.amount)
            }
        }

        override fun product(): Int = h * depth
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
