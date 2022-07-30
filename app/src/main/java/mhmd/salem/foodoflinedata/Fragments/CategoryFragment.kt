package mhmd.salem.foodoflinedata.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mhmd.salem.foodoflinedata.Activites.MainActivity
import mhmd.salem.foodoflinedata.Activites.MealActivity
import mhmd.salem.foodoflinedata.Adapters.CategoryMealAdapter
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_ID
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_NAME
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_THUMB
import mhmd.salem.foodoflinedata.ViewModels.CategoryMealViewModel
import mhmd.salem.foodoflinedata.databinding.FragmentCategoryBinding
import java.io.IOException


class CategoryFragment : Fragment() {

    private lateinit var binding             : FragmentCategoryBinding
    private lateinit var categoryMealMvvm    : CategoryMealViewModel
    private lateinit var categoryMealAdapter : CategoryMealAdapter
    private lateinit var categoryName        : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      //  categoryMealMvvm = ViewModelProviders.of(this)[CategoryMealViewModel::class.java]
        categoryMealMvvm     = (activity as MainActivity).categoryMealMvvm
        categoryMealAdapter  = CategoryMealAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInformationByBundle()
        categoryMealMvvm.getCategoryMeal(categoryName)
        categoryMealMvvm.getSavedMealByCategoryName(categoryName)
        observerGetCategoryMeal()
        setupCategoryMealRecView()
        getSavedDataFromRoom()
        onItemCategoryMealClick()

    }

    private fun onItemCategoryMealClick() {
            categoryMealAdapter.onItemClick = { data ->
                try {
                    val intent = Intent(context , MealActivity::class.java)
                         intent.putExtra(MEAL_ID  , data.idMeal)
                         intent.putExtra(MEAL_NAME  , data.strMeal)
                         intent.putExtra(MEAL_THUMB  , data.strMealThumb)
                    startActivity(intent)
                }catch (t:Throwable)
                {
                    Log.d("testApp" , t.message.toString())
                }
            }
    }

    private fun getSavedDataFromRoom() {
        lifecycleScope.launchWhenStarted {
            categoryMealMvvm.getSavedCategoryMealStateFlow.collect{ savedData ->
                categoryMealAdapter.differ.submitList(savedData)
            }
        }
    }
    private fun setupCategoryMealRecView() {
        binding.recCategory.apply {
            layoutManager = GridLayoutManager(context ,2 , RecyclerView.VERTICAL , false)
            adapter       = categoryMealAdapter
        }
    }
    private fun observerGetCategoryMeal() {

        lifecycleScope.launchWhenStarted {
            categoryMealMvvm.categoryStateFlow.collect{ apiData ->
                if (apiData == null)
                {
                    Snackbar.make(binding.root , "Sorry No Meals Found" , Snackbar.LENGTH_SHORT).show()
                }
                else
                {
                    categoryMealMvvm.insertCategoriesMeals(apiData)
                }
            }
        }
    }

    private fun getInformationByBundle() {
        val data = arguments
            categoryName = data?.getString("categoryName").toString()
    }


}