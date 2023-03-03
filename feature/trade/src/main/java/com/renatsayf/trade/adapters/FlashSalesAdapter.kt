package com.renatsayf.trade.adapters

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.renatsayf.network.models.product.Product
import com.renatsayf.trade.databinding.ItemFlashSaleProductBinding

class FlashSalesAdapter : LatestDealsAdapter() {

    companion object {

        val diffCallback = LatestDealsAdapter.diffCallback
    }

    fun flashSalesAdapterDelegate(itemClickListener: (Product) -> Unit): AdapterDelegate<List<Product>> {

        return adapterDelegateViewBinding<Product, Product, ItemFlashSaleProductBinding>({layoutInflater, parent ->
            ItemFlashSaleProductBinding.inflate(layoutInflater, parent, false)
        }) {

            bind {

                with(binding) {

                    tvProductName.text = item.name
                    tvCategoryName.text = item.category
                    val discountValue = "${item.discount}% off"
                    tvDiscount.text = discountValue
                    tvPrice.text = item.price.toString()

                    Glide.with(context).load(item.image_url).into(ivProductImage)
                }
            }
        }
    }
}