package it.esercizi.ninethegame.logic.game

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import it.esercizi.ninethegame.logic.settings.SymbolProvider
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class GameClass : Parcelable {

    @IgnoredOnParcel
    val choice = mutableStateOf(1)

    @IgnoredOnParcel
    val squaresSymbol = mutableListOf("", "", "", "", "", "", "", "", "")

    @IgnoredOnParcel
    val squaresValues = mutableStateListOf("", "", "", "", "", "", "", "", "")

    @IgnoredOnParcel
    val secretCode = mutableStateListOf("", "", "", "", "", "", "", "", "")

    @IgnoredOnParcel
    val distanceVector = mutableStateListOf("", "", "", "", "", "", "", "", "")

    @IgnoredOnParcel
    val selectedSquareIndex = mutableStateOf(-1)

    @IgnoredOnParcel
    val retrySquaresValue = mutableStateListOf("", "", "", "", "", "", "", "", "")

    @IgnoredOnParcel
    val selectedRetrySquareIndex = mutableStateOf(-1)

    @IgnoredOnParcel
    val initializer = mutableStateOf(false)

    @IgnoredOnParcel
    val attempt = mutableStateOf(0)

    @IgnoredOnParcel
    val playerCode = mutableStateListOf("", "", "", "", "", "", "", "", "")


    fun calculatePlayerCode() {
        if (this.attempt.value == 0) {
            squaresValues.forEachIndexed { index, s ->
                playerCode[index] = (s.toInt() + 1).toString()
            }
        } else {
            retrySquaresValue.forEachIndexed { index, s ->
                playerCode[index] = (s.toInt() + 1).toString()
            }
        }
    }

    fun loadSymbol() {

        val choice = this.choice.value

        val symbolProvider = SymbolProvider()

        when (choice) {
            1 -> symbolProvider.symbolsNumeric.forEachIndexed { index, s ->
                squaresSymbol[index] = s
            }
            2 -> symbolProvider.symbolsAlpha.forEachIndexed { index, i ->
                squaresSymbol[index] = i
            }
            3 -> symbolProvider.symbolsEmoji.forEachIndexed { index, i ->
                squaresSymbol[index] = i
            }
            4 -> symbolProvider.symbolsBall.forEachIndexed { index, i ->
                squaresSymbol[index] = i
            }
            5 -> symbolProvider.symbolsVehicle.forEachIndexed { index, i ->
                squaresSymbol[index] = i
            }
            6 -> symbolProvider.symbolsFruit.forEachIndexed { index, i ->
                squaresSymbol[index] = i
            }
        }
    }


}