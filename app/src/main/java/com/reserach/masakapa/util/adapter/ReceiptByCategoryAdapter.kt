package com.reserach.masakapa.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.reserach.masakapa.data.model.category.ReceiptCategoryResponse
import com.reserach.masakapa.data.model.receiptbycategories.ReceiptByCategoriesResponse
import com.reserach.masakapa.databinding.ItemReceiptBinding
import com.reserach.masakapa.databinding.ItemReceiptByCategoriesBinding

class ReceiptByCategoryAdapter : RecyclerView.Adapter<ReceiptByCategoryAdapter.FoodViewHolder>() {

    private val list = ArrayList<ReceiptByCategoriesResponse.Meals>()
    private var onItemClickCallback: ReceiptByCategoryAdapter.OnItemClickCallback? = null

    fun setList(users: ArrayList<ReceiptByCategoriesResponse.Meals>) {
        list.addAll(users)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: ReceiptByCategoryAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class FoodViewHolder(val bind: ItemReceiptByCategoriesBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(data: ReceiptByCategoriesResponse.Meals) {
            bind.apply {
                Glide.with(itemView)
                    .load(data?.strMealThumb)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgFood)

                tvName.text = data?.strMeal

                bind.root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(data)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = ItemReceiptByCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: ReceiptByCategoriesResponse.Meals)
    }



}