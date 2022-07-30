package mhmd.salem.foodoflinedata.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mhmd.salem.foodoflinedata.Room.ChickenDatabase
import mhmd.salem.foodoflinedata.data.ChickenList
import mhmd.salem.foodoflinedata.data.Chicken
import mhmd.salem.foodoflinedata.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChickenMealsViewModel(
    val db : ChickenDatabase
)  : ViewModel() {

    private val _chickenMealLiveData = MutableLiveData<List<Chicken>>()
    val chickenMealLiveData :LiveData<List<Chicken>> = _chickenMealLiveData

    val savedChickenMeals = db.chickenDao.getChickenSavedInformation()

    fun getChickenMeals(){
        RetrofitInstance.api.getChickenBreast("chicken_breast").enqueue(object :Callback<ChickenList>{
            override fun onResponse(call: Call<ChickenList>, response: Response<ChickenList>) {
                response.body()!!.meals.let {
                    _chickenMealLiveData.postValue(it)
                }
                Log.d("testApp" , "Data chicken Connected")
            }
            override fun onFailure(call: Call<ChickenList>, t: Throwable) {
              Log.d("testApp" , "Data chicken disConnected"+t.message.toString())
            }

        })
    }
    fun  upsertChicken(chicken: List<Chicken>)
    {
        viewModelScope.launch {
            db.chickenDao.upsert(chicken)
        }
    }

}