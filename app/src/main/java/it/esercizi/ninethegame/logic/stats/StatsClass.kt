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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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


        val resultsList = if (orderByData.value && !(filterByMode.value)){
            results.reversed()
        }else if(orderByData.value && filterByMode.value){
            results.reversed().sortedBy { it.gameMode }
        }else if(!orderByData.value && filterByMode.value){
            results.sortedBy { it.gameMode }
        }else{
            results
        }


        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(20.dp))

            //Page Title
            Row(
                modifier = Modifier
                    .weight(0.3f)
                    .align(CenterHorizontally)
            ) {
                Text(
                    text = "Game Stats",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h5
                )
            }

            Spacer(modifier = Modifier.weight(0.2f))
            Row(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(5.dp)
                    .weight(0.2f)
            ) {
                Button(onClick = { orderByData.value = !orderByData.value }, modifier = Modifier.background(BtnColor)) {
                    if(!orderByData.value){
                        Text(text = "Order by newest")
                    }else{
                        Text(text = "Order by oldest")
                    }
                }
                Button(onClick = { filterByMode.value = !filterByMode.value}, modifier = Modifier.background(BtnColor)) {
                    if(!filterByMode.value){
                        Text(text = "Group by Mode")
                    }else{
                        Text(text = "Without filter")
                    }
                }
            }

            //Lazy Column Space
            Column(
                modifier = Modifier
                    .weight(0.6f)
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
                        text = "Score",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.2f)
                    )
                    Text(
                        text = "Time",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.2f)
                    )
                    Text(
                        text = "Mode",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.2f)
                    )
                    Text(
                        text = "Date",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.2f)
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(5.dp)
                        .background(Color(85, 158, 207, 255))
                ) {
                    itemsIndexed(resultsList) { _, result ->
                        Row(modifier = Modifier.align(CenterHorizontally)) {

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

            Spacer(modifier = Modifier.weight(1f))

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
                    Text(text = "Clear Results")
                }



                Button(
                    onClick = { navController.navigate("main") },
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Text(text = "Exit")
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