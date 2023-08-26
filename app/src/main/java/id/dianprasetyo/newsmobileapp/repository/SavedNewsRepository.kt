package id.dianprasetyo.newsmobileapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import id.dianprasetyo.newsmobileapp.room.SavedNews
import id.dianprasetyo.newsmobileapp.room.SavedNewsDao
import id.dianprasetyo.newsmobileapp.room.SavedNewsDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SavedNewsRepository(application: Application) {

    private val savedNewsDao: SavedNewsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = SavedNewsDatabase.getDatabase(application)
        savedNewsDao = db.savedNewsDao()
    }

    fun getAllSavedNews(): LiveData<List<SavedNews>> = savedNewsDao.getAllSavedNews()

    fun insertSavedNews(savedNews: SavedNews) {
        executorService.execute { savedNewsDao.insertSavedNews(savedNews) }
    }

    fun deleteSavedNews(savedNews: SavedNews) {
        executorService.execute { savedNewsDao.deleteSavedNews(savedNews) }
    }

}