package day2

import java.io.File

data class PasswordInformation(val letter: Char,
                               val minOccurrence: Int,
                               val maxOccurrence: Int,
                               val password: String) {

    fun iAmAGoodPassword(): Boolean {
        val count = password.count { it == letter }
        return count in minOccurrence..maxOccurrence
    }

    fun iAmAGoodPasswordForrealThisTime(): Boolean {
        val firstCondition = password[minOccurrence - 1] == letter
        val secondCondition = password[maxOccurrence - 1] == letter
        return (firstCondition || secondCondition) && (firstCondition != secondCondition)
    }
    companion object {
        fun fromInput(input: String): PasswordInformation {
            return PasswordInformation(
                    letter = input[input.indexOf(" ") + 1],
                    minOccurrence = input.slice(0 until input.indexOf("-")).toInt(),
                    maxOccurrence = input.slice((input.indexOf("-") + 1) until input.indexOf(" ")).toInt(),
                    password = input.slice((input.indexOf(":") + 2) until input.length)
            )
        }
    }
}

fun main() {
    val input = File("src/day2/input.txt").readLines().map {
        PasswordInformation.fromInput(it)
    }
    var nValidPasswords = 0
    input.forEach {
        if (it.iAmAGoodPasswordForrealThisTime()) nValidPasswords++
    }
    print("Number of valid passwords: $nValidPasswords")
}