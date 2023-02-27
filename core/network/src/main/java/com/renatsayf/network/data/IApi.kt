package com.renatsayf.network.data

import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface IApi {

    @GET("cc0071a1-f06e-48fa-9e90-b1c2a61eaca")
    suspend fun getLatestDealsAsync(): Response<LatestDeals>

    @GET("a9ceeb6e-416d-4352-bde6-2203416576ac")
    suspend fun getFlashSalesAsync(): Response<FlashSales>
}