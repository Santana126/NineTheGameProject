package it.esercizi.ninethegame

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.esercizi.ninethegame.logic.settings.SettingsClass
import it.esercizi.ninethegame.screens.*
import it.esercizi.ninethegame.ui.theme.MyAppTheme


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)

        setContent{
            val appSettings = remember {SettingsClass()}

            appSettings.notification.value = sharedPreferences.getBoolean("notification", false)
            appSettings.backgroundGradient.value = sharedPreferences.getBoolean("backgroundGradient",true)
            appSettings.autoSave.value = sharedPreferences.getBoolean("autoSave",true)
            appSettings.autoInsert.value = sharedPreferences.getBoolean("autoInsert",false)
            appSettings.symbolChoice.value = sharedPreferences.getInt("symbolChoice",1)
            appSettings.language.value = sharedPreferences.getString("language","English").toString()



            MyAppTheme(backgroundChoice = appSettings.backgroundGradient.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "main"){
                        composable("main") { HomePage(navController,appSettings) }
                        composable("playPage") { PlayPage(navController,appSettings)}
                        composable("trainingPage"){ TrainingPage(navController,appSettings)}
                        composable("statsPage"){ StatsPage(navController,appSettings) }
                        composable("settingsPage"){ SettingsPage(navController,appSettings,sharedPreferences) }
                    }

                }
            }
        }
    }
}