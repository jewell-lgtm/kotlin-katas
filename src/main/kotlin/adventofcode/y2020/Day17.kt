package adventofcode.y2020

import java.io.File

fun main() {
    val day2 = Day17()
    println("Example 1 answer: ${day2.examplePart1()} cubes")
    println("Part 1 answer: ${day2.part1()} cubes")
    println("Example 2 answer: ${day2.examplePart2()} cubes")
    println("Part 2 answer: ${day2.part2()} cubes")
}

class Day17 {
    fun examplePart1(): Int = afterCycles(cubes3DFromStrings(exampleInput), 6).size
    fun part1(): Int = afterCycles(cubes3DFromStrings(puzzleInput), 6).size
    fun examplePart2(): Int = afterCycles(cubes4DFromStrings(exampleInput), 6).size
    fun part2(): Int = afterCycles(cubes4DFromStrings(puzzleInput), 6).size

    abstract class Cube {
        fun willBeActive(otherCubes: Set<Cube>): Boolean {
            val wasActive = otherCubes.contains(this)
            val alive = neighbors().count { it != this && otherCubes.contains(it) }

            return if (wasActive) {
                alive == 2 || alive == 3
            } else {
                alive == 3
            }
        }

        abstract fun neighbors(): Set<Cube>
    }

    data class Cube3D(val x: Int, val y: Int, val z: Int) : Cube() {
        // includes the cube itself
        override fun neighbors(): Set<Cube> =
            (-1..1).flatMap { dX ->
                (-1..1).flatMap { dY ->
                    (-1..1).map { dZ ->
                        Cube3D(this.x + dX, this.y + dY, this.z + dZ)
                    }
                }
            }.toSet()
    }

    data class Cube4D(val x: Int, val y: Int, val z: Int, val w: Int) : Cube() {
        // includes the cube itself
        override fun neighbors(): Set<Cube> =
            (-1..1).flatMap { dX ->
                (-1..1).flatMap { dY ->
                    (-1..1).flatMap { dZ ->
                        (-1..1).map { dW ->
                            Cube4D(this.x + dX, this.y + dY, this.z + dZ, this.w + dW)
                        }
                    }
                }
            }.toSet()
    }

    fun cubes3DFromStrings(input: List<String>) =
        input.flatMapIndexed { indexY: Int, str: String ->
            str.mapIndexedNotNull { indexX, c ->
                if (c == '#') Cube3D(indexX, indexY, 0) else null
            }
        }.toSet()

    fun cubes4DFromStrings(input: List<String>) =
        input.flatMapIndexed { indexY: Int, str: String ->
            str.mapIndexedNotNull { indexX, c ->
                if (c == '#') Cube4D(indexX, indexY, 0, 0) else null
            }
        }.toSet()

    tailrec fun afterCycles(activeCubes: Set<Cube>, remainingCycles: Int): Set<Cube> {
        if (remainingCycles == 0) return activeCubes
        return afterCycles(activeCubes.nextSet(), remainingCycles - 1)
    }

    private fun Set<Cube>.nextSet() =
        flatMap { it.neighbors() }.toSet().filter { it.willBeActive(this) }.toSet()
}


private val exampleInput = """
    .#.
    ..#
    ###
""".trimIndent().split("\n")


private val puzzleInput = File("aoc-input/day17.txt").readLines()
