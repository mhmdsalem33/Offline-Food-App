package mhmd.salem.foodoflinedata.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mhmd.salem.foodoflinedata.Room.ChickenDatabase

class ChickenMealsViewModelFactory(
    val db : ChickenDatabase
) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return return  ChickenMealsViewModel(db) as T
    }

}