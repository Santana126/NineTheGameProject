package it.esercizi.ninethegame.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DaoGameResult {


    @Insert
    suspend fun insert(gameResult: GameResult)



    @Query("SELECT * FROM Results")
    fun readAllGameResults(): List<GameResult>

    @Query("DELETE FROM Results")
    suspend fun clearData()

}