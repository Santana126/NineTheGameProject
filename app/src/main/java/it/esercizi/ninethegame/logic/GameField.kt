package it.esercizi.ninethegame.logic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.ui.theme.BtnBorder

class GameField {

    @Composable
    fun GameFieldMaker(

        game: GameClass,
        navController: NavHostController,
        confirmRequest: () -> Unit
    ) {


        val squareLineRef = mutableStateOf<ConstrainedLayoutReference?>(null)

        val showDistace = remember {
            mutableStateOf(false)
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
        ) {

            val separatorLine = createRef()


            Spacer(modifier = Modifier
                .constrainAs(separatorLine) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )


            SquaresMaker(game, squareLineRef, showDistace.value)
            {
                if (game.attempt.value <= 0) {
                    game.selectedSquareIndex.value = it
                } else {
                    game.selectedSquareIndex.value = -1
                    game.selectedRetrySquareIndex.value = it
                }
            }

            KeyBoardMaker(
                {
                    if (game.attempt.value <= 0) {
                        game.squaresValues[game.selectedSquareIndex.value] = it.toString()
                        game.selectedSquareIndex.value = (game.selectedSquareIndex.value + 1) % 9
                    } else {
                        game.retrySquaresValue[game.selectedRetrySquareIndex.value] = it.toString()
                        game.selectedRetrySquareIndex.value =
                            (game.selectedRetrySquareIndex.value + 1) % 9
                    }

                },
                { navController.navigate("main") },
                {
                    confirmRequest()
                    game.selectedSquareIndex.value = -1
                    showDistace.value = true
                }
            )


        }


    }


