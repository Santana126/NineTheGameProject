package it.esercizi.ninethegame.screens


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.logic.game.PlayLogic
import it.esercizi.ninethegame.logic.settings.SettingsClass
import it.esercizi.ninethegame.ui.theme.MyAppTheme

@Composable
fun PlayPage(navController: NavHostController, appSettings: SettingsClass) {

    MyAppTheme(backgroundChoice = appSettings.backgroundGradient.value) {
        val playLogic = PlayLogic()

        playLogic.PlayInit(navController, appSettings)


    }
}