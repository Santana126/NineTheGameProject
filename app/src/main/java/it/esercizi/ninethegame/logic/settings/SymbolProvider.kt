package it.esercizi.ninethegame.logic.settings

class SymbolProvider {

    val symbolsNumeric = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")

    val symbolsAlpha = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I")

    val symbolsEmoji = listOf(
        "😄",
        "😍",
        "\uD83D\uDE34",
        "\uD83D\uDC80",
        "\uD83D\uDC7D",
        "\uD83D\uDE13",
        "\uD83D\uDE1D",
        "\uD83D\uDE21",
        "\uD83D\uDE31"
    )

    val symbolsBall = listOf("⚽", "🏀", "🏈", "⚾", "\uD83E\uDD4E", "🎾", "🏐", "🏉", "🎱")

    val symbolsVehicle = listOf(
        "\uD83D\uDEF4",
        "\uD83D\uDE8C",
        "\uD83D\uDE95",
        "\uD83D\uDE9A",
        "\uD83D\uDE8B",
        "\uD83D\uDE86",
        "\uD83D\uDEA2",
        "\uD83D\uDEB2",
        "\uD83D\uDE9C"
    )

    val symbolsFruit = listOf(
        "\uD83C\uDF48",
        "\uD83C\uDF49",
        "\uD83C\uDF52",
        "\uD83C\uDF50",
        "\uD83C\uDF4E",
        "\uD83C\uDF4D",
        "\uD83C\uDF4C",
        "\uD83C\uDF53",
        "\uD83C\uDF47"
    )


    fun getAvailableTrailerSymbol(): List<List<String>> {
        return listOf(
            symbolsNumeric.take(3),
            symbolsAlpha.take(3),
            symbolsEmoji.take(3),
            symbolsBall.take(3),
            symbolsVehicle.take(3),
            symbolsFruit.take(3)
        )
    }

}