package adventofcode.y2021

fun main() {
    val day = Day17()

//    println(Day17.Vector(7, 2).trajectory(Day17.Target(x = 20..30, y = -10..-5)))
//    println(Day17.Vector(6, 3).trajectory(Day17.Target(x = 20..30, y = -10..-5)))
//    println(Day17.Vector(9, 0).trajectory(Day17.Target(x = 20..30, y = -10..-5)))
//    println(null == Day17.Vector(17, -4).trajectory(Day17.Target(x = 20..30, y = -10..-5)))
    println("Example 1 answer: ${day.example1()}")
//    println("Part 1 answer: ${day.puzzle1()}")
//    println("Example 2 answer: ${day.example2()}")
//    println("Part 2 answer: ${day.puzzle2()}")


}

private class Day17 {
    fun example1() = findBestY(Target(x = 20..30, y = -10..-5))
    fun puzzle1() = puzzleInput.size
    fun example2() = exampleInput.size
    fun puzzle2() = puzzleInput.size

    fun v(x:Int,y:Int)=Vector(x, y)


    private fun findBestY(target: Target): Int {
        var bestY = 0
        for (x in 1..target.x.first) {
            var lastY = 0
            for (y in 1..100_000) {
                val hit = Vector(x,y).trajectory(target)
                if (hit != null) {
                    val thisY = hit.maxOf { it.y }
                    bestY = maxOf(bestY, thisY)
                    if (thisY < lastY) break
                    lastY = thisY
                }
            }
        }
        return bestY
    }


    data class Target(val x: IntRange, val y: IntRange)


    data class Vector(val x: Int, val y: Int) {
        operator fun plus(that: Vector): Vector =
            Vector(this.x + that.x, this.y + that.y)

        fun trajectory(target: Target): List<Vector>? {
            var vel = this
            var pos = Vector(0, 0)
            val trajectory = mutableListOf(pos)
            while (pos.x <= target.x.last && pos.y >= target.y.last) {
                pos += vel
                trajectory.add(pos)
                vel = Vector(maxOf(0, vel.x - 1), vel.y - 1)
                if (pos.inside(target)) {

                    return trajectory
                }
            }
            return null
        }

        fun inside(target: Target): Boolean = target.x.contains(this.x) && target.y.contains(this.y)

    }

}


private val puzzleInput = getInput("Day01")
private val exampleInput = """
""".trimIndent().split("\n")