package mhmd.salem.foodoflinedata.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mhmd.salem.foodoflinedata.Room.CategoryMealDatabase
import mhmd.salem.foodoflinedata.data.Categories
import mhmd.salem.foodoflinedata.data.CategoryMeal
import mhmd.salem.foodoflinedata.data.CategoryMealList
import mhmd.salem.foodoflinedata.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel(
    val db : CategoryMealDatabase
):ViewModel() {

    private val _categoryStateFlow = MutableStateFlow<List<CategoryMeal>>(emptyList())
    val categoryStateFlow :StateFlow<List<CategoryMeal>> = _categoryStateFlow

    private val _getSavedCategoryMealStateFlow = MutableStateFlow<List<CategoryMeal>>(emptyList())
    val getSavedCategoryMealStateFlow :StateFlow<List<CategoryMeal>> = _getSavedCategoryMealStateFlow

    fun getCategoryMeal(categoryName : String)
    {
        RetrofitInstance.api.getCategoryMeal(categoryName).enqueue(object :Callback<CategoryMealList>{
            override fun onResponse(call: Call<CategoryMealList>, response: Response<CategoryMealList>
            ) {

                response.body()!!.meals.let {
                    viewModelScope.launch {
                        _categoryStateFlow.emit(it)
                    }
                }
              Log.d("testApp" , "data category meal connected")

            }
            override fun onFailure(call: Call<CategoryMealList>, t: Throwable) {
                Log.d("testApp" , "data category meal Disconnected")
            }
        })
    }

    fun insertCategoriesMeals(categoryMeal: List<CategoryMeal> ) = viewModelScope.launch {
        db.categoryMealDao.upsert(categoryMeal)
    }
    fun getSavedMealByCategoryName(categoryName : String){
     viewModelScope.launch {
         db.categoryMealDao.searchInCategoryName(categoryName).collect{  savedData ->
             _getSavedCategoryMealStateFlow.emit(savedData)
         }
     }
    }

}