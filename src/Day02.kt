import java.lang.Exception

fun main() {
    /*
    A, X = Rock (1)
    B, Y = Paper (2)
    C, Z = Scissors (3)

    win = 6
    lose = 0
    draw = 3
     */

    val entries = readInput("Day02_test")
    val player1games = mapOf('A' to 1, 'B' to 2, 'C' to 3)
    val player2games = mapOf('X' to 1, 'Y' to 2, 'Z' to 3)
    val resultMap = mapOf(
        0 to 3, // draw
        -1 to 0, // lose
        -2 to 6, // win
        1 to 6, // win
        2 to 0 // lose
    )
    val calculatedResult = entries.fold(0) { acc, game ->
        val player1game = player1games[game[0]] ?: throw Exception("invalid input $game")
        val player2game = player2games[game[2]] ?: throw Exception("invalid input $game")
        val result =
            resultMap[player2game - player1game] ?: throw Exception("unexpected result $player2game - $player1game")
        acc + player2game + result
    }

    println(calculatedResult)
}

