package it.esercizi.ninethegame.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Results")
data class GameResult(
    var score: Int,
    var day: String,
    var month: String,
    var year: String,
    //Game time in seconds
    var time: Int,
    var gameMode: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}