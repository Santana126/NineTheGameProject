package it.esercizi.ninethegame.logic.game

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.R
import it.esercizi.ninethegame.db.DbGameResult
import it.esercizi.ninethegame.db.GameResult
import it.esercizi.ninethegame.db.Repository
import it.esercizi.ninethegame.logic.profile.ProfileClass
import it.esercizi.ninethegame.logic.settings.SettingsClass
import java.time.LocalDate

class TrainingLogic {

    @Composable
    fun TrainingInit(
        navController: NavHostController,
        appSettings: SettingsClass,
        profile: ProfileClass
    ) {

        val hintCost = 3

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
            gameLogic.startTimer()
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

        val context = LocalContext.current

        if (showResult.value) {
            ShowResult(
                result.value,
                navController,
                game.attempt.value,
                appSettings.autoSave.value,
                gameLogic.gameTime,
                context
            )
            if (appSettings.autoSave.value) {
                saveResult(result.value, context, gameLogic.gameTime)
            }
            showResult.value = false
        }

        game.choice.value = appSettings.symbolChoice.value
        game.loadSymbol()

        gameField.GameFieldMaker(
            game, navController,
            {
                codeConfirm(game, gameLogic, result, showResult, message, showAlert, context)
            },
            {
                navController.navigate("main")
            },
            {
                gameLogic.getHint(game)
            },
            hintCost,
            {
                gameLogic.stopTimer()
            },
            {
                ((gameLogic.getTimeMin()) / 60)
            },
            {
                (((gameLogic.getTimeSec()) % 3600) / 60)
            },
            appSettings.timer.value,
            appSettings.autoInsert.value,
            profile
        )


    }


    private fun codeConfirm(
        game: GameClass,
        gameLogic: GameLogic,
        result: MutableState<Boolean>,
        showResult: MutableState<Boolean>,
        message: MutableState<String>,
        showAlert: MutableState<Boolean>,
        context: Context
    ): Boolean {
        if (gameLogic.checkMissing(game)) {
            message.value = context.getString(R.string.EnterAllSymbolsAlert)
            showAlert.value = true
            return false
        }
        if (game.attempt.value > 0) {

            if (gameLogic.checkRetryMissing(game)) {
                message.value = context.getString(R.string.EnterAllSymbolsAlert)
                showAlert.value = true
                return false
            }
        }

        gameLogic.trainingDistanceVectorCalculator(game)
        game.attempt.value++
        if (gameLogic.checkMatchingCode(game)) {
            gameLogic.stopTimer()
            result.value = true
            showResult.value = true
        }

        return true

    }


    @Composable
    fun ShowResult(
        result: Boolean,
        navController: NavHostController,
        attempt: Int,
        autoSave: Boolean,
        gameTime: MutableState<Int>,
        context: Context
    ) {


        val askForSave = stringResource(R.string.AskSaveGameStats)

        var message =
            stringResource(R.string.YouWon) + " in " + attempt + stringResource(R.string.Attempt) + "\n\n"


        if (!autoSave) {
            message += askForSave
        }

        val alertDialog =
            if (autoSave) {
                androidx.appcompat.app.AlertDialog.Builder(LocalContext.current)
                    .setTitle(stringResource(R.string.Finished))
                    .setMessage(
                        message
                    )
                    .setPositiveButton(stringResource(R.string.OkBtn)) { dialog, _ ->
                        navController.navigate("main")
                        dialog.dismiss()
                    }
                    .create()
            } else {
                androidx.appcompat.app.AlertDialog.Builder(LocalContext.current)
                    .setTitle(stringResource(R.string.Finished))
                    .setMessage(
                        message
                    )
                    .setPositiveButton(stringResource(R.string.Save)) { dialog, _ ->
                        saveResult(result, context, gameTime)
                        navController.navigate("main")
                        dialog.dismiss()
                    }
                    .setNegativeButton(stringResource(R.string.DiscardGameStats)) { dialog, _ ->
                        navController.navigate("main")
                        dialog.dismiss()
                    }
                    .create()
            }

        alertDialog.show()
    }


    private fun saveResult(
        result: Boolean,
        context: Context,
        gameTime: MutableState<Int>
    ) {

        val day = LocalDate.now().dayOfMonth.toString()
        val month = LocalDate.now().monthValue.toString()
        val year = LocalDate.now().year.toString()

        val score = if (result) {
            5
        } else {
            0
        }

        val gameResult = GameResult(score, day, month, year, gameTime.value, "Training")


        val db = DbGameResult.getInstance(context)
        val repository = Repository(db.gameResultDao())

        repository.insert(gameResult)

    }

    @Composable
    fun ShowAlert(message: String) {

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(LocalContext.current)
            .setTitle(stringResource(R.string.Warning))
            .setMessage(message)
            .setPositiveButton(stringResource(R.string.OkBtn)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()

    }

}