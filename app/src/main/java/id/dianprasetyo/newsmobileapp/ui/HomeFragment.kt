package id.dianprasetyo.newsmobileapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.adapter.AdapterGridHome
import id.dianprasetyo.newsmobileapp.databinding.FragmentHomeBinding
import id.dianprasetyo.newsmobileapp.factory.ThemeViewModelFactory
import id.dianprasetyo.newsmobileapp.model.CategoryModel
import id.dianprasetyo.newsmobileapp.preferences.SettingPreferences
import id.dianprasetyo.newsmobileapp.viewmodel.ThemeViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private lateinit var themeViewModel: ThemeViewModel
    private lateinit var listCategory : List<CategoryModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        listCategory = ArrayList()

        getCurrentTheme()

        val adapterGridHome = AdapterGridHome(requireContext())
        listCategory += CategoryModel("Book", R.drawable.book, "https://jakpost.vercel.app/api/category/life/books")
        listCategory += CategoryModel("Economy", R.drawable.income, "https://jakpost.vercel.app/api/category/business/economy")
        listCategory += CategoryModel("Entertainment", R.drawable.earphone, "https://jakpost.vercel.app/api/category/life/entertainment")
        listCategory += CategoryModel("Heath", R.drawable.healthcare, "https://jakpost.vercel.app/api/category/life/health")
        listCategory += CategoryModel("Opinion", R.drawable.chat, "https://jakpost.vercel.app/api/category/academia/opinion")
        listCategory += CategoryModel("People", R.drawable.team, "https://jakpost.vercel.app/api/category/life/people")
        listCategory += CategoryModel("Politic", R.drawable.politician, "https://jakpost.vercel.app/api/category/indonesia/politics")
        listCategory += CategoryModel("Technology", R.drawable.smartphone, "https://jakpost.vercel.app/api/category/business/tech")
        listCategory += CategoryModel("World", R.drawable.travel, "https://jakpost.vercel.app/api/category/world")
        adapterGridHome.addCategory(listCategory)
        binding?.gvHome?.apply {
            adapter = adapterGridHome
        }

        return binding?.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun getCurrentTheme(){
        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        themeViewModel =
            ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]
        themeViewModel.getTheme().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding?.rlHome?.setBackgroundResource(R.drawable.layout_bg_dark)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding?.rlHome?.setBackgroundResource(R.drawable.layout_bg_light)
            }
        }
    }
}