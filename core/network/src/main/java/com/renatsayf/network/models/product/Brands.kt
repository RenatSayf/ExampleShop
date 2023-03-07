package com.renatsayf.network.models.product

import com.google.gson.annotations.SerializedName
import com.renatsayf.network.models.ISales
import java.io.Serializable

data class Brands(
    @SerializedName("latest")
    val brands: List<Product>?
): Serializable, ISales {
    override fun getListTitle(): String {
        return "Brands"
    }
}