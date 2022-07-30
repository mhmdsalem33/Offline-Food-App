package mhmd.salem.foodoflinedata.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mhmd.salem.foodoflinedata.data.Category
import mhmd.salem.foodoflinedata.data.Egyptian
import mhmd.salem.foodoflinedata.data.EgyptianList
import mhmd.salem.foodoflinedata.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BritishMealsViewModel():ViewModel() {

    private val _britishMealsLiveData = MutableLiveData<List<Egyptian>>()
    val britishMealsLiveData : LiveData<List<Egyptian>> = _britishMealsLiveData

        fun getBritishMeals(){
            RetrofitInstance.api.getEgyptianMeals("British").enqueue(object :Callback<EgyptianList>{
                override fun onResponse(call: Call<EgyptianList>, response: Response<EgyptianList>
                ) {

                    response.body()?.meals.let {
                        _britishMealsLiveData.postValue(it)
                    }
                   Log.d("testApp" , "Data British Meals connected")
                }

                override fun onFailure(call: Call<EgyptianList>, t: Throwable) {
                   Log.d("testApp" , t.message.toString())
                }

            })
        }

}