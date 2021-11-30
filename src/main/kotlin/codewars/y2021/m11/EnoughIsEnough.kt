package codewars.y2021.m11

import assertEquals

/*
https://www.codewars.com/kata/554ca54ffa7d91b236000023/train/kotlin

Enough is enough!
Alice and Bob were on a holiday. Both of them took many pictures of the places they've been, and now they want to show Charlie their entire collection. However, Charlie doesn't like these sessions, since the motive usually repeats. He isn't fond of seeing the Eiffel tower 40 times. He tells them that he will only sit during the session if they show the same motive at most N times. Luckily, Alice and Bob are able to encode the motive as a number. Can you help them to remove numbers such that their list contains each number only up to N times, without changing the order?

Task
Given a list lst and a number N, create a new list that contains each number of lst at most N times without reordering. For example if N = 2, and the input is [1,2,3,1,2,1,2,3], you take [1,2,3,1,2], drop the next [1,2] since this would lead to 1 and 2 being in the result 3 times, and then take 3, which leads to [1,2,3,1,2,3].

Example
  delete_nth ([1,1,1,1],2) # return [1,1]

  delete_nth ([20,37,20,21],1) # return [20,37,21]

 */
fun main() {
    println(
        assertEquals(
            intArrayOf(20, 37, 21).toList(),
            EnoughIsEnough.deleteNth(intArrayOf(20, 37, 20, 21), 1).toList()
        )
    )
    println(
        assertEquals(
            intArrayOf(1, 1, 3, 3, 7, 2, 2, 2).toList(),
            EnoughIsEnough.deleteNth(intArrayOf(1, 1, 3, 3, 7, 2, 2, 2, 2), 3).toList()
        )
    )
    println(
        assertEquals(
            intArrayOf(1, 2, 3, 1, 1, 2, 2, 3, 3, 4, 5).toList(),
            EnoughIsEnough.deleteNth(intArrayOf(1, 2, 3, 1, 1, 2, 1, 2, 3, 3, 2, 4, 5, 3, 1), 3).toList()
        )
    )
    println(
        assertEquals(
            intArrayOf(1, 1, 1, 1, 1).toList(),
            EnoughIsEnough.deleteNth(intArrayOf(1, 1, 1, 1, 1), 5).toList()
        )
    )
    println(
        assertEquals(
            intArrayOf().toList(),
            EnoughIsEnough.deleteNth(intArrayOf(), 5).toList()
        )
    )
}

object EnoughIsEnough {
    fun deleteNth(elements: IntArray, maxOcurrences: Int): IntArray {
        val seen = mutableMapOf<Int, Int>()
        return elements.filter {
            val count = seen.getOrDefault(it, 0)
            seen[it] = count + 1
            count < maxOcurrences
        }.toIntArray()

        // recursive algorithm not performant enough :(
//        tailrec fun deleteNth(seen: Map<Int, Int>, result: List<Int>, toProcess: List<Int>): List<Int> {
//            if (toProcess.isEmpty()) return result
//            val head = toProcess[0]
//            val count = seen.getOrDefault(head, 0)
//
//            val newResult = if (count < maxOcurrences) result + head else result
//            val tail = toProcess.drop(1)
//
//            return deleteNth(seen + mapOf(head to count + 1), newResult, tail)
//        }
//
//
//
//        return deleteNth(mapOf(), listOf(), elements.toList()).toIntArray()
    }
}