package it.esercizi.ninethegame

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val appSettings = remember {SettingsClass()}
            MyAppTheme(backgroundChoice = appSettings.backgroundChoice.value) {
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
                        composable("settingsPage"){ SettingsPage(navController,appSettings) }
                    }

                }
            }
        }
    }
}