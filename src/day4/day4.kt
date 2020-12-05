package day4

import java.io.File

data class Passport(
        val byr: String?,
        val cid: String?,
        val ecl: String?,
        val eyr: String?,
        val hcl: String?,
        val hgt: String?,
        val iyr: String?,
        val pid: String?
) {
    constructor(map: Map<String, String>) : this(
            byr = map["byr"],
            cid = map["cid"],
            ecl = map["ecl"],
            eyr = map["eyr"],
            hcl = map["hcl"],
            hgt = map["hgt"],
            iyr = map["iyr"],
            pid = map["pid"]
    )

    fun isExtraValid(): Boolean {
        return isByrValid() && isIyrValid() && isEyrValid() && isPidValid() && isHgtValid() && isEclValid() && isHclValid()
    }

    fun isValid(): Boolean {
        return !byr.isNullOrEmpty() && !ecl.isNullOrEmpty() && !eyr.isNullOrEmpty() && !pid.isNullOrEmpty()
                && !hcl.isNullOrEmpty() && !hgt.isNullOrEmpty() && !iyr.isNullOrEmpty()
    }

    private fun isIyrValid(): Boolean {
        return iyr?.toInt() in 2010..2020
    }

    private fun isEyrValid(): Boolean {
        return eyr?.toInt() in 2020..2030
    }

    private fun isByrValid(): Boolean {
        return byr?.toInt() in 1920..2002
    }

    private fun isPidValid(): Boolean {
        if (pid?.length != 9) return false
        if (pid.toIntOrNull() == null) return false
        return true
    }

    private fun isHgtValid(): Boolean {
        return when (hgt?.takeLast(2)) {
            "cm" -> {
                if (hgt.length != 5) {
                    false
                } else {
                    hgt.take(3).toInt() in 150..193
                }
            }
            "in" ->  {
                if (hgt.length != 4) {
                    false
                } else {
                    hgt.take(2).toInt() in 59..76
                }
            }
            else -> return false
        }
    }

    private fun isEclValid(): Boolean {
        val validEclValues = "amb blu brn gry grn hzl oth".split(" ")
        return ecl in validEclValues
    }

    private fun isHclValid(): Boolean {
        if (hcl?.get(0) != '#') return false
        if (hcl.length != 7) return false
        val regex = Regex("^[a-f0-9]*$")
        return regex.containsMatchIn(hcl.takeLast(6))
    }
}

fun parseInput(): MutableList<Passport> {
    val inputLines = File("src/day4/input.txt").readLines()
    val passports = mutableListOf<Passport>()
    val mapList = linkedMapOf<String, String>()
    inputLines.forEach { line ->
        if (line.isBlank()) {
            passports.add(Passport(mapList))
            mapList.clear()
        } else {
            val lineAsMap = line.split(" ").associate { passwordString ->
                val (key, value) = passwordString.split(":")
                key to value
            }
            mapList.putAll(lineAsMap)
        }
    }
    // Add last passport
    passports.add(Passport(mapList))
    return passports
}

fun main() {
    val passports = parseInput()
    var nValidPassports = 0
    passports.forEach {
        println("Checking: $it")
        if (it.isExtraValid()) {
            nValidPassports++
        }
    }
    print(nValidPassports)
}