package id.dianprasetyo.newsmobileapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.model.PostsItem
import id.dianprasetyo.newsmobileapp.ui.NewsDetailActivity
import id.dianprasetyo.newsmobileapp.viewmodel.NewsViewModel

class AdapterNewsExplore(val context: Context) : RecyclerView.Adapter<AdapterNewsExplore.ListViewHolder>() {

    val dataNews = ArrayList<PostsItem?>()

    fun setNewsItem(listNews: List<PostsItem?>){
        dataNews.clear()
        dataNews.addAll(listNews)
    }


    class ListViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvNewsTitle = view.findViewById<TextView>(R.id.tv_news_title)
        val tvNewsHeadline = view.findViewById<TextView>(R.id.tv_news_headline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_news, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.apply {
            tvNewsTitle.text = dataNews?.get(position)?.title
            tvNewsHeadline.text = dataNews?.get(position)?.headline

            itemView.setOnClickListener {
                val link = dataNews?.get(position)?.link
                val moveWithDataIntent = Intent(context, NewsDetailActivity::class.java)
                moveWithDataIntent.putExtra(NewsDetailActivity.EXTRA_URL, link)
                context.startActivity(moveWithDataIntent)
            }
        }
    }

    override fun getItemCount(): Int {
        if (dataNews != null) {
            return dataNews.size
        }
        return 0
    }
}