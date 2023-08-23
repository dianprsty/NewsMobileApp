package id.dianprasetyo.newsmobileapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.dianprasetyo.newsmobileapp.repository.SettingPreferences
import id.dianprasetyo.newsmobileapp.viewmodel.ThemeViewModel

class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((ThemeViewModel::class.java))) {
            return ThemeViewModel(pref) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}