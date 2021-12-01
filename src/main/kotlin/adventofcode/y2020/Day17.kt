package adventofcode.y2020

fun main() {
    val day2 = Day17()
    println("Example part one: ${day2.examplePart1()} cubes")
}

class Day17 {
    fun examplePart1(): Int {
        val world = World.fromInput(exampleInput)
        return world.afterCycles(6).activeCubes.size
    }

    data class Cube(val x: Int, val y: Int, val z: Int) {
        fun willBeActive(otherCubes: Set<Cube>): Boolean {
            val wasActive = otherCubes.contains(this)
            val alive = neighbors().count { it != this && otherCubes.contains(it) }


            return if (wasActive) {
                alive == 2 || alive == 3
            } else {
                alive == 3
            }
        }


        // includes the cube itself
        fun neighbors(): Set<Cube> =
            (-1..1).flatMap { dX ->
                (-1..1).flatMap { dY ->
                    (-1..1).map { dZ ->
                        Cube(this.x + dX, this.y + dY, this.z + dZ)
                    }
                }
            }.toSet()
    }

    class World(val activeCubes: Set<Cube>) {
        companion object {
            fun fromInput(input: List<String>): World {
                return World(cubesFromStrings(input))
            }

            private fun cubesFromStrings(input: List<String>) =
                input.flatMapIndexed { indexY: Int, str: String ->

                    str.mapIndexedNotNull { indexX, c ->
                        if (c == '#') Cube(indexX, indexY, 0) else null
                    }
                }.toSet()
        }

        fun afterCycles(i: Int): World {
            println("activeCubes.groupBy { it.third }  ${activeCubes.groupBy { it.z }}")
            val nextActive = activeCubes.flatMap { activeCube ->
                activeCube.neighbors().mapNotNull { cube -> cube.takeIf { it.willBeActive(activeCubes) } }
            }.toSet()

            

            return World(emptySet())
        }
    }
}


private val exampleInput = """
    .#.
    ..#
    ###
""".trimIndent().split("\n")