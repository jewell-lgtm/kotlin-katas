package adventofcode.y2021


fun main() {
    val list = listOf("asd", "dsa", "sdf", "fds")
    val sideways = list.sideways()

    println("sideways ${sideways}")
}

private fun List<String>.sideways(): List<String> {
    val w = first().length
    return (0 until w).map { i -> map { it[i] }.joinToString("") }
}

