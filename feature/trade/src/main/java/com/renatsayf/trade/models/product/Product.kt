package com.renatsayf.trade.models.product

import java.io.Serializable

data class Product(
    val category: String,
    val image_url: String,
    val name: String,
    val price: Int,
    val discount: Int? = null
): Serializable