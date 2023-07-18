package it.esercizi.ninethegame.logic.game

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.logic.settings.SettingsClass

class TrainingLogic {

    @Composable
    fun TrainingInit(navController: NavHostController, appSettings: SettingsClass) {

        val gameInit = remember {
            mutableStateOf(GameClass())
        }

        val game = gameInit.value

        val gameLogicInit = remember {
            mutableStateOf(GameLogic())
        }

        val gameLogic = gameLogicInit.value

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
            showAlert.value = false
        }

        if (showResult.value) {
            ShowResult(result.value, navController, game.attempt.value)
            saveResult(result.value)
            showResult.value = false
        }

        game.choice.value = appSettings.symbolChoice.value
        game.loadSymbol()

        gameField.GameFieldMaker(game, navController,
            {
                codeConfirm(game, gameLogic, result, showResult, message, showAlert)
            },
            {
                navController.navigate("main")
            },
            {
                gameLogic.getHint(game)
            }
        )


    }


    private fun codeConfirm(
        game: GameClass,
        gameLogic: GameLogic,
        result: MutableState<Boolean>,
        showResult: MutableState<Boolean>,
        message: MutableState<String>,
        showAlert: MutableState<Boolean>
    ): Boolean {
        if (gameLogic.checkMissing(game)) {
            message.value = "You must enter all the symbols"
            showAlert.value = true
            return false
        }

        gameLogic.trainingDistanceVectorCalculator(game)
        game.attempt.value++
        if (gameLogic.checkMatchingCode(game)) {
            result.value = true
            showResult.value = true
        }
        Log.d("Distance Vector (game.distanceVector)", game.distanceVector.joinToString())

        return true

    }


    @Composable
    fun ShowResult(result: Boolean, navController: NavHostController, attempt: Int) {

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
                navController.navigate("main")
                dialog.dismiss()
            }
            .create()

        alertDialog.show()

    }


    private fun saveResult(result: Boolean) {
        val dummy = !result
        if (dummy) {
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
                dialog.dismiss()
            }
            .create()

        alertDialog.show()

    }

}