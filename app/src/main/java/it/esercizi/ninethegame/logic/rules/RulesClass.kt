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
                    //.padding(10.dp)
            ) {
                Text(
                    "Rules",
                    modifier = Modifier.padding(top = 10.dp).weight(0.1f),
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(modifier = Modifier.weight(0.7f).padding(10.dp)) {

                    item {
                        Text(
                            text = "Lo scopo del gioco è quello di indovinare una combinazione segreta di nove simboli.\n"
                                    + "Inserire nei riquadri una combinazione di simboli tentando di indovinare la posizione di più simboli possibili.\n"
                                    + "Una volta riempiti i riquadri con i simboli puoi confermare la tua scelta. (Ricorda di inserire tutti i simboli necessari e fai in modo che essi siano diversi tra loro)\n"
                                    + "Dopo aver confermato il tuo codice, ti verrà mostrata per ogni simbolo, la distanza tra la sua posizione e quella in cui si trova all'interno della combinazione segreta\n"
                                    + "Attenzione: la distanza può indicare il numero di riquadri sia verso destra che verso sinistra, inoltre se la distanza è maggiore del numero di riquadri disponibili in quella posizione, puoi continuare contando dall'altro lato dei riquadri"
                                    + "Puoi capire meglio guardando l'esempio qui sotto o giocando il tutorial"
                                    + "Tramite queste informazioni dovrai capire dove si trovano i simboli nella combinazione segreta, fare le tue scelte e confermare ancora il codice.\n"
                                    + "Se non ti è possibile indovinare la posizione dei simboli non ti preoccupare. È presente in alto a destra un bottone con cui chiedere un aiuto, esso ti indicherà la posizione corretta di un simbolo e lo inserirà in automatico all'interno della combinazione.\n"
                                    + "Nella modalità Play avrai un solo tentativo dopo aver visto la distanza tra i simboli e il tuo scopo sarà quello di indovinare la combinazione nel minor tempo possibile.\n"
                                    + "Nella modalità Training potrai allenarti e la partità finirà quando avrai indovinato la combinazione segreta. Non avrai un limite di tentativi, perciò qui il tuo scopo è quello di impiegare il minor numero di tentetivi."
                        )
                    }
                    item {
                        Text(text = "Nella barra in basso è presente la pagina delle impostazioni dove puoi modificare alcuni aspetti del gioco, e la pagina dedicata ai feedback dove puoi comunicare problemi o miglioramenti sull'applicazione\n")
                    }
                }
                //Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.background(color = Color.DarkGray).weight(0.1f)) {
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