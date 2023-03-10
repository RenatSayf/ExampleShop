package com.renatsayf.network.data

import com.renatsayf.network.models.details.ProductDetails
import com.renatsayf.network.models.product.Brands
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import com.renatsayf.network.models.words.Hint
import retrofit2.Response
import retrofit2.http.GET

interface IApi {

    @GET("cc0071a1-f06e-48fa-9e90-b1c2a61eaca7")
    suspend fun getLatestDealsAsync(): Response<LatestDeals>

    @GET("a9ceeb6e-416d-4352-bde6-2203416576ac")
    suspend fun getFlashSalesAsync(): Response<FlashSales>

    @GET("cc0071a1-f06e-48fa-9e90-b1c2a61eaca7")
    suspend fun getBrandsAsync(): Response<Brands>

    @GET("4c9cd822-9479-4509-803d-63197e5a9e19")
    suspend fun getWordList(): Response<Hint>

    @GET("f7f99d04-4971-45d5-92e0-70333383c239")
    suspend fun getProductDetails(): Response<ProductDetails>
}