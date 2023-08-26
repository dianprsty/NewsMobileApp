package id.dianprasetyo.newsmobileapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.api.APIConfig
import id.dianprasetyo.newsmobileapp.databinding.ActivityNewsDetailBinding
import id.dianprasetyo.newsmobileapp.factory.ThemeViewModelFactory
import id.dianprasetyo.newsmobileapp.factory.ViewModelFactory
import id.dianprasetyo.newsmobileapp.model.DetailPost
import id.dianprasetyo.newsmobileapp.model.ResponseNewsDetail
import id.dianprasetyo.newsmobileapp.preferences.SettingPreferences
import id.dianprasetyo.newsmobileapp.room.SavedNews
import id.dianprasetyo.newsmobileapp.viewmodel.NewsDetailViewModel
import id.dianprasetyo.newsmobileapp.viewmodel.ThemeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class NewsDetailActivity : AppCompatActivity() {

    private var binding : ActivityNewsDetailBinding? = null
    private lateinit var themeViewModel: ThemeViewModel
    private lateinit var newsDetailViewModel: NewsDetailViewModel
    private var savedNews : SavedNews? = null
    private var savedNewsId : Int = 0
    companion object{
        const val EXTRA_URL = "extra_url"
        const val EXTRA_HEADLINE = "extra_headline"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getCurrentTheme()

        val fullPath = intent.getStringExtra(EXTRA_URL)
        val headline = intent.getStringExtra(EXTRA_HEADLINE)

        val path : String = fullPath!!.removePrefix("https://jakpost.vercel.app/api/detailpost/")
        showLoading(true)
        newsDetailViewModel = obtainViewModel(this)
        APIConfig.getService().getNewsDetail(path).enqueue(object :
            Callback<ResponseNewsDetail> {
            override fun onResponse(call: Call<ResponseNewsDetail>, response: Response<ResponseNewsDetail>) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseNewsDetail = response.body()
                    val detailPost = responseNewsDetail?.detailPost
                    newsDetailViewModel.setNewsDetail(detailPost!!)
                    binding?.tvNewsTitle?.text = detailPost.title
                    Glide.with(binding?.root!!)
                        .load(detailPost.image)
                        .apply(RequestOptions())
                        .into(binding?.ivNewsImg!!)
                    binding?.tvNewsPublishedAt?.text = detailPost.pusblisedAt
                    binding?.tvNewsLocation?.text = detailPost.location
                    binding?.tvNewsAuthor?.text = detailPost.author
                    binding?.tvNewsPostContent?.text = detailPost.postContent

                    verifyIsNewsSaved(detailPost)

                    binding?.ivSave?.setOnClickListener{
                        savedNews = SavedNews()
                        savedNews.let {
                            it?.title = detailPost.title
                            it?.url = fullPath
                            it?.headline = headline
                        }
                        if(savedNewsId == 0){

                            newsDetailViewModel.insertSavedNews(savedNews!!)
                            binding?.ivSave?.setImageResource(R.drawable.baseline_bookmark_24)
                            Toast.makeText(applicationContext, "Berhasil menyimpan berita", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            savedNews!!.id = savedNewsId
                            newsDetailViewModel.deleteSavedNews(savedNews!!)
                            savedNewsId = 0
                            binding?.ivSave?.setImageResource(R.drawable.baseline_bookmark_border_24)
                            Toast.makeText(applicationContext, "Berhasil menghapus berita tersimpan", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                }
            }

            override fun onFailure(call: Call<ResponseNewsDetail>, t: Throwable) {
                Toast.makeText(applicationContext, "fetch data in $path failed", Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun verifyIsNewsSaved(detailPost: DetailPost){
        newsDetailViewModel.getAllSavedNews().observe(this){ listSavedNews ->
            for (savedNews in listSavedNews){
                if (savedNews.title == detailPost.title){
                    savedNewsId = savedNews.id
                    binding?.ivSave?.setImageResource(R.drawable.baseline_bookmark_24)
                }
            }
        }
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

    private fun obtainViewModel(activity: AppCompatActivity): NewsDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[NewsDetailViewModel::class.java]
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.ivSave?.visibility = View.GONE

        } else {
            binding?.progressBar?.visibility = View.GONE
            binding?.ivSave?.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}