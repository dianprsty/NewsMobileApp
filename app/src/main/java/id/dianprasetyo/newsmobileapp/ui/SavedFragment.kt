package id.dianprasetyo.newsmobileapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.adapter.AdapterSavedNews
import id.dianprasetyo.newsmobileapp.databinding.FragmentSavedBinding
import id.dianprasetyo.newsmobileapp.factory.ViewModelFactory
import id.dianprasetyo.newsmobileapp.viewmodel.SavedNewsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SavedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavedFragment : Fragment() {
    private var binding : FragmentSavedBinding? = null
    private lateinit var savedNewsViewModel: SavedNewsViewModel
    private var adapterSavedNews : AdapterSavedNews? = null

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
        binding = FragmentSavedBinding.inflate(layoutInflater)

        savedNewsViewModel = obtainViewModel(requireActivity() as AppCompatActivity)


        savedNewsViewModel.getAllSavedNews().observe(viewLifecycleOwner){ savedNews ->
            if( savedNews != null){
                binding?.tvSavedEmpty!!.visibility = View.GONE
                adapterSavedNews?.setNewsItem(savedNews)
                adapterSavedNews?.notifyDataSetChanged()
            }

            if (savedNews.isEmpty()){
                binding?.tvSavedEmpty!!.visibility = View.VISIBLE
            }
        }

        adapterSavedNews = AdapterSavedNews(requireContext(), savedNewsViewModel)
        binding?.rvSavedNews?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = adapterSavedNews
        }

        handleBackPressed()

        return binding?.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SavedFragment.
         */
        @JvmStatic
        fun newInstance() =
            SavedFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun obtainViewModel(activity: AppCompatActivity): SavedNewsViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[SavedNewsViewModel::class.java]
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}