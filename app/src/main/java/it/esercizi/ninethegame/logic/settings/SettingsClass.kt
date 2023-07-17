package it.esercizi.ninethegame.logic.settings

import android.os.Parcelable
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
    val symbolChoice = mutableStateOf(1)

    @IgnoredOnParcel
    val backgroundChoice = mutableStateOf(1)

}