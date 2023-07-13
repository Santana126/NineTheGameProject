package it.esercizi.ninethegame.logic

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class GameClass : Parcelable {

    @IgnoredOnParcel
    val squaresValues = mutableStateListOf("","","","","","","","","")

    @IgnoredOnParcel
    val secretCode = mutableStateListOf("","","","","","","","","")

    @IgnoredOnParcel
    val distanceVector = mutableStateListOf("","","","","","","","","")

    @IgnoredOnParcel
    val selectedSquareIndex = mutableStateOf(-1)

    @IgnoredOnParcel
    val retrySquaresValue = mutableStateListOf("","","","","","","","","")

    @IgnoredOnParcel
    val selectedRetrySquareIndex = mutableStateOf(-1)

    @IgnoredOnParcel
    val initializer = mutableStateOf(false)

    @IgnoredOnParcel
    val attempt = mutableStateOf(0)

    @IgnoredOnParcel
    val playerCode = mutableStateListOf("","","","","","","","","")


    fun calculatePlayerCode(){
        if(this.attempt.value == 0){
            squaresValues.forEachIndexed { index, s ->
                playerCode[index] = s
            }
        }else{
            retrySquaresValue.forEachIndexed { index, s ->
                playerCode[index] = s
            }
        }
    }

}