package it.esercizi.ninethegame.logic.profile

import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
class ProfileClass : Parcelable {

    @IgnoredOnParcel
    val nickname = mutableStateOf("Guest")

    @IgnoredOnParcel
    val money = mutableStateOf(100)

    @IgnoredOnParcel
    val firstAccess = mutableStateOf(true)



}