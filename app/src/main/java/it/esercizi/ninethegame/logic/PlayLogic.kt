package it.esercizi.ninethegame.logic

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

class PlayLogic {


    @Composable
    fun PlayInit(navController: NavHostController){


        val gameInit = remember {
            mutableStateOf(GameClass())
        }

        val game = gameInit.value


        val gameLogic = GameLogic()

        val newGame = remember {
            mutableStateOf(true)
        }

        if (newGame.value) {
            newGame.value = false
            gameLogic.secretCodeGeneratorSecond(game)
        }

        val gameField = GameField()


        val showAlert = remember {
            mutableStateOf(false)
        }
        val showResult = remember {
            mutableStateOf(false)
        }
        val result = remember {
            mutableStateOf(false)
        }
        val message = remember {
            mutableStateOf("")
        }

        if (showAlert.value) {
            ShowAlert(message = message.value)
        }

        if (showResult.value) {
            ShowResult(result.value,navController)
            saveResult(result.value)
            showResult.value = false
        }


        gameField.GameFieldMaker(game, navController)
        {
            codeConfirm(game,gameLogic,result,showResult)
        }


    }

    private fun codeConfirm(
        game: GameClass,
        gameLogic: GameLogic,
        result: MutableState<Boolean>,
        showResult: MutableState<Boolean>
    ) {
        if(game.attempt.value == 0){
            gameLogic.distanceVectorCalculator(game)
            game.attempt.value++
            Log.d("Distance Vector (game.distanceVector)",game.distanceVector.joinToString() )
        }else{
            gameLogic.distanceVectorCalculator(game)
            result.value = gameLogic.checkMatchingCode(game)
            showResult.value = true
        }

    }


    @Composable
    fun ShowAlert(message: String) {

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(LocalContext.current)
            .setTitle("Warning")
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                // Azioni da eseguire quando si preme il pulsante OK

                dialog.dismiss() // Chiude l'AlertDialog
            }
            .create()

        alertDialog.show()

    }

    private fun saveResult(result: Boolean) {
        val dummy = !result
        if(dummy){
            !dummy
        }
        /*
        TODO:
            Salvataggio della partita nel DB
         */

    }


    @Composable
    fun ShowResult(result: Boolean, navController: NavHostController) {


        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(LocalContext.current)
            .setTitle("Finished")
            .setMessage(
                if (result) {
                    "You have won"
                } else {
                    "Sorry, try again"
                }
            )
            .setPositiveButton("Ok") { dialog, _ ->
                // Azioni da eseguire quando si preme il pulsante OK

                saveResult(result)
                navController.navigate("main")
                dialog.dismiss() // Chiude l'AlertDialog
            }
            .create()

        alertDialog.show()

    }
}