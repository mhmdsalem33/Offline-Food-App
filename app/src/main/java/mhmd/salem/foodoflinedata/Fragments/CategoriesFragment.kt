package mhmd.salem.foodoflinedata.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.flow.collect
import mhmd.salem.foodoflinedata.Activites.MainActivity
import mhmd.salem.foodoflinedata.Activites.MealActivity
import mhmd.salem.foodoflinedata.Adapters.BritishAdapter
import mhmd.salem.foodoflinedata.Adapters.ChickenAdapter
import mhmd.salem.foodoflinedata.Adapters.DessertsAdapter
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_ID
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_NAME
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_THUMB
import mhmd.salem.foodoflinedata.Realm.BritishDatabase
import mhmd.salem.foodoflinedata.Realm.DessertDatabase
import mhmd.salem.foodoflinedata.ViewModels.BritishMealsViewModel
import mhmd.salem.foodoflinedata.ViewModels.ChickenMealsViewModel
import mhmd.salem.foodoflinedata.ViewModels.DessertViewModel
import mhmd.salem.foodoflinedata.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {


    private lateinit var  binding :FragmentCategoriesBinding
    private lateinit var  dessertMvvm      : DessertViewModel
    private lateinit var  britishMvvm      : BritishMealsViewModel
    private lateinit var  chickenMvvm      : ChickenMealsViewModel
    private lateinit var  dessertsAdapter  : DessertsAdapter
    private lateinit var  britishAdapter   : BritishAdapter
    private lateinit var  chickenAdapter   : ChickenAdapter

    var idRealm : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            dessertMvvm     = ViewModelProviders.of(this)[DessertViewModel::class.java]
            britishMvvm     = ViewModelProviders.of(this)[BritishMealsViewModel::class.java]
          //  chickenMvvm     = ViewModelProviders.of(this)[ChickenMealsViewModel::class.java]
            chickenMvvm     = (activity as MainActivity).chickenMvvm
            dessertsAdapter = DessertsAdapter(context!!)
            britishAdapter  = BritishAdapter(context!!)
            chickenAdapter  = ChickenAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater , container , false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dessertMvvm.getDessert()
        observeGetDessertMeals()
        setupRecViewDessert()

        britishMvvm.getBritishMeals()
        observerGetBritishMeals()
        setupRecViewBritishMeals()
        onBritishMealsClick()



        chickenMvvm.getChickenMeals()
        observerGetChickenMeals()
        setupChickenMealsRecView()
        getSavedChickenMeals()
        onChickenItemClick()



    }

    private fun onChickenItemClick() {
        chickenAdapter.onItemClick = { data ->
            try {
                val intent = Intent(context    , MealActivity::class.java)
                    intent.putExtra(MEAL_ID    , data.idMeal)
                    intent.putExtra(MEAL_NAME  , data.strMeal)
                    intent.putExtra(MEAL_THUMB , data.strMealThumb)
                startActivity(intent)
            }catch (t:Throwable)
            {
                Log.d("testApp" , t.message.toString())
            }
        }
    }

    private fun getSavedChickenMeals() {
        lifecycleScope.launchWhenStarted {
            chickenMvvm.savedChickenMeals.collect{ savedChicken ->
                chickenAdapter.differ.submitList(savedChicken)
            }
        }
    }

    private fun setupChickenMealsRecView() {
       binding.chickenRec.apply {
           layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL  , false)
           adapter       = chickenAdapter
       }
    }

    private fun observerGetChickenMeals() {
        chickenMvvm.chickenMealLiveData.observe(viewLifecycleOwner , Observer { data ->
                    chickenMvvm.upsertChicken(data)
                //chickenAdapter.differ.submitList(data)
        })
    }


    private fun onBritishMealsClick() {
        britishAdapter.onItemClick = { data ->
            try {
                val intent = Intent(context      , MealActivity::class.java)
                    intent.putExtra(MEAL_ID      , data.idMeal)
                    intent.putExtra(MEAL_NAME    , data.strMeal)
                    intent.putExtra(MEAL_THUMB   , data.strMealThumb)
                startActivity(intent)

            }catch (t:Throwable)
            {
                Log.d("testApp" , t.message.toString())
            }
        }


    }
    private fun setupRecViewBritishMeals() {
        binding.britishRec.apply {
            layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL , false)
            adapter       = britishAdapter
        }
    }
    private fun observerGetBritishMeals() {

        val config = RealmConfiguration.Builder().name("desserts.realm").build()
        val realm  = Realm.getInstance(config)
        idRealm    = realm.where(BritishDatabase::class.java).findAll().size

        britishMvvm.britishMealsLiveData.observe(viewLifecycleOwner , Observer { data ->
            if (realm.where(BritishDatabase::class.java).findAll().isEmpty()){
                realm.beginTransaction()
                for (i in data)
                {
                    val saved = realm.createObject(BritishDatabase::class.java , idRealm++)
                        saved.idMeal       =   i.idMeal
                        saved.strMeal      =   i.strMeal
                        saved.strMealThumb =   i.strMealThumb
                }
                realm.commitTransaction()
                setupRecViewBritishMeals()
            }
            if (realm.where(BritishDatabase::class.java).findAll().size != data.size)
            {
                realm.beginTransaction()
                realm.deleteAll()
                for (i in data)
                {
                    val saved = realm.createObject(BritishDatabase::class.java , idRealm++)
                    saved.idMeal       =   i.idMeal
                    saved.strMeal      =   i.strMeal
                    saved.strMealThumb =   i.strMealThumb
                }
                realm.commitTransaction()
                setupRecViewBritishMeals()
            }

            //    britishAdapter.differ.submitList(data)
        })
    }
    private fun setupRecViewDessert() {
        binding.recDesserts.apply {
            layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL , false)
            adapter       = dessertsAdapter
        }
    }
    private fun observeGetDessertMeals() {

        val config = RealmConfiguration.Builder().name("dessert.db").build()
        val realm  = Realm.getInstance(config)
        dessertMvvm.getDesertLiveData.observe(viewLifecycleOwner){ data ->

            idRealm = realm.where(DessertDatabase::class.java).findAll().size
            if (realm.where(DessertDatabase::class.java).findAll().isEmpty())
            {
                realm.beginTransaction()

                for (i in data)
                {
                    val saved = realm.createObject(DessertDatabase::class.java , idRealm++)
                        saved.idMeal       = i.idMeal
                        saved.strMeal      = i.strMeal
                        saved.strMealThumb = i.strMealThumb
                }
                realm.commitTransaction()
                setupRecViewDessert()
            }


            if (realm.where(DessertDatabase::class.java).findAll().size != data.size)
            {
                realm.beginTransaction()
                realm.deleteAll()
                for (i in data)
                {
                    val saved = realm.createObject(DessertDatabase::class.java , idRealm++)
                    saved.idMeal       = i.idMeal
                    saved.strMeal      = i.strMeal
                    saved.strMealThumb = i.strMealThumb
                }

                realm.commitTransaction()
                setupRecViewDessert()
            }


        }
    }

}