package id.dianprasetyo.newsmobileapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.dianprasetyo.newsmobileapp.repository.SettingPreferences
import kotlinx.coroutines.launch

class ThemeViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getTheme(): LiveData<Boolean> {
        return pref.getTheme().asLiveData()
    }

    //Coroutine Scope untuk save tema
    fun saveTheme(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveTheme(isDarkModeActive)
        }
    }

}

