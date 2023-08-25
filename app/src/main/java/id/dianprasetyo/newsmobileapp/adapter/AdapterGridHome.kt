package id.dianprasetyo.newsmobileapp.adapter;


import android.content.Context;
import android.content.Intent
import android.database.DataSetObserver
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.model.GridViewModal
import id.dianprasetyo.newsmobileapp.model.PostsItem
import id.dianprasetyo.newsmobileapp.ui.NewsDetailActivity

class AdapterGridHome(val context:Context) : BaseAdapter(){

        val listCategory = ArrayList<GridViewModal>()

        fun addCategory(gridViewModal:List<GridViewModal>) {
                listCategory.addAll(gridViewModal)
        }

        override fun getCount(): Int {
                if (listCategory != null) {
                        return listCategory.size
                }
                return 0
        }

        override fun getItem(p0: Int): Any? {
                return null
        }

        override fun getItemId(p0: Int): Long {
                return  0
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
                var view = view
                view = LayoutInflater.from(parent?.context)
                        .inflate(R.layout.item_grid_view_home, parent, false)

                val ivCategory = view.findViewById<ImageView>(R.id.iv_category)
                val tvCategory = view.findViewById<TextView>(R.id.tv_category)

                ivCategory.setImageResource(listCategory?.get(position)?.categoryImg!!)
                ivCategory.setColorFilter(com.google.android.material.R.color.design_default_color_primary_dark, PorterDuff.Mode.MULTIPLY)
                tvCategory.text = listCategory?.get(position)?.categoryName

//                itemView.setOnClickListener {
//                        val link = dataNews?.get(position)?.link
//                        val moveWithDataIntent = Intent(context, NewsDetailActivity::class.java)
//                        moveWithDataIntent.putExtra(NewsDetailActivity.EXTRA_URL, link)
//                        context.startActivity(moveWithDataIntent)
//                        }
//                }

                return view
        }


}
