package it.esercizi.ninethegame.screens


import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.logic.game.PlayLogic
import it.esercizi.ninethegame.logic.profile.ProfileClass
import it.esercizi.ninethegame.logic.settings.SettingsClass
import it.esercizi.ninethegame.ui.theme.MyAppTheme

@Composable
fun PlayPage(
    navController: NavHostController,
    appSettings: SettingsClass,
    profile: ProfileClass,
    sharedPreferencesProfile: SharedPreferences
) {

    MyAppTheme(backgroundChoice = appSettings.backgroundGradient.value) {
        val playLogic = PlayLogic()

        playLogic.PlayInit(navController, appSettings, profile, sharedPreferencesProfile)


    }
}