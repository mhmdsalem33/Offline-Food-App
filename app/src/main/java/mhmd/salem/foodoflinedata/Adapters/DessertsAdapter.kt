package mhmd.salem.foodoflinedata.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.realm.Realm
import io.realm.RealmConfiguration
import mhmd.salem.foodoflinedata.Activites.MealActivity
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_ID
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_NAME
import mhmd.salem.foodoflinedata.Fragments.HomeFragment.Companion.MEAL_THUMB
import mhmd.salem.foodoflinedata.Realm.DessertDatabase

import mhmd.salem.foodoflinedata.databinding.RowRecBinding

class DessertsAdapter(val context : Context):RecyclerView.Adapter<DessertsAdapter.ViewHolder>() {

    init {
        Realm.init(context)
    }
    private val config      = RealmConfiguration.Builder().name("dessert.db").build()
    private val realm       = Realm.getInstance(config)
    private val dessertList = realm.where(DessertDatabase::class.java).findAll()

    class ViewHolder(val binding : RowRecBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(RowRecBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(dessertList[position]!!.strMealThumb)
            .into(holder.binding.imgView)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context , MealActivity::class.java)
                intent.putExtra(MEAL_ID    , dessertList[position]!!.idMeal)
                intent.putExtra(MEAL_NAME  , dessertList[position]!!.strMeal)
                intent.putExtra(MEAL_THUMB , dessertList[position]!!.strMealThumb)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount() = dessertList.size


}