private fun buildStacksInfo(stackInput: List<String>): Map<Int, List<String>> {
    val stacks = mutableMapOf<Int, List<String>>()
    val iterator = stackInput.iterator()
    while (iterator.hasNext()) {
        val line = iterator.next()
        if (!iterator.hasNext()) break
        line.chunked(4).forEachIndexed { index, item ->
            stacks.compute(index + 1) { _, stack ->
                if (item.isNotBlank()) {
                    listOf(item) + (stack ?: emptyList())
                } else stack
            }
        }
    }
    return stacks
}

private fun operateMover(
    stacks: Map<Int, List<String>>,
    commands: List<String>,
    moveEngine: (MutableMap<Int, List<String>>, Int, Int, Int) -> Map<Int, List<String>>
): Map<Int, List<String>> {
    val regex = Regex("move (\\d+) from (\\d+) to (\\d+)")
    var localStacks = stacks
    for (command in commands) {
        val (count, source, destination) = regex.find(command)!!.destructured.toList().map { it.toInt() }
        localStacks = moveEngine(localStacks.toMutableMap(), count, source, destination)
    }
    return localStacks
}

private fun printTopOfStacks(stacks: Map<Int, List<String>>) {
    println(stacks.toSortedMap()
        .mapValues { it.value.last().replace("[", "").replace("]", "").trim() }
        .values
        .joinToString(separator = "")
    )
}

fun main() {
    val input = readInput("Day05_test")
    val indexOfStartOfCommands = input.indexOfFirst { it.isBlank() } + 1
    
    val startingStacks = buildStacksInfo(input.subList(0, indexOfStartOfCommands))
    val commands = input.drop(indexOfStartOfCommands)

    val mover9000resultingStacks = operateMover(startingStacks, commands) { stacks, count, source, destination ->
        repeat(count) {
            val toMove = stacks[source]?.takeLast(1) ?: emptyList()
            stacks[source] = stacks[source]?.dropLast(1) ?: emptyList()
            stacks[destination] = (stacks[destination] ?: emptyList()) + toMove
        }
        stacks
    }

    val mover9001resultingStacks = operateMover(startingStacks, commands) { stacks, count, source, destination ->
        val toMove = stacks[source]?.takeLast(count) ?: emptyList()
        stacks[source] = stacks[source]?.dropLast(count) ?: emptyList()
        stacks[destination] = (stacks[destination] ?: emptyList()) + toMove
        stacks
    }

    printTopOfStacks(mover9000resultingStacks)
    printTopOfStacks(mover9001resultingStacks)
}