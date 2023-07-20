package it.esercizi.ninethegame

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.esercizi.ninethegame.logic.profile.ProfileClass
import it.esercizi.ninethegame.logic.settings.SettingsClass
import it.esercizi.ninethegame.screens.*
import it.esercizi.ninethegame.ui.theme.MyAppTheme


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferencesSettings: SharedPreferences
    lateinit var sharedPreferencesProfile: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferencesSettings = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        sharedPreferencesProfile = getSharedPreferences("Profile",Context.MODE_PRIVATE)

        setContent{
            val appSettings = remember {SettingsClass()}
            val profile = remember {ProfileClass()}

            appSettings.notification.value = sharedPreferencesSettings.getBoolean("notification", false)
            appSettings.backgroundGradient.value = sharedPreferencesSettings.getBoolean("backgroundGradient",true)
            appSettings.autoSave.value = sharedPreferencesSettings.getBoolean("autoSave",true)
            appSettings.autoInsert.value = sharedPreferencesSettings.getBoolean("autoInsert",false)
            appSettings.symbolChoice.value = sharedPreferencesSettings.getInt("symbolChoice",1)
            //appSettings.language.value = sharedPreferencesSettings.getString("language","English").toString()

            profile.firstAccess.value = sharedPreferencesProfile.getBoolean("firstAccess",true)
            profile.money.value = sharedPreferencesProfile.getInt("money",100)
            //profile.nickname.value = sharedPreferencesProfile.getString("nickName","Guest").toString()

            val context = LocalContext.current

            MyAppTheme(backgroundChoice = appSettings.backgroundGradient.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "main"){
                        composable("main") { HomePage(navController,appSettings,profile) }
                        composable("playPage") { PlayPage(navController,appSettings,profile,sharedPreferencesProfile,context)}
                        composable("trainingPage"){ TrainingPage(navController,appSettings,profile)}
                        composable("statsPage"){ StatsPage(navController,appSettings) }
                        composable("settingsPage"){ SettingsPage(navController,appSettings,sharedPreferencesSettings,context) }
                    }

                }
            }
        }
    }

}