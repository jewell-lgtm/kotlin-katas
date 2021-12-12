package adventofcode.y2021

fun main() {
    println("Example 1 answer: ${Day12(exampleInput).countPaths()}")
    println("Example 1a answer: ${Day12(exampleInput2).countPaths()}")
    println("Example 1b answer: ${Day12(exampleInput3).countPaths()}")
    println("Part 1 answer: ${Day12(puzzleInput).countPaths()}")
    println("Example 2 answer: ${Day12(exampleInput, 2).countPaths()}")
    println("Example 2a answer: ${Day12(exampleInput2, 2).countPaths()}")
    println("Example 2b answer: ${Day12(exampleInput3, 2).countPaths()}")
    println("Part 2 answer: ${Day12(puzzleInput, 2).countPaths()}")
}

private class Day12(input: List<String>, val smallCaveMax: Int = 1) {
    val nodes: Map<String, Node> = parseInput(input)
    val start = nodes["start"]!!
    val end = nodes["end"]!!

    fun countPaths() = countPathsUntil(start, end, 0, mapOf(start to 1), listOf(start))

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

    fun countPathsUntil(from: Node, to: Node, i: Int, visited: Map<Node, Int>, path: List<Node>): Int {
        if (from == to) return i + 1
        var result = i
        from.connections.forEach { connection ->
            if (canVisit(visited, connection)) {
                val newVisited = visited.toMutableMap()
                newVisited[connection] = (visited[connection] ?: 0) + 1
                val newPath = path + connection
                result = countPathsUntil(connection, to, result, newVisited, newPath)
            }
        }

        return result
    }

    private fun canVisit(
        visited: Map<Node, Int>,
        node: Node
    ): Boolean {
        if (node.isSmall) {
            if (node.isStart) return false
            if (node.isEnd) return !visited.containsKey(node)
            val visitCount = visited.getOrDefault(node, 0)
            val smallVisited = visited.filter { !it.key.isStart && !it.key.isEnd && it.key.isSmall }
            val mostVisitedSmall =
                smallVisited.maxByOrNull { it.value }?.value
                    ?: 0
            return mostVisitedSmall < smallCaveMax || visitCount == 0
        }
        return true
    }

    class Node(val name: String, val connections: MutableSet<Node>) {
        val isStart = name == "start"
        val isEnd = name == "end"
        val isSmall = name[0].isLowerCase()
        val isBig = !isSmall

        override fun toString(): String {
            return "$name"
//            return "$name -> [${connections.joinToString(", ") { it.name }}]"
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


private val exampleInput2 = """
    dc-end
    HN-start
    start-kj
    dc-start
    dc-HN
    LN-dc
    HN-end
    kj-sa
    kj-HN
    kj-dc
""".trimIndent().split("\n")

private val exampleInput3 = """
    fs-end
    he-DX
    fs-he
    start-DX
    pj-DX
    end-zg
    zg-sl
    zg-pj
    pj-he
    RW-he
    fs-DX
    pj-RW
    zg-RW
    start-pj
    he-WI
    zg-he
    pj-fs
    start-RW
""".trimIndent().split("\n")