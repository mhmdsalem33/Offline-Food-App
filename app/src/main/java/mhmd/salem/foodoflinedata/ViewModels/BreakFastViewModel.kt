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

class BreakFastViewModel():ViewModel() {


   private val _getBreakFastLiveData = MutableLiveData<List<Category>>()
    val getBreakFastLiveData :LiveData<List<Category>> = _getBreakFastLiveData

    fun getBreakFastMeals(){
      RetrofitInstance.api.getBreakFastMeals("BreakFast").enqueue(object : Callback<CategoryList>{
          override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                response.body()?.meals.let {  mealList ->
                    _getBreakFastLiveData.postValue(mealList)
                }
             Log.d("testApp" , "Data Break Fast Connected")
          }

          override fun onFailure(call: Call<CategoryList>, t: Throwable) {
              Log.d("testApp" , "Data Break Fast DisConnected"+t.message.toString())
          }
      })
    }



}