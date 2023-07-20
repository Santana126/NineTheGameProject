package it.esercizi.ninethegame.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import it.esercizi.ninethegame.R
import it.esercizi.ninethegame.logic.FeedbackClass
import it.esercizi.ninethegame.logic.profile.ProfileClass
import it.esercizi.ninethegame.logic.rules.RulesClass
import it.esercizi.ninethegame.logic.settings.SettingsClass
import it.esercizi.ninethegame.ui.theme.MyAppTheme


@Composable
fun HomePage(navController: NavController, appSettings: SettingsClass, profile: ProfileClass) {

    MyAppTheme(backgroundChoice = appSettings.backgroundGradient.value) {

        val showRules = remember {
            mutableStateOf(false)
        }

        val showFeedbackForm = remember {
            mutableStateOf(false)
        }

        val count = remember {
            mutableStateOf(0)
        }

        val prizeAvailable = remember {
            mutableStateOf(true)
        }

        if ((count.value == 9) && (prizeAvailable.value)){

            val alertDialog = androidx.appcompat.app.AlertDialog.Builder(LocalContext.current)
                .setTitle("Compliments")
                .setMessage("You found the mistery rewards\n"+"I give you 100 coins...\n\n\ttimes NINE")
                .setPositiveButton("Thanks \uD83D\uDC52") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            alertDialog.show()
            prizeAvailable.value = false
            profile.money.value = profile.money.value + 900
        }

        if (showFeedbackForm.value) {
            FeedbackClass().ShowFeedbackForm {
                showFeedbackForm.value = false
            }
        } else if (showRules.value) {
            val rulesClass = RulesClass()
            rulesClass.ShowRules { showRules.value = false }
        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp)
                        .fillMaxWidth()
                ) {
                    Row(modifier = Modifier
                        .background(Color.White)
                        .border(2.dp, color = Color.DarkGray)) {

                        Icon(imageVector = Icons.Default.CurrencyBitcoin, contentDescription = "Money")
                        Text(text = profile.money.value.toString())
                    }
                }
                Spacer(modifier = Modifier.weight(0.05f))

                Row(
                    modifier = Modifier
                        .weight(0.3f)
                        .align(CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.nine_title),
                        contentDescription = "AppTitleLogo",
                        modifier = Modifier
                            .align(CenterVertically)
                            .clickable(onClick = { count.value++ })
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(0.3f)
                        .align(CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo_home),
                        contentDescription = "AppLogoImg",
                        modifier = Modifier
                            .align(CenterVertically)
                            //.size(250.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(0.1f))

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        // .padding(top = 20.dp)
                        .weight(0.5f)
                ) {

                    val (trainingBtn, playBtn, statsBtn) = createRefs()



                    Button(
                        onClick = { navController.navigate("trainingPage") },
                        modifier = Modifier
                            .constrainAs(trainingBtn) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(playBtn.top)
                            }
                            //.padding(top = 20.dp)
                            .padding(8.dp)
                            .width(150.dp),

                        ) {
                        Text(
                            text = stringResource(R.string.Training),
                            color = Color.Black

                        )
                    }
                    Button(
                        onClick = { navController.navigate("playPage") },
                        modifier = Modifier
                            .constrainAs(playBtn) {
                                top.linkTo(trainingBtn.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(statsBtn.top)
                            }
                            .padding(8.dp)
                            .width(150.dp),
                    ) {
                        Text(text = stringResource(R.string.Play), color = Color.Black)
                    }
                    Button(
                        onClick = { navController.navigate("statsPage") },
                        modifier = Modifier
                            .constrainAs(statsBtn) {
                                top.linkTo(playBtn.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                            .padding(8.dp)
                            .width(150.dp),
                    ) {
                        Text(text = stringResource(R.string.Stats), color = Color.Black)
                    }

                }
                Spacer(modifier = Modifier.weight(0.2f))
                BottomAppBar(
                    cutoutShape = CircleShape,
                    modifier = Modifier.align(CenterHorizontally)
                ) {

                    IconButton(
                        onClick = { showRules.value = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Help, contentDescription = "Help")
                    }
                    IconButton(
                        onClick = { navController.navigate("settingsPage") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                    IconButton(onClick = {
                        showFeedbackForm.value = true
                    }, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Feedback, contentDescription = "Feedback")

                    }
                }
            }
        }
    }
}

