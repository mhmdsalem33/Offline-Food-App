package mhmd.salem.foodoflinedata.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mhmd.salem.foodoflinedata.data.OverPopular
import mhmd.salem.foodoflinedata.databinding.OverRowBinding

class OverPopularAdapter():RecyclerView.Adapter<OverPopularAdapter.ViewHolder>() {



    var onItemClick : ((OverPopular) -> Unit) ? = null
    private val diffUtilCallback = object :DiffUtil.ItemCallback<OverPopular>(){
        override fun areItemsTheSame(oldItem: OverPopular, newItem: OverPopular): Boolean {
            return  oldItem.idMeal == newItem.idMeal
        }
        override fun areContentsTheSame(oldItem: OverPopular, newItem: OverPopular): Boolean {
           return oldItem == newItem
        }
    }
    val differ =  AsyncListDiffer(this , diffUtilCallback)



    class ViewHolder(val binding : OverRowBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return  ViewHolder(OverRowBinding.inflate(LayoutInflater.from(parent.context) , parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data =  differ.currentList[position]

       Glide.with(holder.itemView)
           .load(data.strMealThumb)
           .into(holder.binding.imgOver)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

    override fun getItemCount() = differ.currentList.size

}