package it.esercizi.ninethegame.logic.game

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

class TrainingLogic{

    @Composable
    fun TrainingInit(navController: NavHostController){

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
            ShowResult(result.value,navController,game.attempt.value)
            saveResult(result.value)
            showResult.value = false
        }

        game.loadSymbol()
        game.choice.value = 1

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

        gameLogic.trainingDistanceVectorCalculator(game)
        game.attempt.value++
        if (gameLogic.checkMatchingCode(game)){
            result.value = true
            showResult.value = true
        }
        Log.d("Distance Vector (game.distanceVector)",game.distanceVector.joinToString() )

    }


    @Composable
    fun ShowResult(result: Boolean, navController: NavHostController,attempt: Int) {

        //Il result sarà sempre True. Sistemare in questo caso. (es. result = false se si esce dalla partita)
        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(LocalContext.current)
            .setTitle("Finished")
            .setMessage(
                if (result) {
                    "You have won in $attempt tries"
                } else {
                    "Sorry, try again"
                }
            )
            .setPositiveButton("Ok") { dialog, _ ->
                // Azioni da eseguire quando si preme il pulsante OK

                //saveResult(result)
                navController.navigate("main")
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

}