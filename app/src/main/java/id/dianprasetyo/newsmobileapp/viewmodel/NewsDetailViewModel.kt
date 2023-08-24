package id.dianprasetyo.newsmobileapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.dianprasetyo.newsmobileapp.model.DetailPost

class NewsDetailViewModel(application: Application) : ViewModel(){
    private var newsDetail : DetailPost = DetailPost()

    private val _currentNewsDetail = MutableLiveData<DetailPost>()
    fun currentNewsDetail(): LiveData<DetailPost> = _currentNewsDetail

    fun setNewsDetail(newsDetailPost: DetailPost){
        newsDetail = newsDetailPost
        _currentNewsDetail.value = newsDetail
    }
}