package mhmd.salem.foodoflinedata.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mhmd.salem.foodoflinedata.Activites.MealActivity
import mhmd.salem.foodoflinedata.Adapters.SearchAdapter
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_ID
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_NAME
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_THUMB
import mhmd.salem.foodoflinedata.ViewModels.SearchMealsViewModel
import mhmd.salem.foodoflinedata.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {


    private lateinit var binding       :FragmentSearchBinding
    private lateinit var searchMvvm    :SearchMealsViewModel
    private lateinit var searchAdapter :SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchMvvm    = ViewModelProviders.of(this)[SearchMealsViewModel::class.java]
        searchAdapter = SearchAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSearchBinding.inflate( inflater , container , false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchInMeals()
        observerSearchMeals()
        setupRecViewSearch()
        onItemSearchClick()
        onClearItemClick()

    }

    private fun onClearItemClick() {
        binding.clearSearch.setOnClickListener {
            binding.edtSearch.text.clear()
        }
    }

    private fun onItemSearchClick() {
        searchAdapter.onItemClick = { data ->
            try {
                val intent = Intent(context    , MealActivity::class.java)
                    intent.putExtra(MEAL_ID    , data.idMeal)
                    intent.putExtra(MEAL_NAME  , data.strMeal)
                    intent.putExtra(MEAL_THUMB , data.strMealThumb)
                startActivity(intent)
            }catch (t:Throwable)
            {
                Log.d("testApp" , t.message.toString())
            }
        }
    }

    private fun setupRecViewSearch() {
        binding.searchRec.apply {
            layoutManager = GridLayoutManager(context , 2 , RecyclerView.VERTICAL , false)
            adapter       = searchAdapter
        }
    }

    private fun observerSearchMeals() {
        searchMvvm.searchMealsLiveData.observe(viewLifecycleOwner  , Observer { data ->
            searchAdapter.differ.submitList(data)
        })
    }

    private fun searchInMeals() {
        val searchJob : Job ? = null
        binding.edtSearch.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            lifecycleScope.launch {
                delay(500)
                searchMvvm.getSearchMeal(searchQuery.toString())
            }
        }
    }

}