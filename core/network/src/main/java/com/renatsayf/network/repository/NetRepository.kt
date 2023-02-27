package com.renatsayf.network.repository

import com.renatsayf.network.data.ApiBuilder
import com.renatsayf.network.data.IApi
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Response

class NetRepository(
    private val api: IApi = ApiBuilder.api
) {
    suspend fun getLatestDealsAsync(): Deferred<Response<LatestDeals>> {
        return coroutineScope {
            async {
                api.getLatestDealsAsync()
            }
        }
    }

    suspend fun getFlashSalesAsync(): Deferred<Response<FlashSales>> {
        return coroutineScope {
            async {
                api.getFlashSalesAsync()
            }
        }
    }
}