package com.renatsayf.network.repository

import com.renatsayf.network.data.IApi
import com.renatsayf.network.models.Category
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetRepository @Inject constructor(
    private val api: IApi
): INetRepository {

    override fun getLatestDeals() = flow<Result<LatestDeals>> {
        val body = api.getLatestDealsAsync().body()
        body?.let {
            emit(Result.success(it))
        }?: run {
            emit(Result.failure(Throwable("Empty data")))
        }
    }

    override fun getFlashSale() = flow<Result<FlashSales>> {
        val body = api.getFlashSalesAsync().body()
        body?.let {
            emit(Result.success(it))
        }?: run {
            emit(Result.failure(Throwable("Empty data")))
        }
    }

    override fun getCategories() = flow {
        val list = Category.get()
        emit(list)
    }
}