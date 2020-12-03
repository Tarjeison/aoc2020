package day3

import java.io.File

const val TREE = '#'

val input = File("src/day3/input.txt").readLines()
val inputLength = input[0].length

data class Slope(val traverseX: Int, val traverseY: Int)


fun main() {
    val slopes = listOf(
            Slope(1, 1),
            Slope(3, 1),
            Slope(5, 1),
            Slope(7, 1),
            Slope(1, 2)
    )
    var treesMultiple = 1L
    slopes.forEach {
        treesMultiple *= checkSlope(it)
    }
    print(treesMultiple)
}

fun checkSlope(s: Slope): Int {
    var bottomReached = false
    var x = 0
    var y = 0
    var nTrees = 0

    while (!bottomReached) {
        if (input[y][x] == TREE) nTrees++
        if ((y + s.traverseY) >= input.size) {
            bottomReached = true
        } else {
            y += s.traverseY
            x += s.traverseX
            if (x >= inputLength) {
                x -= inputLength
            }
        }
    }
    return nTrees
}
