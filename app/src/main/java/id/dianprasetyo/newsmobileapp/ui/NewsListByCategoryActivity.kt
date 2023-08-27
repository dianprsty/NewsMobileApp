package id.dianprasetyo.newsmobileapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.dianprasetyo.newsmobileapp.adapter.AdapterNewsExplore
import id.dianprasetyo.newsmobileapp.api.APIConfig
import id.dianprasetyo.newsmobileapp.databinding.ActivityNewsListByCategoryBinding
import id.dianprasetyo.newsmobileapp.factory.ViewModelFactory
import id.dianprasetyo.newsmobileapp.model.ResponseNews
import id.dianprasetyo.newsmobileapp.viewmodel.NewsListByCategoryViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListByCategoryActivity : AppCompatActivity() {

    private var binding : ActivityNewsListByCategoryBinding? = null
    private lateinit var newsListByCategoryViewModel: NewsListByCategoryViewModel
    private var adapterNewsExplore: AdapterNewsExplore? = null
    companion object{
        const val EXTRA_URL = "extra_url"
        const val EXTRA_CATEGORY = ""
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListByCategoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val category = intent.getStringExtra(EXTRA_CATEGORY)
        supportActionBar?.title = category

        newsListByCategoryViewModel = obtainViewModel(this)
        val fullPath = intent.getStringExtra(EXTRA_URL)
        val path : String = fullPath!!.removePrefix("https://jakpost.vercel.app/api/category/")
        showLoading(true)
        fetchNews(path)

        newsListByCategoryViewModel.currentNewsList().observe(this){ newsItem ->
            if( newsItem != null){
                adapterNewsExplore?.setNewsItem(newsItem)
                adapterNewsExplore?.notifyDataSetChanged()
            }
        }

        adapterNewsExplore = AdapterNewsExplore(this)
        binding?.rvNewsList?.apply {
            layoutManager = LinearLayoutManager(this@NewsListByCategoryActivity)
            setHasFixedSize(true)
            adapter = adapterNewsExplore
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE

        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }


    private fun fetchNews(path : String){
        APIConfig.getService().getNewsByCategory(path).enqueue(object :
            Callback<ResponseNews> {
            override fun onResponse(call: Call<ResponseNews>, response: Response<ResponseNews>) {
                if (response.isSuccessful) {
                    val responseNews = response.body()
                    val postsItem = responseNews?.posts
                    newsListByCategoryViewModel.addNews(postsItem)
                }
                showLoading(false)
            }

            override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                Toast.makeText(applicationContext, "fetch data in $path failed", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): NewsListByCategoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[NewsListByCategoryViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
