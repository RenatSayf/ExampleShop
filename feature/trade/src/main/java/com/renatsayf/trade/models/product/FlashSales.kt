package com.renatsayf.trade.models.product

import com.renatsayf.trade.models.ISales
import java.io.Serializable
import java.util.*

data class FlashSales(
    val flash_sale: List<Product>
): Serializable, ISales {

    override fun getListName(): String {
        val fieldName = flash_sale.javaClass.fields[0].name
        return fieldName
            .replace("_", " ")
            .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
    }
}