package mhmd.salem.foodoflinedata.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mhmd.salem.foodoflinedata.data.Category
import mhmd.salem.foodoflinedata.databinding.RowRecBinding

class SeaFoodAdapter():RecyclerView.Adapter<SeaFoodAdapter.ViewHolder>() {

   private val diffUtilCallback = object :DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
          return oldItem.idMeal == newItem.idMeal
        }
        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this , diffUtilCallback)

    lateinit  var onItemClick : ((Category) -> Unit )


    class ViewHolder(val binding : RowRecBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(RowRecBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = differ.currentList[position]
      Glide.with(holder.itemView)
          .load(data.strMealThumb)
          .into(holder.binding.imgView)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(data)
        }
    }
    override fun getItemCount() =  differ.currentList.size

}