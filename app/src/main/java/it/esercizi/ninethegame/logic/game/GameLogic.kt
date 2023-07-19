package it.esercizi.ninethegame.logic.game

import androidx.compose.runtime.mutableStateOf
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.random.Random

class GameLogic {

    private var timer: Timer? = null
    var gameTime = mutableStateOf(0)
    fun startTimer() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                gameTime.value += 1
                //println("Tempo trascorso: ${gameTime.value} secondi")
            }
        }, 1000, 1000)
    }


    fun stopTimer() {
        timer?.cancel()
        timer = null
        //println("Timer interrotto. Tempo trascorso: ${gameTime.value} secondi")
    }

    fun secretCodeGeneratorSecond(gameClass: GameClass){

        if(!gameClass.initializer.value){
            val secretCodeInit = generateRandomNumbers()

            secretCodeInit.forEachIndexed { index, s ->
                gameClass.secretCode[index] = s
            }

            //Log.d("Secret code (game.secretcode)", gameClass.secretCode.joinToString())
        }

    }

    private fun generateRandomNumbers(): List<String> {
        val numbers = mutableListOf<String>()

        while (numbers.size < 9) {
            val randomNumber = (Random.nextInt(9) + 1).toString()
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber)
            }
        }

        return numbers
    }


    fun distanceVectorCalculator(gameClass: GameClass){

        gameClass.calculatePlayerCode()
        val distanceVectorInit = checkForDistanceCode(gameClass.secretCode.toList(),gameClass.playerCode.toList())
/*
        Log.d("distanceVectorInit",distanceVectorInit.toString())
        Log.d("playerCode (game.playerCode)",gameClass.playerCode.joinToString())
        Log.d("secretCode (game.secretCode)",gameClass.secretCode.joinToString())
 */


        distanceVectorInit.forEachIndexed { index, s ->
            gameClass.distanceVector[index] = s
            if (s == "0"){
                gameClass.retrySquaresValue[index] = gameClass.squaresValues[index]
            }
        }

    }

    fun trainingDistanceVectorCalculator(gameClass: GameClass){

        gameClass.calculatePlayerCode()
        val distanceVectorInit = checkForDistanceCode(gameClass.secretCode.toList(),gameClass.playerCode.toList())
/*
        Log.d("distanceVectorInit",distanceVectorInit.toString())
        Log.d("playerCode (game.playerCode)",gameClass.playerCode.joinToString())
        Log.d("secretCode (game.secretCode)",gameClass.secretCode.joinToString())
 */

        if(gameClass.attempt.value > 0){

            gameClass.retrySquaresValue.forEachIndexed { index, s ->
                gameClass.squaresValues[index] = s
            }
        }

        distanceVectorInit.forEachIndexed { index, s ->
            gameClass.distanceVector[index] = s
            if (s == "0"){
                gameClass.retrySquaresValue[index] = gameClass.squaresValues[index]
            }else{
                gameClass.retrySquaresValue[index] = ""
            }
        }

    }

    private fun checkForDistanceCode(secretCode: List<String>?, playerCode: List<String>): List<String> {

        val distanceList = mutableListOf<String>()

        if(secretCode == null) return distanceList


        playerCode.forEachIndexed { indPlayer, playerVal ->
            secretCode.forEachIndexed { indSecret, secretVal ->
                if(playerVal == secretVal){
                    val d1 = (indSecret-indPlayer).absoluteValue
                    val d2 = (d1-9).absoluteValue
                    val distance = min(d1.absoluteValue,d2.absoluteValue)
                    distanceList.add(distance.toString())
                }
            }
        }
        return distanceList
    }

    fun checkMatchingCode(gameClass: GameClass): Boolean {

        gameClass.distanceVector.forEachIndexed { _, s ->
            if(s != "0"){
                return false
            }
        }
        return true
    }

    fun checkDuplicate(game: GameClass): Boolean {

        return game.squaresValues.size != game.squaresValues.distinct().size

    }

    fun checkMissing(game: GameClass): Boolean {
        return game.squaresValues.contains("")
    }

    fun getHint(game: GameClass){
        if(game.attempt.value >= 1){
            var finish = true
            while(finish) {
                val randNum = (0..8).random()
                //Log.d("random Num",randNum.toString())
                if(game.distanceVector[randNum] != "0"){
                    game.retrySquaresValue[randNum] = (game.secretCode[randNum].toInt()-1).toString()
                    finish = false
                }
            }
        }
    }

    fun checkRetryMissing(game: GameClass): Boolean {
        return game.retrySquaresValue.contains("")
    }

    fun checkRetryDuplicate(game: GameClass): Boolean {

        return game.retrySquaresValue.size != game.retrySquaresValue.distinct().size

    }

}