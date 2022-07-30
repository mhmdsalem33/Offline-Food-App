package mhmd.salem.foodoflinedata.Activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.channels.Channel
import mhmd.salem.foodoflinedata.Fragments.HomeFragment

import mhmd.salem.foodoflinedata.ViewModels.MealViewModel
import mhmd.salem.foodoflinedata.ViewModels.MealViewModelFactory
import mhmd.salem.foodoflinedata.data.Meal
import mhmd.salem.foodoflinedata.databinding.ActivityMealBinding

class MealActivity : AppCompatActivity() {



    private lateinit var binding   :ActivityMealBinding
    private lateinit var mealId    :String
    private lateinit var mealName  :String
    private lateinit var mealThumb :String




    val mealMvvm : MealViewModel  by lazy {
        val mealDatabase         = mhmd.salem.foodoflinedata.Room.MealDatabase.getInstance(this)
        val mealViewModelFactory = MealViewModelFactory(mealDatabase)
        ViewModelProvider(this , mealViewModelFactory)[MealViewModel::class.java]
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getMealInformationByIntent()
        setMealInformationInViews()

        observerGetMeal()
        getMealFromRoom()

        mealMvvm.getMeal(mealId)
        mealMvvm.searchId(mealId)     // Pass Id To searchQuery to get value


    }

    private fun observerGetMeal() {
        mealMvvm.mealLiveData.observe(this , Observer { data ->
            data?.let {
                mealMvvm.insertMeal(data)       // Pass Data From Api To Room
               // Toast.makeText(applicationContext, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getMealFromRoom(){
        lifecycleScope.launchWhenCreated {   // Read Data From Room By idMeal
            mealMvvm.getMealBySavedIdMeal.collect { data ->
                for (i in data)
                {
                    binding.categoryName.text = "Category : ${i.strCategory}"
                    binding.textDetails.text  = i.strInstructions
                    binding.locationName.text = "Area : ${i.strArea}"
                    binding.imgYoutube.setOnClickListener{
                        val intent = Intent(Intent.ACTION_VIEW , Uri.parse(i.strYoutube))
                            startActivity(intent)
                    }
                }
            }
        }
    }

    private fun setMealInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMeal)
        binding.collapsing.title = mealName

    }
    private fun getMealInformationByIntent() {
        val intent    = intent
        mealId    = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName  = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

}


