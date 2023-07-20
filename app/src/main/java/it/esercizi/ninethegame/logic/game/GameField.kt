package it.esercizi.ninethegame.logic.game

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.R
import it.esercizi.ninethegame.logic.FeedbackClass
import it.esercizi.ninethegame.logic.profile.ProfileClass
import it.esercizi.ninethegame.ui.theme.BtnBorder

class GameField {

    @Composable
    fun GameFieldMaker(
        game: GameClass,
        navController: NavHostController,
        confirmRequest: () -> Boolean,
        exitRequest: () -> Unit,
        askHint: () -> Unit,
        hintCost: Int,
        gameExit: () -> Unit,
        getTimerMin: () -> Int,
        getTimerSec: () -> Int,
        autoInsert: Boolean,
        profile: ProfileClass
    ) {

        val showDistance = remember {
            mutableStateOf(false)
        }

        val showAlert = remember {
            mutableStateOf(false)
        }

        if (showAlert.value) {
            ShowAlert()
            showAlert.value = false
        }

        val showFeedbackForm = remember {
            mutableStateOf(false)
        }

        if (showFeedbackForm.value) {
            FeedbackClass().ShowFeedbackForm {
                showFeedbackForm.value = false
            }
        } else {


            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                // Top Row
                Row(
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(10.dp)
                ) {
                    //Coin Balance
                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .border(2.dp, color = Color.DarkGray)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CurrencyBitcoin,
                            contentDescription = "Money"
                        )
                        Text(text = profile.money.value.toString())
                    }
                    Spacer(modifier = Modifier.weight(0.8f))
                    //Timer
                    Row() {
                        Text(
                            text = stringResource(R.string.Time) + ": " + getTimerMin() + ":" + getTimerSec(),
                            fontWeight = FontWeight.Bold, fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.8f))
                    //Hint button
                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .border(2.dp, color = Color.DarkGray)
                            .padding(5.dp)
                            .align(CenterVertically),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = CenterVertically
                    ) {
                        IconButton(onClick = { askHint() }) {
                            Icon(imageVector = Icons.Default.Lightbulb, contentDescription = "Hint")
                        }
                        Text(text = "Cost ")
                        Icon(
                            imageVector = Icons.Default.CurrencyBitcoin,
                            contentDescription = "Money"
                        )
                        Text(text = hintCost.toString())
                    }

                }
                // Squares Space
                Column(modifier = Modifier.weight(0.4f)) {


                    SquaresMaker(game, showDistance.value)
                    {
                        if (game.attempt.value <= 0) {
                            game.selectedSquareIndex.value = it
                        } else {
                            game.selectedSquareIndex.value = -1
                            game.selectedRetrySquareIndex.value = it
                        }
                    }

                }
                // Keyboard Space
                Column(
                    modifier = Modifier
                        .weight(0.7f)

                ) {

                    KeyBoardMaker(
                        game,
                        {

                            if (game.attempt.value <= 0) {
                                if (autoInsert) {
                                    if (game.selectedSquareIndex.value == -1) {
                                        game.selectedSquareIndex.value = 0
                                    }
                                }
                                if (game.selectedSquareIndex.value == -1) {
                                    showAlert.value = true
                                } else {
                                    game.squaresValues[game.selectedSquareIndex.value] = it
                                    if (it != "") game.selectedSquareIndex.value =
                                        (game.selectedSquareIndex.value + 1) % 9
                                }
                            } else {
                                if (autoInsert) {
                                    if (game.selectedRetrySquareIndex.value == -1) {
                                        game.selectedRetrySquareIndex.value = 0
                                    }
                                }
                                if (game.selectedRetrySquareIndex.value == -1) {
                                    showAlert.value = true
                                } else {
                                    game.retrySquaresValue[game.selectedRetrySquareIndex.value] = it
                                    if (it != "") game.selectedRetrySquareIndex.value =
                                        (game.selectedRetrySquareIndex.value + 1) % 9
                                }
                            }

                        },
                        exitRequest
                    ) {
                        if (confirmRequest()) {
                            game.selectedSquareIndex.value = -1
                            showDistance.value = true
                        }
                    }
                }

                //Bottom Bar
                BottomAppBar(
                    cutoutShape = CircleShape,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {

                    IconButton(onClick = { /* TODO */ }, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Help, contentDescription = "Help")
                    }
                    IconButton(
                        onClick = {
                            gameExit()
                            navController.navigate("settingsPage")
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                    IconButton(
                        onClick = { showFeedbackForm.value = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Feedback, contentDescription = "Feedback")

                    }
                }
            }
        }

    }


    @Composable
    fun SquaresMaker(
        game: GameClass,
        showDistance: Boolean,
        squareClick: (Int) -> Unit
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
        ) {

            val squareRefs = remember { mutableListOf<ConstrainedLayoutReference>() }

            for (i in 1..9) {
                squareRefs.add(createRef())
            }

            //Squares
            for (i in 0..8) {
                Text(
                    text = if (game.squaresValues[i] == "") {
                        ""
                    } else {
                        game.squaresSymbol[game.squaresValues[i].toInt()]
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = Color.Black,
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

                //Distance Squares
                for (i in 0..8) {
                    Text(
                        text = game.distanceVector[i],
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp,
                        color = Color.Black,
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

                //Retry Squares
                for (i in 0..8) {
                    Text(
                        text = if (game.retrySquaresValue[i] == "") {
                            ""
                        } else {
                            game.squaresSymbol[game.retrySquaresValue[i].toInt()]
                        },
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp,
                        color = Color.Black,
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
        game: GameClass,
        keyboardClick: (String) -> Unit,
        exitRequest: () -> Unit,
        confirmPressed: () -> Unit
    ) {
        //Keyboard Space
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Symbols Keyboard
            Column(
                modifier = Modifier
                    .weight(0.7f)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                        .weight(0.3f)
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    game.squaresSymbol.forEachIndexed { i, s ->
                        if (i in (0..2)) {
                            Button(
                                onClick = { keyboardClick(i.toString()) },
                                modifier = Modifier
                                    .weight(0.4f)
                                    .padding(10.dp)
                                    .border(6.dp, color = BtnBorder)
                            ) {
                                Text(text = s, fontSize = 20.sp)
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                        .weight(0.3f)
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    game.squaresSymbol.forEachIndexed { i, s ->
                        if (i in (3..5)) {
                            Button(
                                onClick = { keyboardClick(i.toString()) },
                                modifier = Modifier
                                    .weight(0.4f)
                                    .padding(10.dp)
                                    .border(6.dp, color = BtnBorder)
                            ) {
                                Text(text = s, fontSize = 20.sp)
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                        .weight(0.3f)
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    game.squaresSymbol.forEachIndexed { i, s ->
                        if (i in (6..8)) {
                            Button(
                                onClick = { keyboardClick(i.toString()) },
                                modifier = Modifier
                                    .weight(0.4f)
                                    .padding(10.dp)
                                    .border(6.dp, color = BtnBorder)
                            ) {
                                Text(text = s, fontSize = 20.sp)
                            }
                        }
                    }
                }
            }

            //Action Button
            Column(
                modifier = Modifier
                    .weight(0.35f)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    //.padding(5.dp),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { exitRequest() },
                        modifier = Modifier
                            .weight(0.3f)
                            .padding(10.dp)
                            .border(6.dp, color = BtnBorder)
                    ) {
                        Text(text = stringResource(R.string.Exit), fontSize = 20.sp)
                    }
                    Button(
                        onClick = { keyboardClick(("")) },
                        modifier = Modifier
                            .weight(0.3f)
                            .padding(10.dp)
                            .border(6.dp, color = BtnBorder)
                    ) {
                        Text(text = stringResource(R.string.CancelBtn), fontSize = 20.sp)
                    }
                    Button(
                        onClick = { confirmPressed() },
                        modifier = Modifier
                            .weight(0.3f)
                            .padding(10.dp)
                            .border(6.dp, color = BtnBorder)
                    ) {
                        Text(text = stringResource(R.string.EnterBtn), fontSize = 20.sp)
                    }
                }
            }
        }
    }


    @Composable
    fun ShowAlert() {

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(LocalContext.current)
            .setTitle(stringResource(R.string.Warning))
            .setMessage(stringResource(R.string.SelectSquareOrAutoInsertAlert))
            .setPositiveButton(stringResource(R.string.OkBtn)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()

    }


}