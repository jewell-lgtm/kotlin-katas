package adventofcode.y2020.d15

import assertEquals
import java.io.File

fun main() {
    val exampleInput1 = parseInput(exampleInput)
    println(assertEquals(listOf(4, 55, 12), invalidFields(exampleInput1.fields, exampleInput1.tickets)))

    val puzzle = parseInput(puzzleInput)
    println("The answer to part one is: ${invalidFields(puzzle.fields, puzzle.tickets).sum()}")

    val validTickets = puzzle.tickets.filter { it.isValid(puzzle.fields) }
    println("${validTickets.size} of ${puzzle.tickets.size} tickets are valid")

    val map = solveFields(validTickets, puzzle.fields)
    val dep = map.filter { it.value.name.startsWith("departure") }.keys
    println(assertEquals(6, dep.size))

    val myDeparts = puzzle.myTicket.fieldsAt(dep)

    println("myDeps $myDeparts")

    // gets to here but the answer is wrong
    println("The answer to part 2 is ${myDeparts.product()}")

}

private fun  List<Int>.product(): Int = foldRight(1) { i, acc -> acc * i }


fun solveFields(tickets: List<NearbyTicket>, fields: Set<Field>): Map<Int, Field> {
    val possibleAtPos = (0 until tickets.first().list.size).associateWith { position ->
        fields.filter { field ->

            field.isValid(tickets.map { it.list[position] })
        }.toMutableSet()
    }.toMutableMap()

    val result = mutableMapOf<Int, Field>()
    while (possibleAtPos.isNotEmpty()) {
        val hasOne = possibleAtPos.filter { it.value.size == 1 }
        hasOne.forEach {
            possibleAtPos.remove(it.key)
            val field = it.value.first()
            result[it.key] = field
            possibleAtPos.forEach { possibilities ->
                possibilities.value.remove(field)
            }
        }
    }
    return result
}


fun validAtPos(pos: Int, tickets: List<NearbyTicket>, fields: Set<Field>): Set<Field> {
    return fields.filter { field -> field.isValid(tickets.map { ticket -> ticket.list[pos] }) }.toSet()
}

fun invalidFields(fields: Set<Field>, tickets: List<NearbyTicket>): List<Int> {
    val allValues = tickets.flatMap { it.list }

    return allValues.filter { value -> fields.none { field -> field.isValid(value) } }
}


data class Field(val name: String, val rangeA: IntRange, val rangeB: IntRange) {
    companion object {
        private val regex = """^(.+): (\d+)-(\d+) or (\d+)-(\d+)""".toRegex()
        fun maybeCreate(str: String): Field? =
            regex.find(str)?.destructured?.let { (name, startA, endA, startB, endB) ->
                Field(name, startA.toInt()..endA.toInt(), startB.toInt()..endB.toInt())
            }
    }

    fun isValid(i: Int): Boolean = rangeA.contains(i) or rangeB.contains(i)
    fun isValid(list: List<Int>): Boolean = list.all { this.isValid(it) }
}

data class MyTicket(val list: List<Int>) {
    fun fieldsAt(dep: Set<Int>): List<Int> =
        dep.map { list[it] }


    constructor(str: String) : this(str.split(",").map { it.toInt() })
}

data class NearbyTicket(val list: List<Int>) {
    fun isValid(fields: Set<Field>) = list.all { item -> fields.any { field -> field.isValid(item) } }

    constructor(str: String) : this(str.split(",").map { it.toInt() })
}

data class PuzzleInput(val fields: Set<Field>, val myTicket: MyTicket, val tickets: List<NearbyTicket>)

fun parseInput(input: List<String>): PuzzleInput {
    val fields = input.mapNotNull { Field.maybeCreate(it) }.toSet()

    val myTicketInput = input[input.indexOf("your ticket:") + 1]
    val myTicket = MyTicket(myTicketInput)

    val ticketsInput = input.drop(input.indexOf("nearby tickets:") + 1)
    val tickets = ticketsInput.map { NearbyTicket(it) }

    return PuzzleInput(fields, myTicket, tickets)
}



val exampleInput = """
    class: 1-3 or 5-7
    row: 6-11 or 33-44
    seat: 13-40 or 45-50

    your ticket:
    7,1,14

    nearby tickets:
    7,3,47
    40,4,50
    55,2,20
    38,6,12
""".trimIndent().split("\n")

val exampleInputPart2 = """
    class: 0-1 or 4-19
    row: 0-5 or 8-19
    seat: 0-13 or 16-19

    your ticket:
    11,12,13

    nearby tickets:
    3,9,18
    15,1,5
    5,14,9
""".trimIndent().split("\n")

val puzzleInput = File("aoc-input/day16.txt").readLines()
