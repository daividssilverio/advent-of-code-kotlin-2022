
private fun extractRange(stringRange: String): IntRange = stringRange.split("-").let { it[0].toInt()..it[1].toInt() }
private fun IntRange.contains(other: IntRange) = this.first <= other.first && other.last <= this.last
private fun IntRange.overlap(other: IntRange) =
    this.first in other || this.last in other || other.first in this || other.last in this
fun main() {
    val rawPairs = readInput("Day04_test")
    val rangePairs = rawPairs.map { pair -> pair.split(",").let { extractRange(it[0]) to extractRange(it[1]) } }
    val pairsWithOneCompleteOverlap = rangePairs.count { it.first.contains(it.second) || it.second.contains(it.first) }
    val pairsWithAnyOverlap = rangePairs.count { it.first.overlap(it.second) }

    println(pairsWithOneCompleteOverlap)
    println(pairsWithAnyOverlap)
}