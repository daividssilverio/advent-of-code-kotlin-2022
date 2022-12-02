import java.lang.Exception

fun main() {
    /*
    scoring
    win = 6
    lose = 0
    draw = 3

    initial strategy (wrong)
    A, X = Rock (1)
    B, Y = Paper (2)
    C, Z = Scissors (3)

    actual strategy
    X = lose
    Y = draw
    Z = win
    */

    val entries = readInput("Day02_test")

    val wronglyCalculatedResult = entries.fold(0) { acc, game ->
        acc + judgeMatch(game) { _, player2 -> player2.minus(23) }
    }

    val actualIdealResult = entries.fold(0) { acc, game ->
        acc + judgeMatch(game) { player1, player2 ->
            when (player2) {
                'X' -> loseTo(player1)
                'Z' -> winAgainst(player1)
                'Y' -> player1
                else -> throw Exception("Invalid game $game $player1, $player2")
            }
        }
    }
    println(wronglyCalculatedResult)
    println(actualIdealResult)
}

private fun Char.scoreValue() = code - 64
private fun winAgainst(game: Char) = when (game) {
    'A' -> 'B'
    'B' -> 'C'
    'C' -> 'A'
    else -> throw Exception("Invalid hand $game")
}

private fun loseTo(game: Char) = winAgainst(winAgainst(game))

private val resultMap = mapOf(
    0 to 3, // draw
    -1 to 0, // lose
    -2 to 6, // win
    1 to 6, // win
    2 to 0 // lose
)

private fun judgeMatch(game: String, strategy: (Char, Char) -> Char): Int {
    val player1game = game[0]
    val player2Strategy = game[2]
    val player2game = strategy(player1game, player2Strategy)
    return (resultMap[player2game - player1game] ?: throw Exception("unexpected result $player2game - $player1game")) +
            player2game.scoreValue()
}