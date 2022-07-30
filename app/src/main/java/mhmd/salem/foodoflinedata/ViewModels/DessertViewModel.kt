package mhmd.salem.foodoflinedata.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mhmd.salem.foodoflinedata.data.Category
import mhmd.salem.foodoflinedata.data.CategoryList
import mhmd.salem.foodoflinedata.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DessertViewModel () : ViewModel() {

    private val _getDesertLiveData = MutableLiveData<List<Category>>()
    val getDesertLiveData  :LiveData<List<Category>> = _getDesertLiveData

    fun getDessert(){
        RetrofitInstance.api.getBreakFastMeals("Dessert").enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                response.body()!!.meals.let {
                    _getDesertLiveData.postValue(it)
                }
                Log.d("testApp" , "Data Dessert Connected")
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
               Log.d("testApp" , "Data dessert disconnected")
            }
        })
    }




}