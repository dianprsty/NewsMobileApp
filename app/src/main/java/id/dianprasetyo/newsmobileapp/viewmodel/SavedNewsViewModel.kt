package id.dianprasetyo.newsmobileapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.dianprasetyo.newsmobileapp.model.DetailPost
import id.dianprasetyo.newsmobileapp.repository.SavedNewsRepository
import id.dianprasetyo.newsmobileapp.room.SavedNews

class SavedNewsViewModel(application: Application) : ViewModel() {
    private val repository: SavedNewsRepository = SavedNewsRepository(application)

    private val _currentSavedNews = MutableLiveData<List<SavedNews>>()
    fun currentSavedNews(): LiveData<List<SavedNews>> = _currentSavedNews

    fun setSavedNews(savedNews: List<SavedNews>){
        _currentSavedNews.value = savedNews
    }

    fun getAllSavedNews(): LiveData<List<SavedNews>> = repository.getAllSavedNews()

    fun deleteSavedNews(savedNews: SavedNews) {
        repository.deleteSavedNews(savedNews)
    }
}