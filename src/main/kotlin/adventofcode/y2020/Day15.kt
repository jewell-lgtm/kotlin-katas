package adventofcode.y2020.d15

import assertEquals

fun main() {

    val day1example = listOf(0, 3, 6)
//    println(assertEquals(day1(day1example, 1), 0))
//    println(assertEquals(day1(day1example, 2), 3))
//    println(assertEquals(day1(day1example, 3), 6))
//    println(assertEquals(day1(day1example, 4), 0))
//    println(assertEquals(day1(day1example, 5), 3))
//    println(assertEquals(day1(day1example, 6), 3))
//    println(assertEquals(day1(day1example, 7), 1))
//    println(assertEquals(day1(day1example, 8), 0))
//    println(assertEquals(day1(day1example, 9), 4))
    println(assertEquals(day1(day1example, 10), 0))

    listOf(
        listOf(1,3,2) to 1,
        listOf(2,1,3) to 10,
        listOf(1,2,3) to 27,
        listOf(3,1,2) to 1836,
    ).forEach { (a, b) -> println(assertEquals(day1(a, 2020), b)) }


    val startingNumbers = listOf(20, 9, 11, 0, 1, 2)
    println("The answer for day 1 is ${day1(startingNumbers, 2020)}")


    listOf(
        listOf(0,3,6) to 175594,
    ).forEach { (a, b) -> println(assertEquals(day1(a, 30000000), b)) }


    println("The answer for day 2 is ${day1(startingNumbers, 30000000)}")
}



fun day1(day1Input: List<Int>, tilTurn: Int): Int {
    var turn = 1
    var lastSaid = -2
    val saidMultiple: MutableMap<Int, Int> = mutableMapOf()
    val said: MutableMap<Int, Int> = mutableMapOf()
    fun say(i: Int, turn: Int): Unit {
        if (said.containsKey(i)) {
            saidMultiple[i] = said[i]!!
        }
        said[i] = turn

        lastSaid = i
    }

    while (turn <= tilTurn) {
        if (turn <= day1Input.size) {
            say(day1Input[turn - 1], turn)
        } else if (!saidMultiple.containsKey(lastSaid)) {
            say(0, turn)
        } else {
            val lastSpoken = said[lastSaid]!!
            val spokenBefore = saidMultiple[lastSaid]!!
            say(lastSpoken - spokenBefore, turn)
        }

        turn += 1
    }

    return lastSaid
}

