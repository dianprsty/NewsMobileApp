package id.dianprasetyo.newsmobileapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.get
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.api.APIConfig
import id.dianprasetyo.newsmobileapp.databinding.ActivityMainBinding
import id.dianprasetyo.newsmobileapp.factory.ThemeViewModelFactory
import id.dianprasetyo.newsmobileapp.factory.ViewModelFactory
import id.dianprasetyo.newsmobileapp.model.ResponseNews
import id.dianprasetyo.newsmobileapp.preferences.SettingPreferences
import id.dianprasetyo.newsmobileapp.viewmodel.NewsViewModel
import id.dianprasetyo.newsmobileapp.viewmodel.ThemeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var themeViewModel: ThemeViewModel
    private lateinit var newsViewModel : NewsViewModel
    private val listCategory = listOf<String>(
        "indonesia", "indonesia/politics", "indonesia/jakarta", "indonesia/society",
        "indonesia/archipelago", "business", "business/economy" , "business/tech", "business/companies",
        "business/regulations", "business/markets", "world", "world/asia-pacific", "world/americas",
        "world/europe", "world/middle-east-africa", "academia/opinion", "academia/editorial",
        "academia/insight", "academia/analysis", "academia", "academia/interview", "life",
        "life/style", "life/entertainment",
        "life/arts-culture", "life/science-tech", "life/people", "life/health", "life/parents",
        "life/food", "life/books", "life/environment"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        getCurrentTheme()

        newsViewModel = obtainViewModel(this@MainActivity)


        for(category in listCategory){
            fetchNews(category)
        }
        if (savedInstanceState == null){
            loadFragment(HomeFragment())

        }

        binding?.bottomNavigation?.setOnItemSelectedListener {

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
            .commit()
    }

    private fun getCurrentTheme(){
        val pref = SettingPreferences.getInstance(dataStore)
        themeViewModel =
            ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]
        themeViewModel.getTheme().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun fetchNews(path : String){
        APIConfig.getService().getNewsByCategory(path).enqueue(object :
            Callback<ResponseNews> {
            override fun onResponse(call: Call<ResponseNews>, response: Response<ResponseNews>) {
                if (response.isSuccessful) {
                    val responseNews = response.body()
                    val postsItem = responseNews?.posts
                    newsViewModel.addNews(postsItem)
                }
            }

            override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                Toast.makeText(applicationContext, "fetch data in $path failed", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): NewsViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[NewsViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}


