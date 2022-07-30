package mhmd.salem.foodoflinedata.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mhmd.salem.foodoflinedata.Room.CategoryMealDatabase

class CategoryMealViewModelFactory(
    val db :CategoryMealDatabase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryMealViewModel(db) as T
    }
}