package mhmd.salem.foodoflinedata.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mhmd.salem.foodoflinedata.Room.EgyptianMealDatabase
import mhmd.salem.foodoflinedata.data.Egyptian
import mhmd.salem.foodoflinedata.data.EgyptianList
import mhmd.salem.foodoflinedata.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EgyptianViewModel(
    val db : EgyptianMealDatabase
):ViewModel() {


   private val getEgyptLiveData = MutableLiveData<List<Egyptian>>()
    val getSavedEgyptianMeal = db.egyptianDao.getAllFromEgyptian()

    fun getEgyptianMeals(){
        RetrofitInstance.api.getEgyptianMeals("Egyptian").enqueue(object :Callback<EgyptianList>{
            override fun onResponse(call: Call<EgyptianList>, response: Response<EgyptianList>) {

                response.body()?.meals.let {
                    getEgyptLiveData.postValue(it)
                }

                Log.d("testApp" , "Data Egyptian Connected")
            }

            override fun onFailure(call: Call<EgyptianList>, t: Throwable) {

                Log.d("testApp" , "Data Egyptian DisConnected")
            }

        })
    }
    fun observeGetEgyptianLiveData(): LiveData<List<Egyptian>> = getEgyptLiveData


    fun upsert(egyptian: List<Egyptian>) {
        viewModelScope.launch {
            db.egyptianDao.upsert(egyptian)
        }
    }







}