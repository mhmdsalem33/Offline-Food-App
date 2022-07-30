package mhmd.salem.foodoflinedata.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mhmd.salem.foodoflinedata.Room.MealDatabase

class MealViewModelFactory(
    val db : MealDatabase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(db) as T
    }
}