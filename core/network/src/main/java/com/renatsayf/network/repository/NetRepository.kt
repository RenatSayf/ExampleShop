package com.renatsayf.network.repository

import com.renatsayf.network.data.ApiBuilder
import com.renatsayf.network.data.IApi
import com.renatsayf.network.models.Category
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetRepository @Inject constructor(
    private val api: IApi
) {

    fun getLatestDeals() = flow<Result<LatestDeals>> {
        val body = api.getLatestDealsAsync().body()
        body?.let {
            emit(Result.success(it))
        }?: run {
            emit(Result.failure(Throwable("Empty data")))
        }
    }

    fun getFlashSale() = flow<Result<FlashSales>> {
        val body = api.getFlashSalesAsync().body()
        body?.let {
            emit(Result.success(it))
        }?: run {
            emit(Result.failure(Throwable("Empty data")))
        }
    }

    fun getCategories() = flow {
        val list = Category.get()
        emit(list)
    }
}