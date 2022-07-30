package mhmd.salem.foodoflinedata.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mhmd.salem.foodoflinedata.data.Meal
import mhmd.salem.foodoflinedata.databinding.CategoryRowBinding

class SearchAdapter():RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private val diffUtilCallback = object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return  oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    val differ  = AsyncListDiffer(this , diffUtilCallback)

    lateinit var onItemClick :((Meal) -> Unit)

    class ViewHolder (val binding :CategoryRowBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(CategoryRowBinding.inflate(LayoutInflater.from(parent.context)))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(data.strMealThumb)
            .into(holder.binding.imgCategory)

        holder.binding.txtName.text = data.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(data)
        }
    }
    override fun getItemCount() =  differ.currentList.size

}