package mhmd.salem.foodoflinedata.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mhmd.salem.foodoflinedata.data.CategoryMeal
import mhmd.salem.foodoflinedata.databinding.CategoryRowBinding


class CategoryMealAdapter():RecyclerView.Adapter<CategoryMealAdapter.ViewHolder>() {

    lateinit var onItemClick :((CategoryMeal) -> Unit)
   private val diffUtilCallback = object :DiffUtil.ItemCallback<CategoryMeal>(){
       override fun areItemsTheSame(oldItem: CategoryMeal, newItem: CategoryMeal): Boolean {
           return oldItem.idMeal == newItem.idMeal
       }
       override fun areContentsTheSame(oldItem: CategoryMeal, newItem: CategoryMeal): Boolean {
            return oldItem == newItem
       }
   }
    val differ = AsyncListDiffer(this, diffUtilCallback)

    class ViewHolder(val binding : CategoryRowBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder((CategoryRowBinding.inflate(LayoutInflater.from(parent.context))))
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

    override fun getItemCount() = differ.currentList.size


}