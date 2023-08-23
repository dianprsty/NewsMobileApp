package id.dianprasetyo.newsmobileapp.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val key = booleanPreferencesKey("theme_setting")

    fun getTheme(): Flow<Boolean> {
        return dataStore.data.map { preferences -> preferences[key] ?: false }
    }

    //fungsi asinkron untuk save tema
    suspend fun saveTheme(isDarkModeActive: Boolean) {
        dataStore.edit { preferences -> preferences[key] = isDarkModeActive }
    }

    //Singleton Patter
    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}