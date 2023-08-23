package id.dianprasetyo.newsmobileapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.databinding.ActivityMainBinding
import id.dianprasetyo.newsmobileapp.factory.ViewModelFactory
import id.dianprasetyo.newsmobileapp.repository.SettingPreferences
import id.dianprasetyo.newsmobileapp.viewmodel.ThemeViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var themeViewModel: ThemeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        themeViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[ThemeViewModel::class.java]
        themeViewModel.getTheme().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        if (savedInstanceState == null){
            loadFragment(HomeFragment())
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                }
                R.id.menu_explore -> {
                    loadFragment(ExploreFragment())
                }
                R.id.menu_saved -> {
                    loadFragment(SavedFragment())
                }
                R.id.menu_setting -> {
                    loadFragment(SettingFragment())
                }

            }
            true
        }


    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}


