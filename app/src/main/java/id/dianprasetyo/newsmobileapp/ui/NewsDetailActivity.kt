package id.dianprasetyo.newsmobileapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.api.APIConfig
import id.dianprasetyo.newsmobileapp.databinding.ActivityNewsDetailBinding
import id.dianprasetyo.newsmobileapp.factory.ThemeViewModelFactory
import id.dianprasetyo.newsmobileapp.factory.ViewModelFactory
import id.dianprasetyo.newsmobileapp.model.ResponseNews
import id.dianprasetyo.newsmobileapp.model.ResponseNewsDetail
import id.dianprasetyo.newsmobileapp.repository.SettingPreferences
import id.dianprasetyo.newsmobileapp.viewmodel.NewsDetailViewModel
import id.dianprasetyo.newsmobileapp.viewmodel.NewsViewModel
import id.dianprasetyo.newsmobileapp.viewmodel.ThemeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class NewsDetailActivity : AppCompatActivity() {

    private var binding : ActivityNewsDetailBinding? = null
    private lateinit var themeViewModel: ThemeViewModel
    private lateinit var newsDetailViewModel: NewsDetailViewModel
    companion object{
        const val EXTRA_URL = "extra_url"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)

        getCurrentTheme()

        val fullPath = intent.getStringExtra(EXTRA_URL)

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
                }
            }

            override fun onFailure(call: Call<ResponseNewsDetail>, t: Throwable) {
                Toast.makeText(applicationContext, "fetch data in $path failed", Toast.LENGTH_SHORT).show()
            }

        })
        setContentView(binding?.root)

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
            binding?.tvNewsAuthorLabel?.visibility = View.GONE

        } else {
            binding?.progressBar?.visibility = View.GONE
            binding?.ivSave?.visibility = View.VISIBLE
            binding?.tvNewsAuthorLabel?.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}