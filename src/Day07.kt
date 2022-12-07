fun main() {
    val logs = readInput("Day07_test")
    val root = Node(
        name = "/",
        parent = null,
        children = mutableMapOf(),
        isFolder = true,
        size = 0
    )
    var currentFolder: Node = root
    for (entry in logs) {
        when {
            entry.startsWith("$ cd") -> {
                currentFolder = when (val dest = entry.removePrefix("$ cd ")) {
                    "/" -> root
                    ".." -> currentFolder.parent ?: root
                    else -> {
                        currentFolder.children.computeIfAbsent(dest) {
                            Node("${currentFolder.name}/$it", currentFolder, isFolder = true)
                        }
                    }
                }
            }

            entry.startsWith("$ ls") -> {}

            entry.startsWith("dir") -> {
                val folderListing = entry.removePrefix("dir ")
                currentFolder.children.computeIfAbsent(folderListing) {
                    Node("${currentFolder.name}/$it", currentFolder, isFolder = true)
                }
            }

            else -> {
                val (size, fileName) = entry.split(" ").let { it[0].toInt() to it[1] }
                currentFolder.children.computeIfAbsent(fileName) {
                    Node(it, parent = currentFolder, size = size)
                }
            }
        }
    }

    println(part1(root))
    println(part2(root))
}

private fun part1(root: Node): Int {
    val nodesToCalculate = mutableListOf(root)
    var sum = 0
    while (nodesToCalculate.isNotEmpty()) {
        val currentNode: Node = nodesToCalculate.removeFirst()
        val size = nodeSize(currentNode)
        if (size <= 100000) {
            sum += size
        }
        nodesToCalculate.addAll(currentNode.children.values.filter { it.isFolder })
    }
    return sum
}

private fun part2(root: Node): Int {
    val diskSize = 70000000
    val usedSpace = nodeSize(root)
    val availableSpace = diskSize - usedSpace
    val spaceNecessaryForUpdate = 30000000
    println(availableSpace)

    val nodesToCalculate = mutableListOf(root)
    val folderSizes = mutableListOf<Int>()
    while (nodesToCalculate.isNotEmpty()) {
        val currentNode: Node = nodesToCalculate.removeFirst()
        val size = nodeSize(currentNode)
        if (availableSpace + size >= spaceNecessaryForUpdate) {
            folderSizes += size
        }
        nodesToCalculate.addAll(currentNode.children.values.filter { it.isFolder })
    }
    return folderSizes.min()
}

private fun nodeSize(node: Node): Int {
    return if (node.isFolder) node.children.values.sumOf { nodeSize(it) }
    else node.size
}

private class Node(
    val name: String,
    val parent: Node?,
    val children: MutableMap<String, Node> = mutableMapOf(),
    val isFolder: Boolean = false,
    val size: Int = 0,
)