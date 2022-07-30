package mhmd.salem.foodoflinedata.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mhmd.salem.foodoflinedata.Room.MealDatabase
import mhmd.salem.foodoflinedata.Room.SeaFoodDatabase
import mhmd.salem.foodoflinedata.data.Category
import mhmd.salem.foodoflinedata.data.CategoryList
import mhmd.salem.foodoflinedata.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeaFoodViewModel (
    val db :SeaFoodDatabase
        ) :ViewModel() {

    private val _seafoodMutableStateFlow = MutableStateFlow<List<Category>>(emptyList())
    val seafoodMutableStateFlow :StateFlow<List<Category>> =  _seafoodMutableStateFlow
    var getSavedSeaFood = db.seaFoodDao.getAllSeafoodMeals()

     fun getSeaFood(){
        RetrofitInstance.api.getSeafood("Seafood").enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                viewModelScope.launch {
                    _seafoodMutableStateFlow.emit(response.body()!!.meals)
                }
                Log.d("testApp" , "connected")
            }
            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("testApp" , "Disconnected "+t.message.toString())
            }
        })
    }

    fun insertSeaFood(category: Category){
        viewModelScope.launch {
            db.seaFoodDao.upsertSeaFood(category)
        }
    }
    fun deleteSeaFood(category: Category){
        viewModelScope.launch {
            db.seaFoodDao.deleteSeaFood(category)
        }
    }


}