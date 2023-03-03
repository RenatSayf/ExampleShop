package com.renatsayf.trade.adapters

import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.renatsayf.network.models.product.Product
import com.renatsayf.trade.databinding.ItemLatestProductBinding


open class LatestDealsAdapter {

    companion object {

        val diffCallback = object : DiffUtil.ItemCallback<Product>(){
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun latestDealsAdapterDelegate(itemClickListener: (Product) -> Unit): AdapterDelegate<List<Product>> {

        return adapterDelegateViewBinding<Product, Product, ItemLatestProductBinding>({layoutInflater, parent ->
            ItemLatestProductBinding.inflate(layoutInflater, parent, false)
        }) {

            bind {
                with(binding) {

                    tvProductName.text = item.name
                    tvCategoryName.text = item.category
                    tvPrice.text = item.price.toString()

                    Glide.with(context).load(item.image_url).into(ivProductImage)
                }
            }
        }
    }


}