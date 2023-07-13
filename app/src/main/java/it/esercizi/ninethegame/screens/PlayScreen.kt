package it.esercizi.ninethegame.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.logic.PlayLogic
import it.esercizi.ninethegame.ui.theme.MyAppTheme

@Composable
fun PlayPage(navController: NavHostController) {

    MyAppTheme {
        val playLogic = PlayLogic()
        playLogic.PlayInit(navController)
    }

}