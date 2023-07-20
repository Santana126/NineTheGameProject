package it.esercizi.ninethegame.logic.game

import android.content.Context
import android.content.SharedPreferences
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

class PlayLogic {


    @Composable
    fun PlayInit(
        navController: NavHostController,
        appSettings: SettingsClass,
        profile: ProfileClass,
        sharedPreferencesProfile: SharedPreferences,
        context: Context
    ) {


        val hintCost = 5

        sharedPreferencesProfile.edit().putInt("money",profile.money.value).apply()


        val gameInit = remember {
            mutableStateOf(GameClass())
        }
        val gameLogicInit = remember {
            mutableStateOf(GameLogic())
        }

        val game = gameInit.value


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
                gameLogic.gameTime,
                appSettings.autoSave.value,
                profile,
                navController,
                context
            )
            if (appSettings.autoSave.value) {
                saveResult(result.value, context, gameLogic.gameTime,profile)
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
                gameLogic.stopTimer()
                navController.navigate("main")
            },
            {
                gameLogic.getHint(game)
                profile.money.value = profile.money.value - hintCost
            },
            hintCost,
            {
                gameLogic.stopTimer()
            },
            {
                gameLogic.getTimeMin()
            },
            {
                gameLogic.getTimeSec()
            },
            appSettings.autoInsert.value,profile
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
        if (gameLogic.checkDuplicate(game)) {
            message.value = context.getString(R.string.EnterDifferentSymbolsAlert)
            showAlert.value = true
            return false
        }
        //Log.d("Stampo timer", gameLogic.gameTime.value.toString())

        if (game.attempt.value == 0) {
            gameLogic.distanceVectorCalculator(game)
            game.attempt.value++
            if (gameLogic.checkMatchingCode(game)) {
                gameLogic.stopTimer()
                //Log.d("Timer Interrotto", gameLogic.gameTime.value.toString())
                result.value = true
                showResult.value = true
            }
            //Log.d("Distance Vector (game.distanceVector)", game.distanceVector.joinToString())
        } else {
            if (gameLogic.checkRetryMissing(game)) {
                message.value = context.getString(R.string.EnterAllSymbolsAlert)
                showAlert.value = true
                return false
            } else if (gameLogic.checkRetryDuplicate(game)) {
                message.value = context.getString(R.string.EnterDifferentSymbolsAlert)
                showAlert.value = true
                return false
            } else {
                gameLogic.stopTimer()
                //Log.d("Timer Interrotto", gameLogic.gameTime.value.toString())
                gameLogic.distanceVectorCalculator(game)
                result.value = gameLogic.checkMatchingCode(game)
                showResult.value = true
            }
        }
        return true
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

    private fun saveResult(
        result: Boolean,
        context: Context,
        gameTime: MutableState<Int>,
        profile: ProfileClass
    ) {



        profile.money.value = profile.money.value + 10


        val day = LocalDate.now().dayOfMonth.toString()
        val month = LocalDate.now().monthValue.toString()
        val year = LocalDate.now().year.toString()

        val score = if (result) {
            10
        } else {
            2
        }

        val gameResult = GameResult(score, day, month, year, gameTime.value, "Play")


        val db = DbGameResult.getInstance(context)
        val repository = Repository(db.gameResultDao())

        repository.insert(gameResult)

    }


    @Composable
    fun ShowResult(
        result: Boolean,
        time: MutableState<Int>,
        autoSave: Boolean,
        profile: ProfileClass,
        navController: NavHostController,
        context: Context
    ) {

        val askForSave = stringResource(R.string.AskSaveGameStats)

        var message = if (result) {
            stringResource(R.string.YouWon) + "\n" + stringResource(R.string.Time) + ": " + ((time.value % 3600) / 60) + stringResource(
                R.string.minutes) + " - " + (time.value % 60) + stringResource(R.string.seconds) + "\n\n"
        } else {
            stringResource(R.string.TryAgain) + "\n" + stringResource(R.string.Time) + ":" + ((time.value % 3600) / 60) + stringResource(
                R.string.minutes) + " - " + (time.value % 60) + stringResource(R.string.seconds) + "\n\n"
        }

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
                        saveResult(result, context, time,profile)
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
}