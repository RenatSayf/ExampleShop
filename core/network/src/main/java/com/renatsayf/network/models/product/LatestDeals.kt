package com.renatsayf.network.models.product

import com.renatsayf.network.models.ISales
import java.io.Serializable
import java.util.*

data class LatestDeals(
    val latest: List<Product>
): Serializable, ISales {

    override fun getListName(): String {
        val fieldName = latest.javaClass.fields[0].name
        return fieldName
            .replace("_", " ")
            .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
    }
}
