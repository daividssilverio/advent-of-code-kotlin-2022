fun main() {
    val inventory = readInput("Day01_test")
    val highestInventoryInCalories = findHighestInventoryInCalories(inventory)
    val sumOfTopThreeInventories = findTopSumsOfCaloriesInventories(inventory, 3)
    println(highestInventoryInCalories)
    println(sumOfTopThreeInventories)
}

fun findTopSumsOfCaloriesInventories(foodInventory: List<String>, count: Int): Int {
    var currentElfInventoryCaloriesSum = 0
    var maxCaloriesInventories = emptyList<Int>()
    foodInventory.forEachIndexed { index, food ->
        currentElfInventoryCaloriesSum += food.toIntOrNull() ?: 0
        if (food.isBlank() || index == foodInventory.lastIndex) {
            maxCaloriesInventories = (maxCaloriesInventories + currentElfInventoryCaloriesSum).sortedDescending().take(count)
            currentElfInventoryCaloriesSum = 0
        }
    }

    return maxCaloriesInventories.sum()
}
fun findHighestInventoryInCalories(foodInventory: List<String>): Int {
    var maxCalories = -1
    var currentElfInventoryCaloriesSum = 0
    foodInventory.forEachIndexed { index, food ->
        currentElfInventoryCaloriesSum += food.toIntOrNull() ?: 0
        if (food.isBlank() || index == foodInventory.lastIndex) {
            if (currentElfInventoryCaloriesSum > maxCalories) {
                maxCalories = currentElfInventoryCaloriesSum
            }
            currentElfInventoryCaloriesSum = 0
        }
    }
    return maxCalories
}
