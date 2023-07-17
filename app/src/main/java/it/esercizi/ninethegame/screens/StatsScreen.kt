package it.esercizi.ninethegame.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.logic.settings.SettingsClass
import it.esercizi.ninethegame.logic.stats.StatsClass
import it.esercizi.ninethegame.ui.theme.MyAppTheme


@Composable
fun StatsPage(navController: NavHostController, appSettings: SettingsClass) {
    MyAppTheme(backgroundChoice = appSettings.backgroundChoice.value) {
        val statsClass = StatsClass()
        statsClass.StatsScreen(navController)
    }
}