package com.renatsayf.network.models.product

import com.renatsayf.network.models.ISales
import java.io.Serializable
import java.util.*

data class FlashSales(
    val flash_sale: List<Product>
): Serializable, ISales {

    override fun getListTitle(): String {
        return "Flash sale"
    }
}