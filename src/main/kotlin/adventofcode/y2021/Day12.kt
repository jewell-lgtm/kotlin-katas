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

    fun countPaths() = countPathsUntil(0, end, start, mapOf(start to 1), listOf(start))

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

    fun countPathsUntil(countPaths: Int, to: Node, from: Node, visited: Map<Node, Int>, path: List<Node>): Int {
        if (from == to) return countPaths + 1
        var newCount = countPaths

        from.connections.forEach { connection ->
            if (canVisit(visited, connection)) {
                val newVisited = visited.toMutableMap()
                newVisited[connection] = (visited[connection] ?: 0) + 1
                val newPath = path + connection
                // println("path ${path}")
                newCount = countPathsUntil(newCount, to, connection, newVisited, newPath)
            }
        }

        return newCount
    }

    private fun canVisit(
        visited: Map<Node, Int>,
        node: Node
    ): Boolean {
        if (node.isSmall) {
            if (node == start) return false
            if (node == end) return !visited.containsKey(node)
            val visitCount = visited.getOrDefault(node, 0)
            val smallVisited = visited.filterKeys { it != start && it != end && it.isSmall }
            val mostVisitedSmall = smallVisited.maxByOrNull { it.value }?.value ?: 0
            return mostVisitedSmall < smallCaveMax || visitCount == 0
        }
        return true
    }

    class Node(val name: String, val connections: MutableSet<Node>) {
        val isSmall = name[0].isLowerCase()

        override fun toString(): String {
            return name
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