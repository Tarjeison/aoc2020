package day8

import java.io.File
import java.io.InvalidObjectException
import java.lang.IndexOutOfBoundsException

enum class Operation {
    NOP, ACC, JMP
}

data class Instruction(var operation: Operation, val argument: Int, var visited: Boolean = false)

fun readInput(): List<Instruction> {
    return File("src/day8/input.txt").readLines().map {
        val split = it.split(" ")
        val argument = if (split.last()[0] == '+') {
            split.last().drop(1).toInt()
        } else {
            split.last().drop(1).toInt() * (-1)
        }
        Instruction(Operation.valueOf(split.first().toUpperCase()), argument)
    }
}

fun findAccumulatorAtInfiniteLoop(instructions: List<Instruction>): Pair<Int, Boolean> {
    var accumulator: Int = 0
    var position = 0
    while (true) {
        if (position == instructions.size) return Pair(accumulator, true)
        val instruction = instructions[position]
        if (instructions[position].visited) return Pair(accumulator, false) else instructions[position].visited = true

        when (instructions[position].operation) {
            Operation.NOP -> position++
            Operation.ACC -> {
                position++
                accumulator += instruction.argument
            }
            Operation.JMP -> {
                position += instructions[position].argument
            }
        }
    }
}

fun resetVisited(instructions: List<Instruction>) {
    instructions.forEach { it.apply { visited = false } }
}

fun changeJmpNopUntilProgramRuns(instructions: List<Instruction>): Int {
    instructions.filter { it.operation in listOf(Operation.JMP, Operation.NOP) }.forEach {
        val originalInstruction = it.copy()
        when (originalInstruction.operation) {
            Operation.NOP -> it.operation = Operation.JMP
            Operation.ACC -> throw InvalidObjectException("Can be ACC after filter")
            Operation.JMP -> it.operation = Operation.NOP
        }
        val result = findAccumulatorAtInfiniteLoop(instructions)
        if (result.second) return result.first
        it.operation = originalInstruction.operation
        resetVisited(instructions)
    }
    return -1
}

fun main() {
    val instructions = readInput()
    val accumulatorWhenFirstLoop = changeJmpNopUntilProgramRuns(instructions)
    print(accumulatorWhenFirstLoop)

}