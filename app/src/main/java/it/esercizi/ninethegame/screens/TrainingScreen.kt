package it.esercizi.ninethegame.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.logic.game.TrainingLogic
import it.esercizi.ninethegame.logic.settings.SettingsClass
import it.esercizi.ninethegame.ui.theme.MyAppTheme


@Composable
fun TrainingPage(navController: NavHostController, appSettings: SettingsClass) {

    MyAppTheme(backgroundChoice = appSettings.backgroundChoice.value) {
        val trainingLogic = TrainingLogic()
        trainingLogic.TrainingInit(navController,appSettings)
    }

}
