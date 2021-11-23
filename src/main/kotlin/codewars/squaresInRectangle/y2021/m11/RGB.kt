package codewars.squaresInRectangle.y2021.m11

import assertEquals

/*
The rgb function is incomplete. Complete it so that passing in RGB decimal values will result in a hexadecimal representation being returned. Valid decimal values for RGB are 0 - 255. Any values that fall out of that range must be rounded to the closest valid value.

Note: Your answer should always be 6 characters long, the shorthand with 3 will not work here.

The following are examples of expected output values:

rgb(255, 255, 255) // returns FFFFFF
rgb(255, 255, 300) // returns FFFFFF
rgb(0, 0, 0) // returns 000000
rgb(148, 0, 211) // returns 9400D3

 */

fun main() {
    println(assertEquals("000000", rgb(0, 0, 0)))
    println(assertEquals("000000", rgb(0, 0, -20)))
    println(assertEquals("FFFFFF", rgb(300, 255, 255)))
    println(assertEquals("ADFF2F", rgb(173, 255, 47)))
    println(assertEquals("9400D3", rgb(148, 0, 211)))
}

fun rgb(r: Int, g: Int, b: Int): String =
    listOf(r, g, b).joinToString("") {
        it.coerceIn(0..255).toString(16).padStart(2, '0')
    }.uppercase()