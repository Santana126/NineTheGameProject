package it.esercizi.ninethegame.logic.game

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.db.DbGameResult
import it.esercizi.ninethegame.db.GameResult
import it.esercizi.ninethegame.db.Repository
import it.esercizi.ninethegame.logic.settings.SettingsClass
import java.time.LocalDate

class PlayLogic {


    @Composable
    fun PlayInit(navController: NavHostController, appSettings: SettingsClass){


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

        val context = LocalContext.current

        if (showResult.value) {
            ShowResult(result.value,navController)
            saveResult(result.value, context)
            showResult.value = false
        }

        game.choice.value = appSettings.symbolChoice.value
        game.loadSymbol()


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
            if(gameLogic.checkMatchingCode(game)){
                result.value = true
                showResult.value = true
            }
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

    private fun saveResult(result: Boolean, context: Context) {

        val day = LocalDate.now().dayOfMonth.toString()
        val month = LocalDate.now().monthValue.toString()
        val year = LocalDate.now().year.toString()

        val score = if(result){
            10
        }else{
            2
        }

        val gameResult = GameResult(score,day,month,year)


        val db = DbGameResult.getInstance(context)
        val repository = Repository(db.gameResultDao())

        repository.insert(gameResult)

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

                //saveResult(result)
                navController.navigate("main")
                dialog.dismiss() // Chiude l'AlertDialog
            }
            .create()

        alertDialog.show()

    }
}