package it.esercizi.ninethegame.db

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(private val dao: DaoGameResult) {
    fun insert(gameResult: GameResult){
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(gameResult)
        }
    }

    fun readAllGameResult(): List<GameResult> {
        return dao.readAllGameResults()
    }

    fun clearData(){
        CoroutineScope(Dispatchers.IO).launch {
            dao.clearData()
        }
    }
}