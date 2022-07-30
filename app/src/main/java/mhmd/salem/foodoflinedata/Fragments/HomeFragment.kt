package mhmd.salem.foodoflinedata.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.RealmConfiguration

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch
import mhmd.salem.foodoflinedata.Activites.MainActivity
import mhmd.salem.foodoflinedata.Activites.MealActivity
import mhmd.salem.foodoflinedata.Adapters.*
import mhmd.salem.foodoflinedata.R
import mhmd.salem.foodoflinedata.Realm.BreakFastDatabase
import mhmd.salem.foodoflinedata.Realm.CategoriesDatabase
import mhmd.salem.foodoflinedata.ViewModels.*

import mhmd.salem.foodoflinedata.data.Category
import mhmd.salem.foodoflinedata.data.OverPopular
import mhmd.salem.foodoflinedata.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {


    private lateinit var binding           : FragmentHomeBinding
    private lateinit var seaFoodMvvm       : SeaFoodViewModel
    private lateinit var overMvvm          : OverPopularViewModel
    private lateinit var egyptianMvvm      : EgyptianViewModel
    private lateinit var breakFastMvvm     : BreakFastViewModel
    private lateinit var categoriesMvvm    : CategoriesViewModel

    private lateinit var egyptianAdapter   : EgyptianAdapter
    private lateinit var seaFoodAdapter    : SeaFoodAdapter
    private lateinit var overPopularAdapter: OverPopularAdapter
    private lateinit var breakFastAdapter  : BreakFastAdapter
    private lateinit var categoriesAdapter : CategoriesAdapter


    private val channel = Channel<OverPopular>(Channel.RENDEZVOUS)

    var realmId : Int = 0

    companion object{
        const val MEAL_ID    = "mhmd.salem.foodoflinedata.Fragments.mealId"
        const val MEAL_NAME  = "mhmd.salem.foodoflinedata.Fragments.mealName"
        const val MEAL_THUMB = "mhmd.salem.foodoflinedata.Fragments.mealThumb"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  seaFoodMvvm   =  ViewModelProviders.of(this)[SeaFoodViewModel::class.java]
        //  overMvvm      =  ViewModelProviders.of(this)[OverPopularViewModel::class.java]
        //  egyptianMvvm  =  ViewModelProviders.of(this)[EgyptianViewModel::class.java]
            categoriesMvvm = ViewModelProviders.of(this)[CategoriesViewModel::class.java]
            breakFastMvvm  = ViewModelProviders.of(this)[BreakFastViewModel::class.java]

            egyptianMvvm = (activity as MainActivity).egyptianMvvm
            seaFoodMvvm  = (activity as MainActivity).seaFoodMvvm
            overMvvm     = (activity as MainActivity).overMvvm

            egyptianAdapter    = EgyptianAdapter()
            seaFoodAdapter     = SeaFoodAdapter()
            overPopularAdapter = OverPopularAdapter()
            breakFastAdapter   = BreakFastAdapter(context!!)
            categoriesAdapter  = CategoriesAdapter(context!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater , container , false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        seaFoodMvvm.getSeaFood()
        observeDataSeaFood()
        setupSeaFoodRecView()
        onItemSeaFoodClick()

        overMvvm.getOverPopular()
        observeGetOverPopular()
        setupOverPopularRecView()
        onItemOverClick()

        egyptianMvvm.getEgyptianMeals()
        observeEgyptianMeal()
        setupEgyptianRecView()
        onItemEgyptianClick()

        breakFastMvvm.getBreakFastMeals()
        observerGetBreakFastMeals()
        setupRecViewBreakFast()
        onItemBreakFastClick()

        onSearchImgClick()

        categoriesMvvm.getCategories()
        observeGetCategories()
        setupCategoriesRecView()
        onItemCategoriesClick()

    }

    private fun onItemCategoriesClick() {
        categoriesAdapter.onItemClick = { data ->
            try {
                val fragment = CategoryFragment()
                val bundle   = Bundle()
                    bundle.putString("categoryName" , data.strCategory)
                    fragment.arguments = bundle
                findNavController().navigate(R.id.action_homeFragment_to_categoryFragment , bundle)
            }catch (t:Throwable){
                Log.d("testApp" , "Categories Intent"+t.message.toString())
            }

        }
    }


    private fun setupCategoriesRecView() {
        binding.recCategories.apply {
            layoutManager = GridLayoutManager(context , 3 , RecyclerView.VERTICAL , false)
            adapter       = categoriesAdapter
        }
    }

    private fun observeGetCategories() {

        val config  = RealmConfiguration.Builder().name("categories.realm").build()
        val realm   = Realm.getInstance(config)
            realmId = realm.where(CategoriesDatabase::class.java).findAll().size

        categoriesMvvm.categoriesMealLiveData.observe(viewLifecycleOwner , Observer { data ->
            if (realm.where(CategoriesDatabase::class.java).findAll().isEmpty())
            {
                realm.beginTransaction()
                for (i in data)
                {
                    val saved = realm.createObject(CategoriesDatabase::class.java , realmId++)
                    saved.idCategory             = i.idCategory
                    saved.strCategory            = i.strCategory
                    saved.strCategoryThumb       = i.strCategoryThumb
                    saved.strCategoryDescription = i.strCategoryDescription
                }
                realm.commitTransaction()
                setupCategoriesRecView()
            }
            if (realm.where(CategoriesDatabase::class.java).findAll().size != data.size)
            {
                realm.beginTransaction()
                realm.deleteAll()
                for (i in data)
                {
                    val saved = realm.createObject(CategoriesDatabase::class.java , realmId++)
                    saved.idCategory             = i.idCategory
                    saved.strCategory            = i.strCategory
                    saved.strCategoryThumb       = i.strCategoryThumb
                    saved.strCategoryDescription = i.strCategoryDescription
                }
                realm.commitTransaction()
                setupCategoriesRecView()
            }

            //categoriesAdapter.differ.submitList(data)
        })
    }

    private fun onSearchImgClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }


    private fun onItemBreakFastClick() {
        breakFastAdapter.onItemClick = { data ->
            try {
                val  intent = Intent(context , MealActivity::class.java)
                     intent.putExtra(HomeFragment.MEAL_ID    , data.idMeal)
                     intent.putExtra(HomeFragment.MEAL_NAME  , data.strMeal)
                     intent.putExtra(HomeFragment.MEAL_THUMB , data.strMealThumb)
                startActivity(intent)

            }catch (t:Throwable)
            {
                Snackbar.make(binding.root , "Please Check Your Internet Connections" , Snackbar.LENGTH_SHORT).show()
                Log.d("testApp" ,"BreakFast Item Click" +t.message.toString())
            }
        }
    }

    private fun setupRecViewBreakFast() {
        binding.recBreakfast.apply {
            layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL , false)
            adapter       = breakFastAdapter
        }
    }

    private fun observerGetBreakFastMeals() {
        Realm.init(context)
        val config = RealmConfiguration.Builder().name("breakfast.realm").build()
        val realm  = Realm.getInstance(config)

        lifecycleScope.launch {
            breakFastMvvm.getBreakFastLiveData.observe(viewLifecycleOwner, Observer { apiData ->

                realmId = realm.where(BreakFastDatabase::class.java).findAll().size

                if (realm.where(BreakFastDatabase::class.java).findAll().isEmpty())
                {
                    realm.beginTransaction()
                    for (i in apiData)
                    {
                        val saved = realm.createObject(BreakFastDatabase::class.java , realmId++)
                        saved.idMeal       = i.idMeal
                        saved.strMeal      = i.strMeal
                        saved.strMealThumb = i.strMealThumb
                    }
                    realm.commitTransaction()
                    setupRecViewBreakFast()
                }
                //  setupRecViewBreakFast()

                if (realm.where(BreakFastDatabase::class.java).findAll().size != apiData.size ){
                    realm.beginTransaction()
                    realm.deleteAll()
                    for (i in apiData)
                    {
                        val  saved = realm.createObject(BreakFastDatabase::class.java , i.idMeal)  // <<<<<
                        saved.idMeal         = i.idMeal
                        saved.strMeal        = i.strMeal
                        saved.strMealThumb   = i.strMealThumb
                    }
                    realm.commitTransaction()
                    setupRecViewBreakFast()
                }

                // breakFastAdapter.differ.submitList(apiData)

            })
        }

    }

    private fun onItemEgyptianClick() {
        egyptianAdapter.onItemClick = { data ->
            try {
                val intent = Intent(context , MealActivity::class.java)
                    intent.putExtra(HomeFragment.MEAL_ID    , data.idMeal)
                    intent.putExtra(HomeFragment.MEAL_NAME  , data.strMeal)
                    intent.putExtra(HomeFragment.MEAL_THUMB , data.strMealThumb)
                startActivity(intent)

            }catch (e:Throwable){
                Log.d("testApp" , e.message.toString())
            }
        }
    }

    private fun setupEgyptianRecView() {
        binding.recEgyption.apply {
            layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL , false)
            adapter       =  egyptianAdapter
        }
    }

    private fun observeEgyptianMeal() {
        egyptianMvvm.observeGetEgyptianLiveData().observe(viewLifecycleOwner , Observer { data ->
            egyptianMvvm.upsert(data)
        })
        lifecycleScope.launchWhenStarted {
            egyptianMvvm.getSavedEgyptianMeal.collect{ data ->
                egyptianAdapter.differ.submitList(data)
            }
        }
    }

    private fun onItemOverClick() {
        overPopularAdapter.onItemClick = { data ->
            try {
                val intent = Intent(context , MealActivity::class.java)
                intent.putExtra(HomeFragment.MEAL_ID , data.idMeal)
                intent.putExtra(HomeFragment.MEAL_NAME , data.strMeal)
                intent.putExtra(HomeFragment.MEAL_THUMB , data.strMealThumb)
                startActivity(intent)
            }catch (t:Throwable){
                Log.d("testApp" , t.message.toString())
            }
        }
    }

    private fun setupOverPopularRecView() {
        binding.recOverPoular.apply {
            layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL , false)
            adapter       = overPopularAdapter
        }
    }

    private fun observeGetOverPopular() {
        overMvvm.getOverPopularLiveData.observe(viewLifecycleOwner , Observer { apiData ->
            for (i in apiData)
            {
               val serverData = OverPopular(
                    idMeal       =  i.idMeal,
                    strMeal      =  i.strMeal,
                    strMealThumb =  i.strMealThumb
                )
                overMvvm.upsert(serverData)
                if (apiData!!.size !=  overPopularAdapter.differ.currentList.size)
                {
                    lifecycleScope.launch {
                        delay(1000)
                        overMvvm.deleteOver(serverData)
                        overMvvm.upsert(serverData)
                    }
                }
            }
        })

        lifecycleScope.launchWhenStarted {
            overMvvm.getOverSavedData.collect {   savedData ->
                overPopularAdapter.differ.submitList(savedData)
            }
        }
    }

    private fun observeDataSeaFood() {
       lifecycleScope.launchWhenStarted {
           seaFoodMvvm.seafoodMutableStateFlow.collect{ data ->
               for (i in data)
               {
                   Category(
                       idMeal       = i.idMeal,
                       strMeal      = i.strMeal,
                       strMealThumb = i.strMealThumb).also {
                       serverData ->
                       seaFoodMvvm.insertSeaFood(serverData)
                   }
               }
           }
       }
        lifecycleScope.launchWhenStarted {
            seaFoodMvvm.getSavedSeaFood.collect { savedData ->
                seaFoodAdapter.differ.submitList(savedData)
            }
        }
    }
    private fun onItemSeaFoodClick() {
        seaFoodAdapter.onItemClick = { data ->
            try {
                val intent = Intent(context , MealActivity::class.java)
                intent.putExtra(MEAL_ID     , data.idMeal)
                intent.putExtra(MEAL_NAME   , data.strMeal)
                intent.putExtra(MEAL_THUMB  , data.strMealThumb)
                startActivity(intent)
            }catch (t:Throwable){
                Toast.makeText(context, ""+t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun setupSeaFoodRecView() {
        binding.recSeafood.apply {
            layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL , false)
            adapter       = seaFoodAdapter

        }
    }




}

