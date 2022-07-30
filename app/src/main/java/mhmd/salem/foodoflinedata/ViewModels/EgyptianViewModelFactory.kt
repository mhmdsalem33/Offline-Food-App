package mhmd.salem.foodoflinedata.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mhmd.salem.foodoflinedata.Room.EgyptianMealDatabase

class EgyptianViewModelFactory(val db: EgyptianMealDatabase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EgyptianViewModel(db) as T
    }
}