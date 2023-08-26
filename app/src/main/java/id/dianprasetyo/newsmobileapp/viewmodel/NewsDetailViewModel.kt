package id.dianprasetyo.newsmobileapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.dianprasetyo.newsmobileapp.model.DetailPost
import id.dianprasetyo.newsmobileapp.repository.SavedNewsRepository
import id.dianprasetyo.newsmobileapp.room.SavedNews

class NewsDetailViewModel(application: Application) : ViewModel(){
    private var newsDetail : DetailPost = DetailPost()
    private val repository: SavedNewsRepository = SavedNewsRepository(application)

    private val _currentNewsDetail = MutableLiveData<DetailPost>()
    fun currentNewsDetail(): LiveData<DetailPost> = _currentNewsDetail

    fun getAllSavedNews(): LiveData<List<SavedNews>> = repository.getAllSavedNews()

    fun setNewsDetail(newsDetailPost: DetailPost){
        newsDetail = newsDetailPost
        _currentNewsDetail.value = newsDetail
    }

    fun insertSavedNews(savedNews: SavedNews) {
        repository.insertSavedNews(savedNews)
    }

    fun deleteSavedNews(savedNews: SavedNews) {
        repository.deleteSavedNews(savedNews)
    }

}