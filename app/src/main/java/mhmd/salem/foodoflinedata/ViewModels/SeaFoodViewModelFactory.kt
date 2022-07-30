package mhmd.salem.foodoflinedata.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mhmd.salem.foodoflinedata.Room.MealDatabase
import mhmd.salem.foodoflinedata.Room.SeaFoodDatabase

class SeaFoodViewModelFactory(
    val db: SeaFoodDatabase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SeaFoodViewModel(db) as T
    }
}