package id.dianprasetyo.newsmobileapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.model.PostsItem

class AdapterNewsExplore(val dataNews: List<PostsItem?>?) : RecyclerView.Adapter<AdapterNewsExplore.ListViewHolder>() {

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
                val title = dataNews?.get(position)?.title
                Toast.makeText(holder.itemView.context, "$title", Toast.LENGTH_SHORT).show()
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