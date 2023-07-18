package it.esercizi.ninethegame.logic.rules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class RulesClass {


    @Composable
    fun ShowRules(onClose: () -> Unit) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(color = Color.Gray)
                    .height(500.dp)
                    .width(300.dp)
                    .border(3.dp, color = Color.Black)
                    .padding(10.dp)
                    .padding(10.dp)
            ) {
                Text(
                    "Rules",
                    modifier = Modifier.padding(top = 10.dp),
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.height(20.dp))
                LazyColumn {

                    item {
                        Text(
                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis scelerisque placerat ornare. Duis ut dignissim augue. Suspendisse efficitur varius lectus eu egestas. Praesent tortor libero, viverra nec nisl et, porttitor laoreet quam. In pharetra dignissim turpis, in sagittis mi semper sed. Praesent placerat hendrerit nunc, at maximus ligula pellentesque vitae. Curabitur massa urna, consequat non arcu dignissim, gravida auctor velit. Nunc fermentum sem magna, eu hendrerit enim convallis a.\n" +
                                    "\n"
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row() {
                    Button(onClick = onClose, modifier = Modifier.padding(5.dp)) {
                        Text("Play the Tutorial")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = onClose, modifier = Modifier.padding(5.dp)) {
                        Text("OK!")
                    }
                }

            }
        }
    }


}