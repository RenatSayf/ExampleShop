package com.renatsayf.network.models.details

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductDetails(
    val colors: List<String>,

    val description: String,

    @SerializedName("image_urls")
    val imageUrls: List<String>,
    val name: String,

    @SerializedName("number_of_reviews")
    val reviews: Int,

    val price: Double,

    val rating: Double
): Serializable