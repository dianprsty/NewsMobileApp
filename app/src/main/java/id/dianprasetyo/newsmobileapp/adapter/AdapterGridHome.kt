package id.dianprasetyo.newsmobileapp.adapter;


import android.content.Context;
import android.content.Intent
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.model.CategoryModel
import id.dianprasetyo.newsmobileapp.ui.NewsListByCategoryActivity

class AdapterGridHome(val context:Context) : BaseAdapter(){

        val listCategory = ArrayList<CategoryModel>()

        fun addCategory(categoryModel:List<CategoryModel>) {
                listCategory.addAll(categoryModel)
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
                val llGrid = view.findViewById<LinearLayout>(R.id.ll_grid)

                ivCategory.setImageResource(listCategory?.get(position)?.categoryImg!!)
                tvCategory.text = listCategory?.get(position)?.categoryName

                llGrid.setOnClickListener {
                        val link = listCategory?.get(position)?.categoryUrl
                        val category = listCategory?.get(position)?.categoryName
                        val moveWithDataIntent = Intent(context, NewsListByCategoryActivity::class.java)
                        moveWithDataIntent.putExtra(NewsListByCategoryActivity.EXTRA_URL, link)
                        moveWithDataIntent.putExtra(NewsListByCategoryActivity.EXTRA_CATEGORY, category)
                        context.startActivity(moveWithDataIntent)

                }

                return view
        }


}
