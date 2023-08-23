package id.dianprasetyo.newsmobileapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())

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


