package day1

import java.io.File

fun main() {
    val input = File("src/day1/input.txt").readLines().map { it.toInt() }
    // val result = findTwoEntriesSummingTo(input, 2020)
    val result = findThreeEntriesSummingTo(input, 2020)
    print(result)
}

fun findTwoEntriesSummingTo(entries: List<Int>, sumCondition: Int): Int? {
    entries.forEachIndexed { firstIndex, i ->
        entries.forEachIndexed { secondIndex, j ->
            val sum = if (firstIndex != secondIndex) i + j else null
            if (sum == sumCondition) return i * j
        }
    }
    return null
}

fun findThreeEntriesSummingTo(entries: List<Int>, sumCondition: Int): Int? {
    entries.forEachIndexed { firstIndex, i ->
        entries.forEachIndexed { secondIndex, j ->
            entries.forEachIndexed { thirdIndex, k ->
                val sum = if (firstIndex != secondIndex && firstIndex != thirdIndex && secondIndex != thirdIndex) {
                    i + j + k
                } else {
                    null
                }
                if (sum == sumCondition) return i * j * k
            }
        }
    }
    return null
}
