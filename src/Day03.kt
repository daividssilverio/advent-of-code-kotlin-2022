fun String.splitInHalf() = this.subSequence(0, length / 2) to this.subSequence(length / 2, length)
fun Char.toPriority() = if (isLowerCase()) code - 96 else code - 38
fun main() {
    val rucksacks = readInput("Day03_test")
    val sumOfDuplicates = rucksacks
        .flatMap { rucksack ->
            rucksack.splitInHalf()
                .let { compartment -> compartment.first.toSet().intersect(compartment.second.toSet()) }
        }
        .sumOf(Char::toPriority)
    println(sumOfDuplicates)

    val sumOfBadges = rucksacks
        .chunked(3)
        .flatMap { group -> group.map { it.toSet() }.reduce(Set<Char>::intersect) }
        .sumOf(Char::toPriority)

    println(sumOfBadges)
}