package adventofcode.y2020.d15

import assertEquals
import java.io.File

fun main() {

    val classField = Field("class", 1..3, 5..7)
    println(assertEquals(listOf(true, true, false, true), listOf(1, 3, 4, 5).map { classField.isValid(it) }))

    val exampleFields = listOf(
        Triple("class", 1..3, 5..7),
        Triple("row", 6..11, 33..44),
        Triple("seat", 13..40, 45..50),
    ).map { (a, b, c) -> Field(a, b, c) }

    val exampleTickets = listOf(
        listOf(7, 3, 47),
        listOf(40, 4, 50),
        listOf(55, 2, 20),
        listOf(38, 6, 12),
    ).map { NearbyTicket(it) }

    val exampleMyTicket = MyTicket(listOf(7, 1, 14))

    println(assertEquals(emptyList(), invalidFields(exampleFields, listOf(NearbyTicket(listOf(7, 3, 47))))))
    println(assertEquals(listOf(4, 55, 12), invalidFields(exampleFields, exampleTickets)))

    println(assertEquals(PuzzleInput(exampleFields, exampleMyTicket, exampleTickets), parseInput(exampleInput)))
    println(assertEquals(listOf(4, 55, 12), invalidFields(parseInput(exampleInput))))

    println("The answer to part one is: ${invalidFields(parseInput(input)).sum()}")


}

fun invalidFields(puzzle: PuzzleInput): List<Int> {
    return invalidFields(puzzle.fields, puzzle.tickets)
}

fun invalidFields(fields: List<Field>, tickets: List<NearbyTicket>): List<Int> {
    val allValues = tickets.flatMap { it.list }

    return allValues.filter { value -> fields.none { field -> field.isValid(value) } }
}


data class Field(val name: String, val rangeA: IntRange, val rangeB: IntRange) {
    companion object {
        private val regex = """^(\w+): (\d+)-(\d+) or (\d+)-(\d+)""".toRegex()
        fun maybeCreate(str: String): Field? =
            regex.find(str)?.destructured?.let { (name, startA, endA, startB, endB) ->
                Field(name, startA.toInt()..endA.toInt(), startB.toInt()..endB.toInt())
            }
    }

    fun isValid(i: Int): Boolean = rangeA.contains(i) || rangeB.contains(i)
}

data class MyTicket(val list: List<Int>) {
    constructor(str: String) : this(str.split(",").map { it.toInt() })
}

data class NearbyTicket(val list: List<Int>) {
    constructor(str: String) : this(str.split(",").map { it.toInt() })
}

data class PuzzleInput(val fields: List<Field>, val myTicket: MyTicket, val tickets: List<NearbyTicket>)

fun parseInput(input: List<String>): PuzzleInput {
    val fields = input.mapNotNull { Field.maybeCreate(it) }

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

val input = File(System.getProperty("user.dir")+"/aoc-input/day16.txt").readLines()
