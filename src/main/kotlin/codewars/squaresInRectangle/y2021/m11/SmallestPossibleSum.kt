package codewars.squaresInRectangle.y2021.m11

import assertEquals

/*
https://www.codewars.com/kata/52f677797c461daaf7000740/kotlin

Description
Given an array X of positive integers, its elements are to be transformed by running the following operation on them as many times as required:

if X[i] > X[j] then X[i] = X[i] - X[j]

When no more transformations are possible, return its sum ("smallest possible sum").

For instance, the successive transformation of the elements of input X = [6, 9, 21] is detailed below:

X_1 = [6, 9, 12] # -> X_1[2] = X[2] - X[1] = 21 - 9
X_2 = [6, 9, 6]  # -> X_2[2] = X_1[2] - X_1[0] = 12 - 6
X_3 = [6, 3, 6]  # -> X_3[1] = X_2[1] - X_2[0] = 9 - 6
X_4 = [6, 3, 3]  # -> X_4[2] = X_3[2] - X_3[1] = 6 - 3
X_5 = [3, 3, 3]  # -> X_5[1] = X_4[0] - X_4[1] = 6 - 3
The returning output is the sum of the final transformation (here 9).

Example
solution([6, 9, 21]) #-> 9
Solution steps:
[6, 9, 12] #-> X[2] = 21 - 9
[6, 9, 6] #-> X[2] = 12 - 6
[6, 3, 6] #-> X[1] = 9 - 6
[6, 3, 3] #-> X[2] = 6 - 3
[3, 3, 3] #-> X[1] = 6 - 3

 */

fun main() {
    println(assertEquals(9, solution(longArrayOf(6, 9, 21))))
    println(assertEquals(3, solution(longArrayOf(1, 21, 55))))
    println(assertEquals(5, solution(longArrayOf(3, 13, 23, 7, 83))))
    println(assertEquals(923, solution(longArrayOf(71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71))))
    println(assertEquals(22, solution(longArrayOf(11, 22))))
    println(assertEquals(2, solution(longArrayOf(5, 17))))
    println(assertEquals(12, solution(longArrayOf(4, 16, 24))))
    println(assertEquals(9, solution(longArrayOf(9))))
}

// works, but too slow
fun solution(numbers: LongArray): Long {
    var result = numbers.sortedDescending()

    var head = result[0]
    var next = result.drop(1).firstOrNull { it < head }

    while (next != null) {
        val new = head - next
        val insertAt = result.drop(1).indexOfFirst { it < new }
            .let { if (it == -1) result.size - 1 else it }


        result = result.subList(1, insertAt + 1) + new + result.subList(insertAt + 1, result.size)

        head = result[0]
        next = result.drop(1).firstOrNull { it < head }
    }

    return result.sum()
}



