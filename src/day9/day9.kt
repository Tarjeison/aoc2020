package day9

import java.io.File

const val PREAMBLE_SIZE = 25
const val invalidNumberFromPart1 = 90433990L

fun readInput() = File("src/day9/input.txt").readLines().map { it.toLong() }

// Part 1
fun isASumOfPreamble(number: Long, preamble: List<Long>): Boolean {
    val sortedPreamble = preamble.sorted()
    preamble.forEachIndexed { firstIndex, firstLong ->
        preamble.forEachIndexed { secondIndex, secondLong ->
            if (firstIndex != secondIndex) {
                if ((firstLong + secondLong) == number) return true
            }
        }
    }
    return false
}

// Part 1
fun findWrongNumber() {
    val input = readInput()
    val preamble = input.take(PREAMBLE_SIZE).toMutableList()
    val remainingInput = input.drop(PREAMBLE_SIZE)
    remainingInput.forEach {
        if (isASumOfPreamble(it, preamble)) {
            preamble.removeAt(0)
            preamble.add(it)
        } else {
            println(it)
            return
        }
    }
}


fun findSetThatSumsToInvalidNumber(preamble: List<Long>, invalidNumber: Long = invalidNumberFromPart1): Long {
    var sum = 0L
    preamble.forEach {
        sum += it
        if (sum == invalidNumber) {
            val range = preamble.subList(0, preamble.indexOf(it))
            return range.min()!! + range.max()!!
        }
        if (sum > invalidNumber) return -1
    }
    return -1
}

fun main() {
    val input = readInput().toMutableList()
    var solution = -1L
    while (solution == -1L) {
        solution = findSetThatSumsToInvalidNumber(input)
        input.removeAt(0)
    }
    print(solution)
}