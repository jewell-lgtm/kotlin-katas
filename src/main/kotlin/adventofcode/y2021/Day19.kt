package adventofcode.y2021

import kotlin.reflect.KProperty1

fun main() {
    println("Example 1a answer: ${Day19(exampleInput1a).part1()}")
//    println("Part 1 answer: ${Day19(puzzleInput).part1()}")
}

private class Day19(input: List<String>) {
    val scanners = parseScanners(input)
    fun part1(): Int {
        println(scanners)
        val a = scanners[0]
        val b = scanners[1]
        val c = scanners[1].plus(Vector2D::x, -1)
        println(a.errBy(Vector2D::x, b))
        println(a.errBy(Vector2D::x, c))
        println(a.errBy(Vector2D::x, c.plus(Vector2D::x, -1)))
        return -1
    }


    private fun parseScanners(input: List<String>): List<Reading> {
        val result = mutableListOf<Reading>()
        val take = mutableListOf<String>()
        for (line in input) {
            if (line.startsWith("---")) {
                if (take.isNotEmpty()) {
                    result.add(Reading(take.fold(mutableListOf()) { acc, s ->
                        acc.also {
                            it.add(
                                Vector2D.from(
                                    s
                                )
                            )
                        }
                    }))
                    take.clear()
                }
            } else if (line.isNotEmpty()) {
                take.add(line)
            }
        }

        result.add(Reading(take.fold(mutableListOf()) { acc, s ->
            acc.also {
                it.add(
                    Vector2D.from(
                        s
                    )
                )
            }
        }))


        return result
    }

    data class Vector2D(val x: Int, val y: Int) {
        companion object {
            fun from(i: String): Vector2D {
                val input = i.trim().split(",").map { it.toInt() }
                return Vector2D(input[0], input[1])
            }
        }

        override fun toString(): String {
            return "$x,$y"
        }

        fun plus(dir: KProperty1<Vector2D, Int>, amount: Int):Vector2D =
            if (dir == Vector2D::x) Vector2D(x + amount, y) else Vector2D(x, y + amount)
    }

    data class Reading(val lines: List<Vector2D>) {
        fun errBy(
            prop: KProperty1<Vector2D, Int>, other: Reading
        ) = lines.sumOf { first -> other.lines.sumOf { second -> prop.get(first) - prop.get(second) }  }

        fun plus(dir: KProperty1<Vector2D, Int>, amount: Int): Reading {
            return Reading(lines.map { it.plus(dir, amount) })
        }
    }
}

private val puzzleInput = getInput("Day19")
private val exampleInput1a = """
    --- scanner 0 ---
    0,2
    4,1
    3,3

    --- scanner 1 ---
    -1,-1
    -5,0
    -2,1
""".trimIndent().split("\n")