package it.esercizi.ninethegame.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import it.esercizi.ninethegame.R
import it.esercizi.ninethegame.ui.theme.MyAppTheme


@Composable
fun HomePage(navController: NavController) {

    MyAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 20.dp)
            ) {

                val (trainingBtn, playBtn, statsBtn, fbBtn, helpBtn) = createRefs()



                Button(
                    onClick = { },
                    modifier = Modifier
                        .constrainAs(trainingBtn) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(playBtn.top)
                        }
                        .padding(top = 20.dp)
                        .padding(8.dp)
                        .width(150.dp),

                    ) {
                    Text(
                        text = "Training",
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
                    Text(text = "Play", color = Color.Black)
                }
                Button(
                    onClick = { },
                    modifier = Modifier
                        .constrainAs(statsBtn) {
                            top.linkTo(playBtn.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(8.dp)
                        .width(120.dp),
                ) {
                    Text(text = "Stats", color = Color.Black)
                }
                Button(onClick = {

                },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .constrainAs(fbBtn) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .padding(10.dp)
                        .size(80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.feedback_icon ),
                        contentDescription = "FeedbackImage",
                        Modifier
                            .size(80.dp)

                    )
                }
                Button(onClick = {

                },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .constrainAs(helpBtn) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .padding(10.dp)
                        .size(80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.help_icon),
                        contentDescription = "HelpImage",
                        Modifier
                            .size(80.dp)

                    )
                }
            }
        }
    }

}

