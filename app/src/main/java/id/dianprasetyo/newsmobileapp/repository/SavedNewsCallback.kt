package id.dianprasetyo.newsmobileapp.repository

import androidx.recyclerview.widget.DiffUtil
import id.dianprasetyo.newsmobileapp.room.SavedNews

class SavedNewsCallback(private val oldList: List<SavedNews>, private val newList: List<SavedNews>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSavedNews = oldList[oldItemPosition]
        val newSavedNews = newList[newItemPosition]
        return oldSavedNews.title == newSavedNews.title && oldSavedNews.headline == newSavedNews.headline
    }

}