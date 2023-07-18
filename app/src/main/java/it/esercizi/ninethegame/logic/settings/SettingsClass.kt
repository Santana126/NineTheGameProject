package it.esercizi.ninethegame.logic.settings

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
class SettingsClass : Parcelable {

    @IgnoredOnParcel
    val notification = mutableStateOf(false)

    @IgnoredOnParcel
    val darkMode = mutableStateOf(false)

    @IgnoredOnParcel
    val autoSave = mutableStateOf(true)

    @IgnoredOnParcel
    val music = mutableStateOf(true)

    @IgnoredOnParcel
    val autoInsert = mutableStateOf(false)

    @IgnoredOnParcel
    val symbolChoice = mutableStateOf(1)

    @IgnoredOnParcel
    val backgroundChoice = if(darkMode.value){
        mutableStateOf(1)
    }else{
        mutableStateOf(3)
    }

    @IgnoredOnParcel
    val languageAvailable = mutableStateListOf("Italian","English","French","Spanish")

    @IgnoredOnParcel
    val language = mutableStateOf("English")

}