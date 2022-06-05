package com.reserach.masakapa.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.reserach.masakapa.data.model.category.ReceiptCategoryResponse
import com.reserach.masakapa.databinding.ItemReceiptBinding
import java.util.*
import kotlin.collections.ArrayList

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(), Filterable {

    private var list = ArrayList<ReceiptCategoryResponse.CategoryDetailResponse>()
    private var listAll = ArrayList<ReceiptCategoryResponse.CategoryDetailResponse>()
    private var onItemClickCallback: OnItemClickCallback? = null


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<ReceiptCategoryResponse.CategoryDetailResponse>) {
        listAll.addAll(users)
        list = listAll
        notifyDataSetChanged()
    }

    fun clear() {
        listAll.clear()
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val bind: ItemReceiptBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(data: ReceiptCategoryResponse.CategoryDetailResponse) {
            bind.apply {
                Glide.with(itemView)
                        .load(data.strCategoryThumb)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(imgHomeTopcampaign)

                tvReceiptName.text = data.strCategory

                bind.root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(data)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = ItemReceiptBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: ReceiptCategoryResponse.CategoryDetailResponse)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    list = listAll
                } else {
                    val resultList = ArrayList<ReceiptCategoryResponse.CategoryDetailResponse>()
                    for (row in listAll) {
                        if (row.strCategory.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    list = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = list
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list = results?.values as ArrayList<ReceiptCategoryResponse.CategoryDetailResponse>
                notifyDataSetChanged()
            }

        }
    }
}