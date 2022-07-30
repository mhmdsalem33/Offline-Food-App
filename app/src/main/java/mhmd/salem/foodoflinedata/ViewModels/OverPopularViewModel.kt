package mhmd.salem.foodoflinedata.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mhmd.salem.foodoflinedata.Room.OverPopularDatabase

import mhmd.salem.foodoflinedata.data.Category
import mhmd.salem.foodoflinedata.data.CategoryList
import mhmd.salem.foodoflinedata.data.OverPopular
import mhmd.salem.foodoflinedata.data.OverPopularList
import mhmd.salem.foodoflinedata.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class OverPopularViewModel (
    val db : OverPopularDatabase
        ) :ViewModel() {

    private val _getOverPopularLiveData = MutableLiveData<List<OverPopular>>()
    val getOverPopularLiveData :LiveData<List<OverPopular>> = _getOverPopularLiveData
    val getOverSavedData = db.overPopularDao.getAllDataOverPopular()

    fun getOverPopular(){
        RetrofitInstance.api.getOverPopular("Beef").enqueue(object  :Callback<OverPopularList>{
            override fun onResponse(call: Call<OverPopularList>, response: Response<OverPopularList>) {

                response.body()!!.meals.let { data ->
                    _getOverPopularLiveData.postValue(data)
                }

            }
            override fun onFailure(call: Call<OverPopularList>, t: Throwable) {
                Log.d("testApp" ,"Over Popular Data Disconnected"+ t.message.toString())
            }

        })
    }
    fun upsert(over : OverPopular){
        viewModelScope.launch {
            db.overPopularDao.upsert(over)
        }
    }
    fun deleteOver(over: OverPopular){
        viewModelScope.launch {
            db.overPopularDao.delete(over)
        }
    }
    fun deleteAllFromOver() = viewModelScope.launch {
        db.overPopularDao.deleteAllFromOver()
    }


}

