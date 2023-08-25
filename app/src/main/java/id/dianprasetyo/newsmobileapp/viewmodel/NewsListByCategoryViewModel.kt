package id.dianprasetyo.newsmobileapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.dianprasetyo.newsmobileapp.model.PostsItem

class NewsListByCategoryViewModel(application: Application) : ViewModel() {

    private var newsList : MutableList<PostsItem?> = mutableListOf()

    private val _currentNewsList = MutableLiveData<MutableList<PostsItem?>>()
    fun currentNewsList(): LiveData<MutableList<PostsItem?>> = _currentNewsList

   fun addNews(listNews: List<PostsItem?>?){
        newsList.addAll(listNews!!)
        _currentNewsList.value = newsList
    }

}

