package mhmd.salem.foodoflinedata.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mhmd.salem.foodoflinedata.Realm.CategoriesDatabase
import mhmd.salem.foodoflinedata.Room.CategoryMealDatabase
import mhmd.salem.foodoflinedata.data.Categories
import mhmd.salem.foodoflinedata.data.CategoriesList
import mhmd.salem.foodoflinedata.data.CategoryList
import mhmd.salem.foodoflinedata.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel():ViewModel() {

    private val  _categoriesMealLiveData = MutableLiveData<List<Categories>>()
    val categoriesMealLiveData : LiveData<List<Categories>> = _categoriesMealLiveData

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoriesList>{
            override fun onResponse(call: Call<CategoriesList>, response: Response<CategoriesList>) {

                response.body()!!.categories.let {
                    _categoriesMealLiveData.postValue(it)
                }
               Log.d("testApp" , "Data Categories connected")
            }
            override fun onFailure(call: Call<CategoriesList>, t: Throwable) {
                Log.d("testApp" , "Data categories disconnected" + t.message.toString())
            }

        })
    }





}