package adventofcode.y2021

import java.io.File

fun getInput(name: String) = File("src/main/kotlin/adventofcode/y2021/input", "$name.txt").readLines().map { it.trim() }

fun getIntInput(name: String) = getInput(name).map { it.toInt() }