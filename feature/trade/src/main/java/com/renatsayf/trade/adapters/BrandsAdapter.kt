package com.renatsayf.trade.adapters

import com.renatsayf.network.models.product.Product


class BrandsAdapter: LatestDealsAdapter() {

    companion object {

        val diffCallback = LatestDealsAdapter.diffCallback
    }

    fun brandsAdapterDelegate(itemClickListener: (Product) -> Unit) = super.latestDealsAdapterDelegate(itemClickListener)
}