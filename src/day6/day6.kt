package day6

import java.io.File

data class Group(val answers: Map<Char, Int>, val nPersonsInGroup: Int)

fun readInput(): MutableList<Group> {
    val groups = mutableListOf<Group>()
    val group = mutableMapOf<Char, Int>()
    var nPersonsInGroup = 0
    File("src/day6/input.txt").readLines().forEach { line ->
        if (line == "") {
            groups.add(Group(group.toMap(), nPersonsInGroup))
            group.clear()
            nPersonsInGroup=0
        } else {
            nPersonsInGroup++
            line.forEach { answer ->
                if (group[answer] == null) group[answer] = 1
                else group[answer] = group[answer]!!.plus(1)
            }
        }
    }
    groups.add(Group(group, nPersonsInGroup))
    return groups
}

fun main() {
    val groups = readInput()
    var answers = 0
    groups.forEach { group ->
        answers += group.answers.filter { it.value == group.nPersonsInGroup }.count()
    }
    print(answers)
}