    @Composable
    fun SquaresMaker(
        game: GameClass,
        squareLineRef: MutableState<ConstrainedLayoutReference?>,
        showDistance: Boolean,
        squareClick: (Int) -> Unit

    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
        ) {

            val squareRefs = remember { mutableListOf<ConstrainedLayoutReference>() }

            for (i in 1..9) {
                squareRefs.add(createRef())
            }

            squareLineRef.value = squareRefs[0]

            for (i in 0..8) {
                Text(
                    text = game.squaresValues[i],
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .border(4.dp, color = Color.Black)
                        .background(
                            if (game.selectedSquareIndex.value == (i)) {
                                Color.Gray
                            } else {
                                Color.White
                            }
                        )
                        .clickable {
                            if (game.attempt.value == 0) {
                                squareClick(i)
                            }
                        }
                        .constrainAs(squareRefs[i]) {
                            top.linkTo(parent.top)
                            when (i) {
                                0 -> {
                                    start.linkTo(parent.start)
                                    end.linkTo(squareRefs[1].start)
                                }
                                8 -> {
                                    end.linkTo(parent.end)
                                    start.linkTo(squareRefs[7].end)
                                }
                                else -> {
                                    start.linkTo(squareRefs[i - 1].end)
                                    end.linkTo(squareRefs[i + 1].start)
                                }
                            }
                        }
                )
            }

            val distanceSquareRefs = remember { mutableListOf<ConstrainedLayoutReference>() }


            for (i in 1..9) {
                distanceSquareRefs.add(createRef())
            }

            val retrySquaresRefs = remember { mutableListOf<ConstrainedLayoutReference>() }


            for (i in 1..9) {
                retrySquaresRefs.add(createRef())
            }

            if (showDistance) {


                for (i in 0..8) {
                    Text(
                        text = game.distanceVector[i],
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .border(4.dp, color = Color.Black)
                            .background(
                                if (game.distanceVector[i] == "0") {
                                    Color.Green
                                } else {
                                    Color.LightGray
                                }
                            )
                            .constrainAs(distanceSquareRefs[i]) {
                                top.linkTo(squareRefs[i].bottom)
                                when (i) {
                                    0 -> {
                                        start.linkTo(parent.start)
                                        end.linkTo(distanceSquareRefs[1].start)
                                    }
                                    8 -> {
                                        end.linkTo(parent.end)
                                        start.linkTo(distanceSquareRefs[7].end)
                                    }
                                    else -> {
                                        start.linkTo(distanceSquareRefs[i - 1].end)
                                        end.linkTo(distanceSquareRefs[i + 1].start)
                                    }
                                }
                            }
                    )
                }

                for (i in 0..8) {
                    Text(
                        text = game.retrySquaresValue[i],
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .border(4.dp, color = Color.Black)
                            .background(
                                if (game.selectedRetrySquareIndex.value == (i)) {
                                    Color.Gray
                                } else {
                                    Color.White
                                }
                            )
                            .clickable { squareClick(i) }
                            .constrainAs(retrySquaresRefs[i]) {
                                top.linkTo(distanceSquareRefs[i].bottom)
                                when (i) {
                                    0 -> {
                                        start.linkTo(parent.start)
                                        end.linkTo(retrySquaresRefs[1].start)
                                    }
                                    8 -> {
                                        end.linkTo(parent.end)
                                        start.linkTo(retrySquaresRefs[7].end)
                                    }
                                    else -> {
                                        start.linkTo(retrySquaresRefs[i - 1].end)
                                        end.linkTo(retrySquaresRefs[i + 1].start)
                                    }
                                }
                            }
                    )
                }

            }
        }
    }


    @Composable
    fun KeyBoardMaker(
        keyboardClick: (Int) -> Unit,
        exitRequest: () -> Unit,
        confirmPressed: () -> Unit
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
        ) {


            val buttonRefs = remember { mutableListOf<ConstrainedLayoutReference>() }

            for (i in 1..9) {
                buttonRefs.add(createRef())
            }

            val lineRef = createRef()


            Spacer(modifier = Modifier
                .constrainAs(lineRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(buttonRefs[1].bottom)
                }
            )


            for (i in 0..8) {
                Button(
                    onClick = { keyboardClick(i + 1) },
                    modifier = Modifier
                        .constrainAs(buttonRefs[i]) {
                            when (i) {
                                0 -> {
                                    top.linkTo(lineRef.bottom)
                                    bottom.linkTo(buttonRefs[4].top)
                                    start.linkTo(parent.start)
                                    end.linkTo(buttonRefs[1].start)
                                }
                                1 -> {
                                    top.linkTo(lineRef.bottom)
                                    bottom.linkTo(buttonRefs[4].top)
                                    start.linkTo(buttonRefs[0].end)
                                    end.linkTo(buttonRefs[2].start)
                                }
                                2 -> {
                                    top.linkTo(lineRef.bottom)
                                    bottom.linkTo(buttonRefs[4].top)
                                    start.linkTo(buttonRefs[1].end)
                                    end.linkTo(parent.end)
                                }
                                3 -> {
                                    top.linkTo(buttonRefs[1].bottom)
                                    bottom.linkTo(buttonRefs[7].top)
                                    start.linkTo(parent.start)
                                    end.linkTo(buttonRefs[4].start)
                                }
                                4 -> {
                                    top.linkTo(buttonRefs[1].bottom)
                                    bottom.linkTo(buttonRefs[7].top)
                                    start.linkTo(buttonRefs[3].end)
                                    end.linkTo(buttonRefs[5].start)
                                }
                                5 -> {
                                    top.linkTo(buttonRefs[1].bottom)
                                    bottom.linkTo(buttonRefs[7].top)
                                    start.linkTo(buttonRefs[4].end)
                                    end.linkTo(parent.end)
                                }
                                6 -> {
                                    top.linkTo(buttonRefs[4].bottom)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(buttonRefs[7].start)
                                }
                                7 -> {
                                    top.linkTo(buttonRefs[4].bottom)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(buttonRefs[6].end)
                                    end.linkTo(buttonRefs[8].start)
                                }
                                8 -> {
                                    top.linkTo(buttonRefs[4].bottom)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(buttonRefs[7].end)
                                    end.linkTo(parent.end)
                                }
                            }
                        }
                        .size(70.dp)
                        .border(6.dp, color = BtnBorder)
                ) {
                    Text(text = (i + 1).toString())
                }
            }

            val (confirmButton, cancelButton, exitButton) = createRefs()

            Button(
                onClick = { confirmPressed() },
                modifier = Modifier
                    .constrainAs(confirmButton) {
                        bottom.linkTo(parent.bottom)
                        top.linkTo(buttonRefs[7].bottom)
                        start.linkTo(exitButton.end)
                        end.linkTo(cancelButton.start)
                    }
                    .size(70.dp)
                    .border(6.dp, color = BtnBorder)
            ) {
                Text(text = "Confirm")
            }
            Button(
                onClick = { exitRequest() },
                modifier = Modifier
                    .constrainAs(exitButton) {
                        bottom.linkTo(parent.bottom)
                        top.linkTo(buttonRefs[6].bottom)
                        start.linkTo(parent.start)
                        end.linkTo(confirmButton.start)
                    }
                    .size(70.dp)
                    .border(6.dp, color = BtnBorder)
            ) {
                Text(text = "Exit")
            }
            Button(
                onClick = { keyboardClick(("".toInt())) },
                modifier = Modifier
                    .constrainAs(cancelButton) {
                        bottom.linkTo(parent.bottom)
                        top.linkTo(buttonRefs[8].bottom)
                        start.linkTo(confirmButton.end)
                        end.linkTo(parent.end)
                    }
                    .size(70.dp)
                    .border(6.dp, color = BtnBorder)
            ) {
                Text(text = "Canc")
            }
        }
    }


}