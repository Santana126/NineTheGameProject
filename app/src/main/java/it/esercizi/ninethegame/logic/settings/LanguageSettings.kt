package it.esercizi.ninethegame.logic.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import it.esercizi.ninethegame.R

class LanguageSettings {

    @Composable
    fun ShowLanguagePage(
        settings: SettingsClass,
        onClose: () -> Unit,
        saveRequest: (Int) -> Unit,
        goHome: () -> Unit
    ) {

        val choice = mutableStateOf(settings.language.value)

        var langToIndex = 0

        when (choice.value) {
            "Italian" -> langToIndex = 0
            "English" -> langToIndex = 1
            "French" -> langToIndex = 2
            "Spanish" -> langToIndex = 3
        }


        val choiceIndex = remember {
            mutableStateOf(langToIndex)
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                    .padding(5.dp)
                    .weight(0.3f)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.Gray),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.SelectLanguage),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.h4,
                    fontFamily = FontFamily.SansSerif
                )
            }
            LazyColumn(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(10.dp)
            ) {
                settings.languageAvailable.forEachIndexed { index, s ->
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                                .padding(5.dp)
                                .height(30.dp)
                                .align(Alignment.CenterHorizontally)
                                .background(color = Color.LightGray),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(text = s, modifier = Modifier.weight(1f))
                            RadioButton(
                                selected = (choiceIndex.value == index),
                                onClick = { choiceIndex.value = index })
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            BottomAppBar(
                cutoutShape = CircleShape,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                IconButton(onClick = { onClose() }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                IconButton(onClick = { goHome() }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(
                    onClick = { saveRequest(choiceIndex.value) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Save, contentDescription = "Save")
                }
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Feedback, contentDescription = "Feedback")
                }
            }

        }


    }


}