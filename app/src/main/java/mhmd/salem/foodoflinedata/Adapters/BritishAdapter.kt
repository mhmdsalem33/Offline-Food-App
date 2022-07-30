package mhmd.salem.foodoflinedata.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.realm.Realm
import io.realm.RealmConfiguration
import mhmd.salem.foodoflinedata.Realm.BritishDatabase
import mhmd.salem.foodoflinedata.data.Egyptian
import mhmd.salem.foodoflinedata.databinding.EgyptianRowBinding
import mhmd.salem.foodoflinedata.databinding.RowRecBinding

class BritishAdapter(val context :Context):RecyclerView.Adapter<BritishAdapter.ViewHolder>() {
    init {
        Realm.init(context)
    }
   private val config =  RealmConfiguration.Builder().name("desserts.realm").build()
   private val realm  =  Realm.getInstance(config)
   private val data   =  realm.where(BritishDatabase::class.java).findAll()

    var onItemClick : ((BritishDatabase) -> Unit) ? = null

    class ViewHolder(val binding : EgyptianRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(EgyptianRowBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(data[position]!!.strMealThumb)
           .into(holder.binding.imgEgyption)

        holder.binding.textName.text = data[position]!!.strMeal

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(data[position]!!)
        }
    }
    override fun getItemCount() = data.size

}