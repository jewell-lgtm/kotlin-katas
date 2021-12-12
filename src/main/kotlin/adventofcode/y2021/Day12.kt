package adventofcode.y2021

fun main() {
    val day = Day12("""
        start-A
        A-end
    """.trimIndent().split("\n"))

    println("count paths ${day.countPaths()}")


    println("Example 1 answer: ${Day12(exampleInput).example1()}")
    println("Part 1 answer: ${Day12(puzzleInput).puzzle1()}")
    println("Example 2 answer: ${Day12(exampleInput).example2()}")
    println("Part 2 answer: ${Day12(puzzleInput).puzzle2()}")


}

private class Day12(input: List<String>) {
    val nodes: Map<String, Node> = parseInput(input)
    val start = nodes["start"]!!
    val end = nodes["end"]!!

    fun example1(): Int = countPaths()
    fun puzzle1(): Int = -1
    fun example2(): Int = -1
    fun puzzle2(): Int = -1

    private fun parseInput(input: List<String>): Map<String, Node> {
        val nodes = mutableMapOf<String, Node>()
        input.forEach { line ->
            val (a, b) = line.split("-")
            val nodeA = if (nodes.containsKey(a)) nodes[a]!! else Node(a, mutableSetOf()).also { nodes[a] = it }
            val nodeB = if (nodes.containsKey(b)) nodes[b]!! else Node(b, mutableSetOf()).also { nodes[b] = it }

            nodeA.connections.add(nodeB)
            nodeB.connections.add(nodeA)
        }
        return nodes
    }

    fun pathsTo(node: Node, visited: Set<Node> = emptySet()): Int {
        val newVisited = visited + node
        if (node.isStart) return -1
        return node.connections.filter { visited.contains(it) }.sumOf { pathsTo(it, newVisited) }
    }

    fun countPaths(): Int {
        return countPathsUntil(start, end, 0)
    }

    private fun countPathsUntil(from: Day12.Node, to: Day12.Node, i: Int): Int {
        if (from == to) return i + 1
        var result = i
        from.connections.forEach { connection ->
            result = countPathsUntil(connection, to, result)
        }

        return result
    }

    class Node(val name: String, val connections: MutableSet<Node>) {
        val isStart = name == "start"
        val isEnd = name == "end"
        val isSmall = name[0].isLowerCase()

        override fun toString(): String {
            return "$name -> [${connections.joinToString(", ") { it.name }}]"
        }
    }
}

private val puzzleInput = getInput("Day12")
private val exampleInput = """
    start-A
    start-b
    A-c
    A-b
    b-d
    A-end
    b-end
""".trimIndent().split("\n")
