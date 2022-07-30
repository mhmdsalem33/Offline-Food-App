package mhmd.salem.foodoflinedata.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mhmd.salem.foodoflinedata.Room.MealDatabase
import mhmd.salem.foodoflinedata.data.Category
import mhmd.salem.foodoflinedata.data.Meal
import mhmd.salem.foodoflinedata.data.MealList
import mhmd.salem.foodoflinedata.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    val db : MealDatabase
) :ViewModel() {

    var mealLiveData = MutableLiveData<Meal>()
    val savedMeals = db.mealDao.getAllMeals()

    private val _getMealBySavedIdMeal = MutableStateFlow<List<Meal>>(emptyList())
    val getMealBySavedIdMeal :StateFlow<List<Meal>> = _getMealBySavedIdMeal


    fun getMeal(id:String){
        RetrofitInstance.api.getMeal(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

              if (response.body() != null)
              {
                  mealLiveData.value = response.body()!!.meals[0]
              }else
              {
                  Log.d("testApp" , "Meal ViewModel error")
              }

                Log.d("testApp" , "Data Meal By Id Connected")
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
              Log.d("testApp" , t.message.toString())
            }

        })

    }

    fun searchId(idQuery :String) = viewModelScope.launch {
            db.mealDao.searchInMealsById(idQuery).collect { data ->
                _getMealBySavedIdMeal.emit(data)
            }
    }

    fun insertMeal(meal :Meal){
        viewModelScope.launch {
            db.mealDao.upsert(meal)
        }
    }
}
