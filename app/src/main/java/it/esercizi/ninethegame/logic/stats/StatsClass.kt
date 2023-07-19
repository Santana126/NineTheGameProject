package it.esercizi.ninethegame.logic.stats

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.R
import it.esercizi.ninethegame.db.DbGameResult
import it.esercizi.ninethegame.db.GameResult
import it.esercizi.ninethegame.db.Repository
import it.esercizi.ninethegame.ui.theme.BtnBorder
import it.esercizi.ninethegame.ui.theme.BtnColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StatsClass {

    @Composable
    fun StatsScreen(navController: NavHostController) {

        var results: List<GameResult> by remember { mutableStateOf(listOf()) }

        val context = LocalContext.current
        CoroutineScope(Dispatchers.IO).launch {
            delay(300)
            results = getGameResultsFromDatabase(context)
        }

        val orderByData = remember {
            mutableStateOf(false)
        }

        val filterByMode = remember {
            mutableStateOf(false)
        }


        val resultsList = if (orderByData.value && !(filterByMode.value)) {
            results.reversed()
        } else if (orderByData.value && filterByMode.value) {
            results.reversed().sortedBy { it.gameMode }
        } else if (!orderByData.value && filterByMode.value) {
            results.sortedBy { it.gameMode }
        } else {
            results
        }

        val showBestTime = remember {
            mutableStateOf(false)
        }






        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(20.dp))

            //Page Title
            Row(
                modifier = Modifier
                    .weight(0.2f)
                    .align(CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.GameStats),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h5
                )
            }

            Spacer(modifier = Modifier.weight(0.1f))

            //Lazy Column Space
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(10.dp)
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(5.dp)
                        .background(
                            Color(
                                133,
                                205,
                                255,
                                255
                            )
                        )
                ) {
                    Text(
                        text = stringResource(R.string.Score),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.2f)
                    )
                    Text(
                        text = stringResource(R.string.Time),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.2f)
                    )
                    Text(
                        text = stringResource(R.string.Mode),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.2f)
                    )
                    Text(
                        text = stringResource(R.string.Date),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.2f)
                    )
                }
                if (showBestTime.value) {

                    val bestTimeResult = results.minByOrNull { it.time }

                    if (bestTimeResult != null) {
                        Row(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .background(Color(85, 158, 207, 255))
                                .padding(5.dp)
                        ) {

                            Text(
                                text = bestTimeResult.score.toString(),
                                modifier = Modifier.weight(0.2f)
                            )
                            Text(
                                text = (((bestTimeResult.time) % 3600) / 60).toString() + ":" + ((bestTimeResult.time) % 60).toString(),
                                modifier = Modifier.weight(0.2f)
                            )
                            Text(
                                text = bestTimeResult.gameMode,
                                modifier = Modifier.weight(0.2f)
                            )
                            Text(
                                text = bestTimeResult.day + "/" + bestTimeResult.month + "/" + bestTimeResult.year.reversed()
                                    .take(2).reversed(),
                                modifier = Modifier.weight(0.2f)
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .background(Color(85, 158, 207, 255))
                        ) {
                            Text(text = stringResource(R.string.NoGameResult))
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(5.dp)
                            .weight(0.9f)
                    ) {
                        itemsIndexed(resultsList) { _, result ->
                            Row(
                                modifier = Modifier
                                    .align(CenterHorizontally)
                                    .background(Color(85, 158, 207, 255))
                            ) {

                                Text(
                                    text = result.score.toString(),
                                    modifier = Modifier.weight(0.2f)
                                )
                                Text(
                                    text = (((result.time) % 3600) / 60).toString() + ":" + ((result.time) % 60).toString(),
                                    modifier = Modifier.weight(0.2f)
                                )
                                Text(
                                    text = result.gameMode,
                                    modifier = Modifier.weight(0.2f)
                                )
                                Text(
                                    text = result.day + "/" + result.month + "/" + result.year.reversed()
                                        .take(2).reversed(),
                                    modifier = Modifier.weight(0.2f)
                                )
                            }
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .border(2.dp, color = BtnBorder)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(0.05f))
            Row(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(10.dp)
                    .weight(0.4f)
            ) {
                Button(onClick = { showBestTime.value = !showBestTime.value }) {
                    if (!showBestTime.value) {
                        Text(text = stringResource(R.string.ShowBestTimeResult))
                    } else {
                        Text(text = stringResource(R.string.ShowAllResult))
                    }
                }
            }

            // Data filters and sorting
            Row(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 10.dp, bottom = 10.dp, start = 25.dp, end = 25.dp)
                    .weight(0.4f)
            ) {
                Button(
                    onClick = { orderByData.value = !orderByData.value }, modifier = Modifier
                        .background(BtnColor)
                        .weight(0.4f)
                ) {
                    if (!orderByData.value) {
                        Text(text = stringResource(R.string.OrderNewest))
                    } else {
                        Text(text = stringResource(R.string.OrderOldest))
                    }
                }
                Spacer(modifier = Modifier.weight(0.3f))
                Button(
                    onClick = { filterByMode.value = !filterByMode.value }, modifier = Modifier
                        .background(BtnColor)
                        .weight(0.4f)
                ) {
                    if (!filterByMode.value) {
                        Text(text = stringResource(R.string.ModeGroupON))
                    } else {
                        Text(text = stringResource(R.string.ModeGroupOFF))
                    }
                }
            }

            Spacer(modifier = Modifier.weight(0.05f))

            //Button Space
            Row(
                modifier = Modifier
                    .weight(0.3f)
                    .align(CenterHorizontally)
            ) {
                Button(
                    onClick = { clearResultsFromDatabase(context) },
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Text(text = stringResource(R.string.ClearResults))
                }



                Button(
                    onClick = { navController.navigate("main") },
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Text(text = stringResource(R.string.Exit))
                }
            }
        }
    }

    private fun getGameResultsFromDatabase(current: Context): List<GameResult> {
        val db = DbGameResult.getInstance(current)
        val repository = Repository(db.gameResultDao())
        return repository.readAllGameResult()
    }

    private fun clearResultsFromDatabase(current: Context) {
        val db = DbGameResult.getInstance(current)
        val repository = Repository(db.gameResultDao())
        repository.clearData()
    }
}