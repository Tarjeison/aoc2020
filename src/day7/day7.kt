package day7

import java.io.File
import javax.naming.NameNotFoundException

data class Bag(val name: String, val canContain: List<Pair<Bag, Int>>? = null)

fun readInput(): MutableList<Bag> {
    val bags = mutableListOf<Bag>()
    File("src/day7/input.txt").readLines().forEach { bagInstruction ->
        val name = bagInstruction.split(" ").take(2).joinToString(" ")
        val instructions = bagInstruction.split(" ").drop(4).chunked(4)
        val canContain = if (instructions[0][0] == "no") {
            null
        } else {
            instructions.map {
                Pair(Bag(it[1] + " " + it[2]), it[0].toInt())
            }
        }
        bags.add(Bag(name, canContain))
    }
    return bags
}

// Part 1
fun howManyBagsCanContain(bagName: String, bags: List<Bag>): Int {
    var nBagsCanContain = 0
    bags.forEach { bag ->
        if (recursiveSearch(bag, bags, bagName)) {
            nBagsCanContain++
        }
    }
    return nBagsCanContain
}

// Part 1
fun recursiveSearch(bag: Bag, bags: List<Bag>, containsBagName: String): Boolean {
    if (bag.canContain?.any { it.first.name == containsBagName } == true) return true
    val childContainsBag = bag.canContain?.map { childBag ->
        val bagInList = bags.find { it.name == childBag.first.name }
                ?: throw NameNotFoundException("${childBag.first.name} not found in bags")
        recursiveSearch(bagInList, bags, containsBagName)
    } ?: return false
    return true in childContainsBag
}

// Part 2
fun howManyBagsMustBeInBag(bagName: String, bags: List<Bag>): Int {
    val bag = bags.find { it.name == bagName }
            ?: throw NameNotFoundException("$bagName not found in bags")
    return recursiveSearchNBags(bag, bags)

}

// Part 2
fun recursiveSearchNBags(bag: Bag, bags: List<Bag>): Int {
    val nInThisBag = bag.canContain?.map { it.second }?.sum() ?: 0
    val nInChildBags: List<Int> = bag.canContain?.map { childBag ->
        val bagInList = bags.find { it.name == childBag.first.name }
                ?: throw NameNotFoundException("${childBag.first.name} not found in bags")
        recursiveSearchNBags(bagInList, bags) * childBag.second
    } ?: emptyList()

    return nInThisBag + nInChildBags.sum()
}

fun main() {
    val bags = readInput()
    val searchForBagName = "shiny gold"
    //val nCanContain = howManyBagsCanContain(searchForBagName, bags)
    val nBagsIsNeeded = howManyBagsMustBeInBag(searchForBagName, bags)
    print(nBagsIsNeeded)

}