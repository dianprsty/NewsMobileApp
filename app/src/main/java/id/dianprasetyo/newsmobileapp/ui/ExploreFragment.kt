package id.dianprasetyo.newsmobileapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.adapter.AdapterNewsExplore
import id.dianprasetyo.newsmobileapp.api.APIConfig
import id.dianprasetyo.newsmobileapp.databinding.FragmentExploreBinding
import id.dianprasetyo.newsmobileapp.factory.ViewModelFactory
import id.dianprasetyo.newsmobileapp.model.PostsItem
import id.dianprasetyo.newsmobileapp.model.ResponseNews
import id.dianprasetyo.newsmobileapp.viewmodel.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [ExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExploreFragment : Fragment() {
    private var binding : FragmentExploreBinding? = null
    private lateinit var newsViewModel: NewsViewModel
    private var adapterNewsExplore: AdapterNewsExplore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        newsViewModel = obtainViewModel(requireActivity() as AppCompatActivity)


        newsViewModel.currentNewsList().observe(viewLifecycleOwner){ listNewsItem ->
            if( listNewsItem != null){
                showLoading(false)
                val filteredNewsData = ArrayList<PostsItem?>()

                sortNews("second", listNewsItem, filteredNewsData)
                sortNews("minute", listNewsItem, filteredNewsData)
                sortNews("hour", listNewsItem, filteredNewsData)
                sortNews("day", listNewsItem, filteredNewsData)
                sortNews("week", listNewsItem, filteredNewsData)
                sortNews("month", listNewsItem, filteredNewsData)
                sortNews("year", listNewsItem, filteredNewsData)

                adapterNewsExplore?.setNewsItem(filteredNewsData)
                adapterNewsExplore?.notifyDataSetChanged()
            }else{
                showLoading(true)
            }
        }

        binding?.svSearch?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                newsViewModel.filterNews(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText?.length == 0){
                    newsViewModel.resetFilter()
                }
                return true
            }

        })

        adapterNewsExplore = AdapterNewsExplore(requireContext())
        binding?.rvExploreNews?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = adapterNewsExplore
        }

        handleBackPressed()


        return binding?.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ExploreFragment.
         */
        @JvmStatic
        fun newInstance() =
            ExploreFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun sortNews(param : String, listNews : MutableList<PostsItem?>, filteredNewsData: ArrayList<PostsItem?>){

        val listDay = listNews.filter { it?.pusblisedAt!!.contains(param) }
        filteredNewsData.addAll(listDay.sortedBy { it?.pusblisedAt })
    }

    private fun handleBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
                bottomNav.selectedItemId = R.id.menu_home
                val fragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                fragmentTransaction
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()

            }
        })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE

        } else {
            binding?.progressBar?.visibility = View.GONE
        }
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