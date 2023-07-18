package it.esercizi.ninethegame.logic.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
        confirmRequest: () -> Boolean,
        exitRequest: () -> Unit,
        askHint: () -> Unit
    ) {

        val showDistace = remember {
            mutableStateOf(false)
        }

        val showAlert = remember {
            mutableStateOf(false)
        }

        if (showAlert.value) {
            ShowAlert()
            showAlert.value = false
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            /*
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    //.fillMaxHeight()
                    .padding(10.dp)
                //.padding(20.dp)
            ) {

             */
            //val hintBtn = createRef()


            Row(modifier = Modifier.height(30.dp)) {
                Spacer(modifier = Modifier.weight(0.8f))
                IconButton(onClick = { askHint() }) {
                    Icon(imageVector = Icons.Default.Lightbulb, contentDescription = "Hint")
                }

            }
            // Squares Space
            Column(modifier = Modifier.weight(0.4f)) {


                SquaresMaker(game, showDistace.value)
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
                            if (game.selectedSquareIndex.value == -1) {
                                showAlert.value = true
                            } else {
                                game.squaresValues[game.selectedSquareIndex.value] = it
                                if (it != "") game.selectedSquareIndex.value =
                                    (game.selectedSquareIndex.value + 1) % 9
                            }
                        } else {
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
                        showDistace.value = true
                    }

                }
            }


            //Spacer(modifier = Modifier.weight(0.3f))

            BottomAppBar(
                cutoutShape = CircleShape,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {

                IconButton(onClick = { /* TODO */ }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Help, contentDescription = "Help")
                }
                IconButton(
                    onClick = { navController.navigate("settingsPage") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Feedback, contentDescription = "Feedback")

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
                //.fillMaxHeight()
                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
        ) {

            val squareRefs = remember { mutableListOf<ConstrainedLayoutReference>() }

            for (i in 1..9) {
                squareRefs.add(createRef())
            }

            for (i in 0..8) {
                Text(
                    //text = game.squaresValues[i],
                    text = if (game.squaresValues[i] == "") {
                        ""
                    } else {
                        game.squaresSymbol[game.squaresValues[i].toInt()]
                    },
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
                        text = if (game.retrySquaresValue[i] == "") {
                            ""
                        } else {
                            game.squaresSymbol[game.retrySquaresValue[i].toInt()]
                        },
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
        game: GameClass,
        keyboardClick: (String) -> Unit,
        exitRequest: () -> Unit,
        confirmPressed: () -> Unit
    ) {

/*
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 10.dp, end = 10.dp)
            ) {

 */

/*
                val buttonRefs = remember { mutableListOf<ConstrainedLayoutReference>() }
                val (confirmButton, cancelButton, exitButton) = createRefs()

                for (i in 1..9) {
                    buttonRefs.add(createRef())
                }

 */

        Column(
            modifier = Modifier
                .fillMaxSize()
            //.background(Color.Gray)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    //.background(Color.Red)
            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                        .weight(0.3f)
                        //.padding(5.dp)
                        //.height(30.dp)
                        .align(Alignment.CenterHorizontally),
                        //.background(color = Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
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
                                Text(text = s)
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                        .weight(0.3f)

                        //.padding(5.dp)
                        //.height(30.dp)
                        .align(Alignment.CenterHorizontally),
                        //.background(color = Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    game.squaresSymbol.forEachIndexed { i, s ->
                        if (i in (3..5)) {
                            Button(
                                onClick = { keyboardClick(i.toString()) },
                                modifier = Modifier
                                    //.size(70.dp)
                                    .weight(0.4f)
                                    .padding(10.dp)
                                    .border(6.dp, color = BtnBorder)
                            ) {
                                Text(text = s)
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                        .weight(0.3f)

                        //.padding(5.dp)
                        //.height(30.dp)
                        .align(Alignment.CenterHorizontally),
                        //.background(color = Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    game.squaresSymbol.forEachIndexed { i, s ->
                        if (i in (6..8)) {
                            Button(
                                onClick = { keyboardClick(i.toString()) },
                                modifier = Modifier
                                    //.size(70.dp)
                                    .weight(0.4f)
                                    .padding(10.dp)
                                    .border(6.dp, color = BtnBorder)
                            ) {
                                Text(text = s)
                            }
                        }
                    }
                }
            }


/*
                game.squaresSymbol.forEachIndexed { i, s ->
                    Button(
                        onClick = { keyboardClick(i.toString()) },
                        modifier = Modifier
                            .padding(top = 10.dp)
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
                                        bottom.linkTo(keyboardDivider.top)//bottom.linkTo(cancelButton.top)
                                        start.linkTo(parent.start)
                                        end.linkTo(buttonRefs[7].start)
                                    }
                                    7 -> {
                                        top.linkTo(buttonRefs[4].bottom)
                                        bottom.linkTo(keyboardDivider.top)//bottom.linkTo(cancelButton.top)
                                        start.linkTo(buttonRefs[6].end)
                                        end.linkTo(buttonRefs[8].start)
                                    }
                                    8 -> {
                                        top.linkTo(buttonRefs[4].bottom)
                                        bottom.linkTo(keyboardDivider.top)//bottom.linkTo(cancelButton.top)
                                        start.linkTo(buttonRefs[7].end)
                                        end.linkTo(parent.end)
                                    }
                                }
                            }
                            .size(70.dp)
                            .border(6.dp, color = BtnBorder)
                    ) {
                        Text(text = s)
                    }
                }

 */


/*
                Spacer(
                    modifier = Modifier
                        .constrainAs(keyboardDivider) {
                            top.linkTo(buttonRefs[7].bottom)
                            bottom.linkTo(cancelButton.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .height(30.dp)
                )

 */

            Column(
                modifier = Modifier
                    .weight(0.3f)
                    //.background(Color.Green)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { exitRequest() },
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(0.3f)
                            .border(6.dp, color = BtnBorder)
                    ) {
                        Text(text = "Exit")
                    }
                    Button(
                        onClick = { keyboardClick(("")) },
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(0.3f)
                            .border(6.dp, color = BtnBorder)
                    ) {
                        Text(text = "Canc")
                    }
                    Button(
                        onClick = { confirmPressed() },
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(0.3f)
                            .border(6.dp, color = BtnBorder)
                    ) {
                        Text(text = "OK")
                    }
                }
            }


/*
                Button(
                    onClick = { keyboardClick(("")) },
                    modifier = Modifier
                        //.padding(10.dp)
                        .constrainAs(cancelButton) {
                            bottom.linkTo(parent.bottom)
                            //top.linkTo(buttonRefs[7].bottom)
                            top.linkTo(keyboardDivider.bottom)
                            start.linkTo(exitButton.end)
                            end.linkTo(confirmButton.start)
                        }
                        .size(70.dp)
                        .border(6.dp, color = BtnBorder)
                ) {
                    Text(text = "Canc")
                }
                Button(
                    onClick = { exitRequest() },
                    modifier = Modifier
                        //.padding(10.dp)
                        .constrainAs(exitButton) {
                            bottom.linkTo(parent.bottom)
                            top.linkTo(keyboardDivider.bottom)//top.linkTo(buttonRefs[7].bottom)
                            start.linkTo(parent.start)
                            end.linkTo(cancelButton.start)
                        }
                        .size(70.dp)
                        .border(6.dp, color = BtnBorder)
                ) {
                    Text(text = "Exit")
                }
                Button(
                    onClick = { confirmPressed() },
                    modifier = Modifier
                        //.padding(10.dp)
                        .constrainAs(confirmButton) {
                            bottom.linkTo(parent.bottom)
                            top.linkTo(keyboardDivider.bottom)//top.linkTo(buttonRefs[7].bottom)
                            start.linkTo(cancelButton.end)
                            end.linkTo(parent.end)
                        }
                        .size(70.dp)
                        .border(6.dp, color = BtnBorder)
                ) {
                    Text(text = "OK")
                }

 */

        }
    }


    @Composable
    fun ShowAlert() {

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(LocalContext.current)
            .setTitle("Warning")
            .setMessage("First select the square")
            .setPositiveButton("Ok") { dialog, _ ->
                // Azioni da eseguire quando si preme il pulsante OK

                dialog.dismiss() // Chiude l'AlertDialog
            }
            .create()

        alertDialog.show()

    }


}