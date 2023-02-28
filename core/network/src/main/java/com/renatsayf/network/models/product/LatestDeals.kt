package com.renatsayf.network.models.product

import com.renatsayf.network.models.ISales
import java.io.Serializable
import java.util.*

data class LatestDeals(
    val latest: List<Product>
): Serializable, ISales {
    override fun getListTitle(): String {
        return "Latest"
    }


}
