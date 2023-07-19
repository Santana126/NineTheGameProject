package it.esercizi.ninethegame.logic.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Home
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
    fun showSettingsPage(
        navController: NavHostController,
        settingsClass: SettingsClass,
        sharedPreferences: SharedPreferences
    ) {

        val settingsOption by mutableStateOf(
            listOf(
                "Dark Mode",
                "Auto Save",
                "Notification",
                "Music",
                "Auto Insert"
            )
        )

        val settingsValueArray = remember {
            mutableStateListOf(
                settingsClass.darkMode.value,
                settingsClass.autoSave.value,
                settingsClass.notification.value,
                settingsClass.music.value,
                settingsClass.autoInsert.value
            )
        }



        var selectedLanguage by remember { mutableStateOf("") }

        val context = LocalContext.current

        val openLanguagePage = remember {
            mutableStateOf(false)
        }

        val symbolProvider = SymbolProvider()

        val availableSymbol = symbolProvider.getAvailableTrailerSymbol()

        val symbolChoice = remember {
            settingsClass.symbolChoice
        }

        sharedPreferences.edit().putBoolean("darkMode", settingsValueArray[0]).apply()
        sharedPreferences.edit().putBoolean("autoSave", settingsValueArray[0]).apply()
        sharedPreferences.edit().putBoolean("notification", settingsValueArray[2]).apply()
        sharedPreferences.edit().putBoolean("music", settingsValueArray[3]).apply()
        sharedPreferences.edit().putBoolean("autoInsert", settingsValueArray[4]).apply()

        sharedPreferences.edit().putInt("symbolChoice", symbolChoice.value).apply()

        sharedPreferences.edit().putString("language", selectedLanguage).apply()



        if (openLanguagePage.value) {
            //Language select Screen
            LanguageSettings().ShowLanguagePage(settingsClass,
                { openLanguagePage.value = false },
                {
                    selectedLanguage = settingsClass.languageAvailable[it]
                    openLanguagePage.value = false
                },
                { navController.navigate("main") })

        } else {

            //Settings Screen
            Column(modifier = Modifier.fillMaxSize()) {

                Spacer(modifier = Modifier.height(16.dp))
                //System settings
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
                        modifier = Modifier.clickable { openLanguagePage.value = true }
                    )
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



                //Symbol select
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
                        text = "Select Symbol",
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

                Spacer(modifier = Modifier.weight(1f))
                //Bottom Bar
                BottomAppBar(
                    cutoutShape = CircleShape,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    //Save and Exit
                    IconButton(onClick = {
                        saveSettings(
                            settingsValueArray,
                            symbolChoice,
                            settingsClass,
                            context, navController
                        )
                    }, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Home, contentDescription = "Home")

                    }
                    //Feedback
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Feedback, contentDescription = "Feedback")

                    }
                }
            }

        }
    }


    private fun saveSettings(
        settingsValueArray: SnapshotStateList<Boolean>,
        symbolChoice: MutableState<Int>,
        settingsClass: SettingsClass,
        context: Context,
        navController: NavHostController,

        ) {

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(context)
            .setTitle("Settings Changes")
            .setMessage("Confirm Setting changes or return home without saving")
            .setPositiveButton("Ok") { dialog, _ ->
                //Saving selected value into settings class
                settingsClass.darkMode.value = settingsValueArray[0]
                settingsClass.autoSave.value = settingsValueArray[1]
                settingsClass.notification.value = settingsValueArray[2]
                settingsClass.music.value = settingsValueArray[3]

                settingsClass.symbolChoice.value = symbolChoice.value

                //Go Home Page
                navController.navigate("main")
                dialog.dismiss()
            }
                //Discard Changes and go Home Page
            .setNegativeButton("Undo Changes") { dialog, _ ->

                navController.navigate("main")
                dialog.dismiss()

            }
            .create()

        alertDialog.show()

    }
}


