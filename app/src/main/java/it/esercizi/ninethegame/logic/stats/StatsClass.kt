package it.esercizi.ninethegame.logic.stats

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.db.DbGameResult
import it.esercizi.ninethegame.db.GameResult
import it.esercizi.ninethegame.db.Repository
import it.esercizi.ninethegame.ui.theme.BtnBorder
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


        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
        ) {

            val lazyCol = createRef()
            val title = createRef()
            val (btnOne, btnTwo) = createRefs()

            Text(
                text = "Titolo Pagina",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(lazyCol.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )


            LazyColumn(
                modifier = Modifier
                    .constrainAs(lazyCol) {
                        top.linkTo(title.bottom)
                        bottom.linkTo(btnOne.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .height(600.dp)
                    .padding(15.dp)
                    .padding(10.dp)
                    .border(4.dp, color = BtnBorder)
            ) {

                itemsIndexed(results){_, result ->
                    Text(text = "Score: " + result.score + " | Date: " + result.day + "/" + result.month + "/" + result.year,
                    modifier = Modifier.padding(5.dp))
                    Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).border(2.dp,color = BtnBorder))
                }
            }

            Button(
                onClick = { clearResultsFromDatabase(context) },
                modifier = Modifier
                    .padding(20.dp)
                    .constrainAs(btnOne) {
                        top.linkTo(lazyCol.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(btnTwo.start)
                    }
            ) {
                Text(text = "Clear Results")
            }



            Button(
                onClick = { navController.navigate("main") },
                modifier = Modifier
                    .padding(20.dp)
                    .constrainAs(btnTwo) {
                        top.linkTo(lazyCol.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(btnOne.end)
                        end.linkTo(parent.end)
                    }
            ) {
                Text(text = "Exit")
            }

        }
    }

    private fun getGameResultsFromDatabase(current: Context): List<GameResult> {
        val db = DbGameResult.getInstance(current)
        val repository = Repository(db.gameResultDao())
        return repository.readAllGameResult()
    }

    private fun clearResultsFromDatabase(current: Context){
        val db = DbGameResult.getInstance(current)
        val repository = Repository(db.gameResultDao())
        repository.clearData()
    }
}