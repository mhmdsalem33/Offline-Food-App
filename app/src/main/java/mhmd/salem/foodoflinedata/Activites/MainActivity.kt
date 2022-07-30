package mhmd.salem.foodoflinedata.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import mhmd.salem.foodoflinedata.R
import mhmd.salem.foodoflinedata.Room.*

import mhmd.salem.foodoflinedata.ViewModels.*
import mhmd.salem.foodoflinedata.data.Category

import mhmd.salem.foodoflinedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding


    val seaFoodMvvm : SeaFoodViewModel  by lazy {
        val seaFoodDatabase         = SeaFoodDatabase.getInstance(this)
        val seaFoodViewModelFactory = SeaFoodViewModelFactory(seaFoodDatabase)
        ViewModelProvider(this , seaFoodViewModelFactory)[SeaFoodViewModel::class.java]
    }

    val overMvvm : OverPopularViewModel by lazy {
        val overDatabase = OverPopularDatabase.getInstance(this)
        val overFactory  = OverPopularViewModelFactory(overDatabase)
        ViewModelProvider(this , overFactory)[OverPopularViewModel::class.java]
    }

    val egyptianMvvm : EgyptianViewModel  by lazy {
        val egyptianMealDatabase     = EgyptianMealDatabase.getInstance(this)
        val egyptianViewModelFactory = EgyptianViewModelFactory(egyptianMealDatabase)
        ViewModelProvider(this , egyptianViewModelFactory)[EgyptianViewModel::class.java]
    }

    val chickenMvvm : ChickenMealsViewModel by lazy {
        val chickenDatabase = ChickenDatabase.getInstance(this)
        val chickenFactory  = ChickenMealsViewModelFactory(chickenDatabase)
        ViewModelProvider(this , chickenFactory)[ChickenMealsViewModel::class.java]
    }

    val categoryMealMvvm : CategoryMealViewModel by lazy {
        val categoryMealDatabase = CategoryMealDatabase.getInstance(this)
        val categoryMealViewModelFactory = CategoryMealViewModelFactory(categoryMealDatabase)
        ViewModelProvider(this , categoryMealViewModelFactory)[CategoryMealViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bottomNavigation = binding.btmNavigation
        val findNav          = Navigation.findNavController(this , R.id.host_fragment)
            NavigationUI.setupWithNavController(bottomNavigation , findNav)
    }





}