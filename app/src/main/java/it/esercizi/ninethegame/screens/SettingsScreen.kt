package it.esercizi.ninethegame.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.logic.settings.SettingsClass
import it.esercizi.ninethegame.logic.settings.SettingsInit
import it.esercizi.ninethegame.ui.theme.MyAppTheme


@Composable
fun SettingsPage(
    navController: NavHostController,
    appSettings: SettingsClass,
    sharedPreferences: SharedPreferences,
    context: Context
) {

    MyAppTheme(backgroundChoice = appSettings.backgroundGradient.value) {
        val settingsInit = SettingsInit()
        settingsInit.showSettingsPage(navController,appSettings,sharedPreferences,context)

    }

}