package it.esercizi.ninethegame.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.logic.settings.SettingsClass
import it.esercizi.ninethegame.logic.settings.SettingsInit
import it.esercizi.ninethegame.ui.theme.MyAppTheme


@Composable
fun SettingsPage(navController: NavHostController,appSettings: SettingsClass) {

    MyAppTheme(backgroundChoice = appSettings.backgroundChoice.value) {
        val settingsInit = SettingsInit()
        settingsInit.showSettingsPage(navController,appSettings)

    }

}