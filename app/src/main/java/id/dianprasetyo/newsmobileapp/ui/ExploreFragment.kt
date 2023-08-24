package id.dianprasetyo.newsmobileapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.adapter.AdapterNewsExplore
import id.dianprasetyo.newsmobileapp.api.APIConfig
import id.dianprasetyo.newsmobileapp.databinding.FragmentExploreBinding
import id.dianprasetyo.newsmobileapp.factory.ViewModelFactory
import id.dianprasetyo.newsmobileapp.model.ResponseNews
import id.dianprasetyo.newsmobileapp.viewmodel.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExploreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var binding : FragmentExploreBinding? = null
    private lateinit var newsViewModel: NewsViewModel
    private var adapterNewsExplore: AdapterNewsExplore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        newsViewModel = obtainViewModel(requireActivity() as AppCompatActivity)


        newsViewModel.currentNewsList().observe(viewLifecycleOwner){ newsItem ->
            if( newsItem != null){
                adapterNewsExplore?.setNewsItem(newsItem)
                adapterNewsExplore?.notifyDataSetChanged()
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



        return binding?.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExploreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExploreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
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