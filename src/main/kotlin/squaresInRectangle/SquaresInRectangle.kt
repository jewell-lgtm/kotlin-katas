package squaresInRectangle

import assertEquals

/**
    The drawing below gives an idea of how to cut a given "true" rectangle into squares ("true" rectangle meaning that the two dimensions are different).

    alternative text

    Can you translate this drawing into an algorithm?

    You will be given two dimensions

    a positive integer length
    a positive integer width
    You will return a collection or a string (depending on the language; Shell bash, PowerShell, Pascal and Fortran return a string) with the size of each of the squares.

    Examples in general form:
    (depending on the language)

    sqInRect(5, 3) should return [3, 2, 1, 1]
    sqInRect(3, 5) should return [3, 2, 1, 1]

    You can see examples for your language in **"SAMPLE TESTS".**

 */

fun main() {
    println(assertEquals(listOf(3, 2, 1, 1), sqInRect(5, 3)))
    println(assertEquals(null, sqInRect(5, 5)))
    println(assertEquals(listOf(14, 6, 6, 2, 2, 2), sqInRect(20, 14)))
}

fun sqInRect(lng: Int, wdth: Int): List<Int>? {
    if (lng == wdth) return null

    tailrec fun sqInRect(list: List<Int>, length: Int, width: Int): List<Int> {
        if (length == width) return list + width
        val smallest = length.coerceAtMost(width)
        val biggest = length.coerceAtLeast(width)

        return sqInRect(list + smallest, biggest - smallest, smallest)
    }

    return sqInRect(emptyList(), lng, wdth)
}