package com.renatsayf.network.models.product

import java.io.Serializable

data class Product(
    val category: String = "",
    val image_url: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val discount: Int? = null
): Serializable