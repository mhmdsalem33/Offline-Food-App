package mhmd.salem.foodoflinedata.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mhmd.salem.foodoflinedata.data.Egyptian
import mhmd.salem.foodoflinedata.databinding.EgyptianRowBinding

class EgyptianAdapter():RecyclerView.Adapter<EgyptianAdapter.ViewHolder>() {



    var onItemClick : ((Egyptian) -> Unit) ? = null

    private  val diffUtilCallback = object :DiffUtil.ItemCallback<Egyptian>(){
        override fun areItemsTheSame(oldItem: Egyptian, newItem: Egyptian): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Egyptian, newItem: Egyptian): Boolean {
            return oldItem == newItem
        }
    }
    val differ  = AsyncListDiffer(this , diffUtilCallback)

    class ViewHolder(val binding : EgyptianRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return  ViewHolder((EgyptianRowBinding.inflate(LayoutInflater.from(parent.context) , parent , false)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data =  differ.currentList[position]
     Glide.with(holder.itemView)
         .load(data.strMealThumb)
         .into(holder.binding.imgEgyption)

        holder.binding.textName.text = data.strMeal

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

    override fun getItemCount() =  differ.currentList.size

}