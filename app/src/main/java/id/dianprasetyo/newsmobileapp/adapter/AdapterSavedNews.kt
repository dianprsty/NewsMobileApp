package id.dianprasetyo.newsmobileapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.model.PostsItem
import id.dianprasetyo.newsmobileapp.room.SavedNews
import id.dianprasetyo.newsmobileapp.ui.NewsDetailActivity
import id.dianprasetyo.newsmobileapp.viewmodel.SavedNewsViewModel

class AdapterSavedNews(
    val context: Context,
    private val savedNewsViewModel: SavedNewsViewModel
) : RecyclerView.Adapter<AdapterSavedNews.ListViewHolder>() {

    val dataNews = ArrayList<SavedNews?>()


    fun setNewsItem(listNews: List<SavedNews>){
        dataNews.clear()
        dataNews.addAll(listNews)
    }


    class ListViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvNewsTitle = view.findViewById<TextView>(R.id.tv_news_title)
        val tvNewsHeadline = view.findViewById<TextView>(R.id.tv_news_headline)
        val tvPublishedAt = view.findViewById<TextView>(R.id.tv_news_published_at)
        val ivDelete = view.findViewById<ImageView>(R.id.iv_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_saved, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.apply {
            tvNewsTitle.text = dataNews[position]?.title
            tvNewsHeadline.text = dataNews[position]?.headline
            tvPublishedAt.text = dataNews[position]?.date

            tvNewsTitle.setOnClickListener {
                val link = dataNews[position]?.url
                val headline = dataNews[position]?.headline
                val moveWithDataIntent = Intent(context, NewsDetailActivity::class.java)
                moveWithDataIntent.putExtra(NewsDetailActivity.EXTRA_URL, link)
                moveWithDataIntent.putExtra(NewsDetailActivity.EXTRA_HEADLINE, headline)
                context.startActivity(moveWithDataIntent)
            }

            ivDelete.setOnClickListener{
                savedNewsViewModel.deleteSavedNews(dataNews[position]!!)
                Toast.makeText(context, "Berhasil Menghapus Berita", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int {
        return dataNews.size
    }


}