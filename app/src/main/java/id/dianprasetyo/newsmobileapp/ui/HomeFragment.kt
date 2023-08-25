package id.dianprasetyo.newsmobileapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.dianprasetyo.newsmobileapp.R
import id.dianprasetyo.newsmobileapp.adapter.AdapterGridHome
import id.dianprasetyo.newsmobileapp.databinding.FragmentHomeBinding
import id.dianprasetyo.newsmobileapp.model.CategoryModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var binding: FragmentHomeBinding? = null
    private lateinit var listCategory : List<CategoryModel>
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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        listCategory = ArrayList<CategoryModel>()

        val adapterGridHome = AdapterGridHome(requireContext())
        listCategory += CategoryModel("Book", R.drawable.baseline_menu_book_24, "https://jakpost.vercel.app/api/category/life/books")
        listCategory += CategoryModel("Economy", R.drawable.baseline_currency_exchange_24, "https://jakpost.vercel.app/api/category/business/economy")
        listCategory += CategoryModel("Entertainment", R.drawable.baseline_slow_motion_video_24, "https://jakpost.vercel.app/api/category/life/entertainment")
        listCategory += CategoryModel("Heath", R.drawable.baseline_health_and_safety_24, "https://jakpost.vercel.app/api/category/life/health")
        listCategory += CategoryModel("Opinion", R.drawable.baseline_comment_24, "https://jakpost.vercel.app/api/category/academia/opinion")
        listCategory += CategoryModel("People", R.drawable.baseline_people_alt_24, "https://jakpost.vercel.app/api/category/life/people")
        listCategory += CategoryModel("Politic", R.drawable.baseline_emoji_people_24, "https://jakpost.vercel.app/api/category/indonesia/politics")
        listCategory += CategoryModel("Technology", R.drawable.baseline_computer_24, "https://jakpost.vercel.app/api/category/business/tech")
        listCategory += CategoryModel("World", R.drawable.baseline_language_24, "https://jakpost.vercel.app/api/category/world")
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}