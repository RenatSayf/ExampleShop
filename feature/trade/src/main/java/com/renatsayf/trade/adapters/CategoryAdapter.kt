package com.renatsayf.trade.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.renatsayf.network.models.Category
import com.renatsayf.trade.databinding.ItemCategoryBinding


object CategoryAdapter {

    fun categoryAdapterDelegate(itemClickListener: (Category) -> Unit) =
        adapterDelegateViewBinding<Category, Category, ItemCategoryBinding>(
            {layoutInflater, parent ->  ItemCategoryBinding.inflate(layoutInflater, parent, false)}
        ) {
            bind {
                with(binding) {

                    tvCategoryName.text = item.name
                    btnCategory.setImageResource(item.imgRes)
                    layoutCategory.setOnClickListener {
                        itemClickListener.invoke(item)
                    }
                }
            }
        }

    val diffCallback = object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }


}