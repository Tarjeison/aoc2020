package day5

import java.io.File
import java.util.*

const val HIGHER_HALF_ROW = 'B'
const val LOWER_HALF_ROW = 'F'

const val HIGHER_HALF_COLUMN = 'R'
const val LOWER_HALF_COLUMN = 'L'

private val rowRange = 0..127
private val columnRange = 0..7


fun getPositionInRange(binaryInput: String, startRange: IntRange, upperMatch: Char, lowerMatch: Char): Int {
    var range = startRange
    binaryInput.forEach {
        range = when (it) {
            upperMatch -> {
                range.first + (range.last - range.first)/2..range.last
            }
            lowerMatch -> {
                range.first..range.first + (range.last - range.first)/2
            }
            else -> throw InputMismatchException ("Input not B or F")
        }
    }
    return range.last
}

fun main () {
    val boardingPasses = File("src/day5/input.txt").readLines()
    val seatIds = boardingPasses.map { boardingPass ->
        val row = getPositionInRange(boardingPass.take(7), rowRange, HIGHER_HALF_ROW, LOWER_HALF_ROW)
        val column = getPositionInRange(boardingPass.takeLast(3), columnRange, HIGHER_HALF_COLUMN, LOWER_HALF_COLUMN)
        row*8 + column
    }.sorted()

    seatIds.forEachIndexed { index, i ->
        when (index) {
            0 -> {
                if (seatIds[index + 1] != i+1) println("Free seat: ${i+1}")
            }
            seatIds.size - 1 -> {
                if (seatIds[index - 1] != i-1) println("Free seat: ${i-1}")
            }
            else -> {
                if (seatIds[index + 1] != i+1) println("Free seat: ${i+1}")
                if (seatIds[index - 1] != i-1) println("Free seat: ${i-1}")
            }
        }
    }
}

fun part1(boardingPasses: List<String>) {
    var highestSeatId = 0
    boardingPasses.forEach { boardingPass ->
        val row = getPositionInRange(boardingPass.take(7), rowRange, HIGHER_HALF_ROW, LOWER_HALF_ROW)
        val column = getPositionInRange(boardingPass.takeLast(3), columnRange, HIGHER_HALF_COLUMN, LOWER_HALF_COLUMN)
        val seatId = row*8 + column
        if (seatId > highestSeatId) highestSeatId = seatId
    }
    print(highestSeatId)
}