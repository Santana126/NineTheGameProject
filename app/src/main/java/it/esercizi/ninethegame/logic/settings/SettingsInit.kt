package it.esercizi.ninethegame.logic.settings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

class SettingsInit {

    @Composable
    fun showSettingsPage(navController: NavHostController, settingsClass: SettingsClass) {

        //var isMenuExpanded by mutableStateOf(false)

        val settingsOption by mutableStateOf(
            listOf(
                "Dark Mode",
                "Auto Save",
                "Notification",
                "Music",
                "Auto Insert"
            )
        )

        //val settingsValue = remember { mutableStateListOf(false, true, false) }

        val settingsValueArray = remember {
            mutableStateListOf(
                settingsClass.darkMode.value,
                settingsClass.autoSave.value,
                settingsClass.notification.value,
                settingsClass.music.value,
                settingsClass.autoInsert.value
            )
        }

        var selectedOption by remember { mutableStateOf("") }
        val expanded = remember { mutableStateOf(false) }

        val languagesAvailable = listOf("Italiano", "Lingua1", "Lingua2", "Lingua3")


        val context = LocalContext.current


        Column(modifier = Modifier.fillMaxSize()) {
            /*
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = { isMenuExpanded = !isMenuExpanded }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false },
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            ) {
                DropdownMenuItem(onClick = { /* TODO */ }) {
                    Text(text = "Option 1")
                }
                DropdownMenuItem(onClick = { /* TODO */ }) {
                    Text(text = "Option 2")
                }
            }

             */

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                    .padding(5.dp)
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.Gray),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Settings",
                    modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),
                    fontWeight = FontWeight.Bold
                )
            }
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
                Text(
                    text = "Language",
                    modifier = Modifier
                        .weight(1f)
                        .padding(3.dp)
                )
                Icon(
                    imageVector = Icons.Default.ArrowRight,
                    contentDescription = "Freccia",
                    modifier = Modifier.clickable { expanded.value = true }
                )

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },

                    ) {
                    languagesAvailable.forEach { language ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = language
                                expanded.value = false
                            }
                        ) {
                            Text(text = language)
                        }
                    }
                }

            }

            settingsOption.forEachIndexed { index, s ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                        .padding(5.dp)
                        .height(30.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(color = Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = s,
                        modifier = Modifier
                            .weight(1f)
                            .padding(3.dp)
                    )
                    Switch(
                        checked = settingsValueArray[index],
                        onCheckedChange = { settingsValueArray[index] = it },
                        modifier = Modifier.padding(3.dp)
                    )
                }

            }

            val symbolProvider = SymbolProvider()

            val availableSymbol = symbolProvider.getAvailableTrailerSymbol()

            val symbolChoice = remember {
                settingsClass.symbolChoice
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                    .padding(5.dp)
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.Gray),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Symbol Choice",
                    modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            availableSymbol.forEachIndexed { index, strings ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                        .padding(5.dp)
                        .height(30.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(color = Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = strings.toString(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(3.dp)
                    )
                    RadioButton(
                        selected = (symbolChoice.value == index + 1),
                        onClick = { symbolChoice.value = index + 1 })
                }
            }
/*
            val backgroundChoice = remember {
                settingsClass.backgroundChoice
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                    .padding(5.dp)
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.LightGray),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gradient_backgrounds_b1),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                RadioButton(
                    selected = (backgroundChoice.value == 1),
                    onClick = { backgroundChoice.value = 1 })

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                    .padding(5.dp)
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.LightGray),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gradient_backgrounds_b2),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                RadioButton(
                    selected = (backgroundChoice.value == 2),
                    onClick = { backgroundChoice.value = 2 })

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                    .padding(5.dp)
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.LightGray),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gradient_backgrounds_b3),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                RadioButton(
                    selected = (backgroundChoice.value == 3),
                    onClick = { backgroundChoice.value = 3 })

            }

 */

            Spacer(modifier = Modifier.weight(1f))
            /*
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = {
                        saveSettings(
                            settingsValueArray,
                            symbolChoice,
                            settingsClass,
                            //backgroundChoice,
                            context
                        )
                    },
                    modifier = Modifier

                        .padding(16.dp)
                ) {
                    Text(text = "Save")
                }
                Button(
                    onClick = { navController.navigate("main") },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(text = "Exit")
                }

             */
            BottomAppBar(
                cutoutShape = CircleShape,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
/*
                IconButton(onClick = { showRules.value = true }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Help, contentDescription = "Help")
                }

                IconButton(
                    onClick = { navController.navigate("settingsPage") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }

 */
                IconButton(onClick = {
                    saveSettings(
                        settingsValueArray,
                        symbolChoice,
                        settingsClass,
                        //backgroundChoice,
                        context, navController
                    )
                }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Home, contentDescription = "Home")

                }
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Feedback, contentDescription = "Feedback")

                }
            }

        }
    }


    private fun saveSettings(
        settingsValueArray: SnapshotStateList<Boolean>,
        symbolChoice: MutableState<Int>,
        settingsClass: SettingsClass,
        //backgroundChoice: MutableState<Int>,
        context: Context,
        navController: NavHostController,

        ) {


        //settingsClass.backgroundChoice.value = backgroundChoice.value

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(context)
            .setTitle("Settings Changes")
            .setMessage("Confirm Setting changes or return home")
            .setPositiveButton("Ok") { dialog, _ ->
                // Azioni da eseguire quando si preme il pulsante OK
                settingsClass.darkMode.value = settingsValueArray[0]
                settingsClass.autoSave.value = settingsValueArray[1]
                settingsClass.notification.value = settingsValueArray[2]
                settingsClass.music.value = settingsValueArray[3]

                settingsClass.symbolChoice.value = symbolChoice.value

                navController.navigate("main")
                dialog.dismiss() // Chiude l'AlertDialog
            }
            .setNegativeButton("Undo Changes") { dialog, _ ->

                navController.navigate("main")
                dialog.dismiss()

            }
            .create()

        alertDialog.show()

    }
}


