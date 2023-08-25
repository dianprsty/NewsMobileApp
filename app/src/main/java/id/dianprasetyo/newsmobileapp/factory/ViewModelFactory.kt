package id.dianprasetyo.newsmobileapp.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.dianprasetyo.newsmobileapp.viewmodel.NewsDetailViewModel
import id.dianprasetyo.newsmobileapp.viewmodel.NewsListByCategoryViewModel
import id.dianprasetyo.newsmobileapp.viewmodel.NewsViewModel

class ViewModelFactory private constructor(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(application) as T
        } else if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
                return NewsDetailViewModel(application) as T
        } else if (modelClass.isAssignableFrom(NewsListByCategoryViewModel::class.java)) {
            return NewsListByCategoryViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}