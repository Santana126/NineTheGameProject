package it.esercizi.ninethegame.logic.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import it.esercizi.ninethegame.R
import it.esercizi.ninethegame.ui.theme.RowDarkerBackgroundDark
import it.esercizi.ninethegame.ui.theme.RowDarkerBackgroundLight
import it.esercizi.ninethegame.ui.theme.RowNormalBackgroundDark
import it.esercizi.ninethegame.ui.theme.RowNormalBackgroundLight

class SettingsInit {

    @Composable
    fun showSettingsPage(
        navController: NavHostController,
        settingsClass: SettingsClass,
        sharedPreferences: SharedPreferences,
        firstContext: Context
    ) {

        val settingsOption by mutableStateOf(
            listOf(
                "Gradient Background",
                "Auto Save",
                "Notification",
                "Auto Insert",
                "Timer on Screen"
            )
        )

        val settingsValueArray = remember {
            mutableStateListOf(
                settingsClass.backgroundGradient.value,
                settingsClass.autoSave.value,
                settingsClass.notification.value,
                settingsClass.autoInsert.value,
                settingsClass.timer.value
            )
        }


        //val selectedLanguage =  remember { mutableStateOf(settingsClass.language.value) }


        val context = LocalContext.current
/*
        val openLanguagePage = remember {
            mutableStateOf(false)
        }

 */

        val symbolProvider = SymbolProvider()

        val availableSymbol = symbolProvider.getAvailableTrailerSymbol()

        val symbolChoice = remember {
            settingsClass.symbolChoice
        }

        sharedPreferences.edit().putBoolean("backgroundGradient", settingsValueArray[0]).apply()
        sharedPreferences.edit().putBoolean("autoSave", settingsValueArray[1]).apply()
        sharedPreferences.edit().putBoolean("notification", settingsValueArray[2]).apply()
        sharedPreferences.edit().putBoolean("autoInsert", settingsValueArray[3]).apply()
        sharedPreferences.edit().putBoolean("timerOnScreen", settingsValueArray[4]).apply()


        sharedPreferences.edit().putInt("symbolChoice", symbolChoice.value).apply()

        //sharedPreferences.edit().putString("language", selectedLanguage.value).apply()


/*
        if (openLanguagePage.value) {
            //Language select Screen
            LanguageSettings().ShowLanguagePage(settingsClass,
                { openLanguagePage.value = false },
                {
                    settingsClass.language.value = settingsClass.languageAvailable[it]
                    selectedLanguage.value = settingsClass.languageAvailable[it]
                    openLanguagePage.value = false
                },
                { navController.navigate("main") })

        } else {

 */


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
                    .background(
                        if (isSystemInDarkTheme()) {
                            RowDarkerBackgroundDark
                        } else {
                            RowDarkerBackgroundLight
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.Settings),
                    modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            /*
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
                    text = stringResource(R.string.Language),
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

             */

            settingsOption.forEachIndexed { index, s ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 3.dp)
                        .padding(5.dp)
                        .height(30.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(
                            color = if (isSystemInDarkTheme()) {
                                RowNormalBackgroundDark
                            } else {
                                RowNormalBackgroundLight
                            }
                        ),
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
                    .background(
                        if (isSystemInDarkTheme()) {
                            RowDarkerBackgroundDark
                        } else {
                            RowDarkerBackgroundLight
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.SelectSymbol),
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
                        .background(
                            color = if (isSystemInDarkTheme()) {
                                RowNormalBackgroundDark
                            } else {
                                RowNormalBackgroundLight
                            }
                        ),
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
                        //selectedLanguage.value,
                        settingsClass,
                        context, navController, firstContext
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

        // }
    }


    private fun saveSettings(
        settingsValueArray: SnapshotStateList<Boolean>,
        symbolChoice: MutableState<Int>,
        //selectedLanguage: String,
        settingsClass: SettingsClass,
        context: Context,
        navController: NavHostController,
        firstContext: Context

    ) {

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.SettingsChanges))
            .setMessage(context.getString(R.string.ConfirmSettingsOrGoHome))
            .setPositiveButton(context.getString(R.string.OkBtn)) { dialog, _ ->
                //Saving selected value into settings class
                settingsClass.backgroundGradient.value = settingsValueArray[0]
                settingsClass.autoSave.value = settingsValueArray[1]
                settingsClass.notification.value = settingsValueArray[2]
                settingsClass.autoInsert.value = settingsValueArray[3]
                settingsClass.timer.value = settingsValueArray[4]


                settingsClass.symbolChoice.value = symbolChoice.value

                //settingsClass.language.value = selectedLanguage

                //changeLang(firstContext,settingsClass.language.value)

                //Go Home Page
                navController.navigate("main")
                dialog.dismiss()
            }
            //Discard Changes and go Home Page
            .setNegativeButton(context.getString(R.string.UndoChanges)) { dialog, _ ->

                navController.navigate("main")
                dialog.dismiss()

            }
            .create()

        alertDialog.show()

    }
/*
    fun changeLang(context: Context,language: String){

        val languageCode = when(language){
            "English" -> "en"
            else -> "it"
        }

        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)

        context.createConfigurationContext(configuration)

    }

 */
}


