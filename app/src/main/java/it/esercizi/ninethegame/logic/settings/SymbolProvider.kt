package it.esercizi.ninethegame.logic.settings

class SymbolProvider {

        val symbolsNumeric = listOf("1","2","3","4","5","6","7","8","9")

        val symbolsAlpha = listOf("A","B","C","D","E","F","G","H","I")

        val symbolsEmoji = listOf("😄","😍","💗","😍","💗","😍","💗","😍","💗")

        val symbolsBall = listOf("⚽","🏀","🏈","⚾","\uD83E\uDD4E","🎾","🏐","🏉","🎱")

        val symbolsHeart = listOf("❤️","\uD83E\uDDE1","💛","💚","💙","💜","🖤","🤎","🤍")

        val symbolsColoredCircle = listOf("⚪","⚫","🔴","🔵","🟤","🟣","🟢","🟡","🟠")

        fun getAvailableTrailerSymbol(): List<List<String>> {
                return listOf(
                        symbolsNumeric.take(3),
                        symbolsAlpha.take(3),
                        symbolsEmoji.take(3),
                        symbolsBall.take(3)
                )
        }

}