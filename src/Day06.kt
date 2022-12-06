fun main() {
    val stream = readInput("Day06_test")[0]

    var startOfPackage: Int? = null
    for (i in 0..stream.length - 4) {
        if (stream.subSequence(i, i + 4).toSet().size == 4) {
            startOfPackage = i + 4
            break
        }
    }
    print(startOfPackage)

    var startOfMessage: Int? = null
    for (i in (startOfPackage ?: 0)..stream.length - 14) {
        if (stream.subSequence(i, i + 14).toSet().size == 14) {
            startOfMessage = i + 14
            break
        }
    }
    print(startOfMessage)
}