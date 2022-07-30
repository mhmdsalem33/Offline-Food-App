package mhmd.salem.foodoflinedata.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.realm.Realm
import io.realm.RealmConfiguration
import mhmd.salem.foodoflinedata.Realm.BreakFastDatabase
import mhmd.salem.foodoflinedata.databinding.OverRowBinding

class BreakFastAdapter(val context : Context):RecyclerView.Adapter<BreakFastAdapter.ViewHolder>() {

    init {
        Realm.init(context)
    }
    private val config     = RealmConfiguration.Builder().name("breakfast.realm").build()
    private val realm      = Realm.getInstance(config)
    private val savedData  = realm.where(BreakFastDatabase::class.java).findAll()

    var onItemClick : ((BreakFastDatabase) -> Unit) ? = null

    class ViewHolder(val binding : OverRowBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(OverRowBinding.inflate(LayoutInflater.from(parent.context) , parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = savedData[position]
        Glide.with(holder.itemView)
            .load(data!!.strMealThumb)
            .into(holder.binding.imgOver)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

    override fun getItemCount() =  savedData.size


}