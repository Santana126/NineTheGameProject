package it.esercizi.ninethegame.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.logic.TrainingLogic
import it.esercizi.ninethegame.ui.theme.MyAppTheme


@Composable
fun TrainingPage(navController: NavHostController) {

    MyAppTheme {
        val trainingLogic = TrainingLogic()
        trainingLogic.TrainingInit(navController)
    }

}
