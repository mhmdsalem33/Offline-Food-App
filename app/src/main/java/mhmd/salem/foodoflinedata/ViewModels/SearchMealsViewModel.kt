package mhmd.salem.foodoflinedata.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mhmd.salem.foodoflinedata.data.Meal
import mhmd.salem.foodoflinedata.data.MealList
import mhmd.salem.foodoflinedata.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMealsViewModel():ViewModel() {

    private val _searchMealsLiveData = MutableLiveData<List<Meal>>()
    val searchMealsLiveData :LiveData<List<Meal>> = _searchMealsLiveData

    fun getSearchMeal(searchQuery : String)
    {
        RetrofitInstance.api.searchMeals(searchQuery).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if (response.body() != null)
                {
                    _searchMealsLiveData.value = response.body()!!.meals
                }
               Log.d("testApp" , "data search connected")
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
              Log.d("testApp" , "Data Search Disconnected"+ t.message.toString())
            }
        })
    }

}