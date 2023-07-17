package it.esercizi.ninethegame.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase


@Database(entities = [GameResult::class], version = 1)
abstract class DbGameResult: RoomDatabase() {
    abstract fun gameResultDao(): DaoGameResult

    companion object{
        private var db: DbGameResult? = null

        fun getInstance(context: Context): DbGameResult {
            if (db == null) {
                db = databaseBuilder(
                    context,
                    DbGameResult::class.java,
                    "newDbProva.db"
                )
                    .createFromAsset("newDbProva.db")
                    .build()
            }
            return db as DbGameResult
        }
    }

}