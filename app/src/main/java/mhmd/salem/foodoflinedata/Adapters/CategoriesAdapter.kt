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
import mhmd.salem.foodoflinedata.Realm.CategoriesDatabase
import mhmd.salem.foodoflinedata.data.Categories
import mhmd.salem.foodoflinedata.databinding.CategoriesRowBinding

class CategoriesAdapter(val context :Context) :RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {


    init {
        Realm.init(context)
    }
    private val config  = RealmConfiguration.Builder().name("categories.realm").build()
    private val realm   = Realm.getInstance(config)
    private val data    = realm.where(CategoriesDatabase::class.java).findAll()

    lateinit var onItemClick :((CategoriesDatabase) -> Unit)

    class ViewHolder(val binding  :CategoriesRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     return  ViewHolder(CategoriesRowBinding.inflate(LayoutInflater.from(parent.context)))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(data[position]!!.strCategoryThumb)
            .into(holder.binding.imgViewCategory)

        holder.binding.textName.text = data[position]!!.strCategory

        holder.itemView.setOnClickListener {
            onItemClick.invoke(data[position]!!)
        }
    }
    override fun getItemCount() = data.size
}