package com.renatsayf.network.repository

import com.renatsayf.network.data.IApi
import com.renatsayf.network.models.Category
import com.renatsayf.network.models.product.Brands
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import com.renatsayf.network.models.words.Hint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetRepositoryImpl @Inject constructor(
    private val api: IApi
): INetRepository {

    override fun getLatestDeals() = flow<Result<LatestDeals>> {
        val body = api.getLatestDealsAsync().body()
        body?.let {
            if (!it.latest.isNullOrEmpty()) {
                emit(Result.success(it))
            }
            else emit(Result.failure(Throwable("Empty data")))
        }?: run {
            emit(Result.failure(Throwable("Empty data")))
        }
    }

    override fun getFlashSale() = flow<Result<FlashSales>> {
        val body = api.getFlashSalesAsync().body()
        body?.let {
            if (!it.flash_sale.isNullOrEmpty()) {
                emit(Result.success(it))
            }
            else emit(Result.failure(Throwable("Empty data")))
        }?: run {
            emit(Result.failure(Throwable("Empty data")))
        }
    }

    override fun getBrands(): Flow<Result<Brands>> {
        return flow {
            val body = api.getBrandsAsync().body()
            body?.let {
                if (!it.brands.isNullOrEmpty()) {
                    emit(Result.success(it))
                }
                else emit(Result.failure(Throwable("Empty data")))
            }?: run {
                emit(Result.failure(Throwable("Empty data")))
            }
        }
    }

    override fun getCategories() = flow {
        val list = Category.get()
        emit(list)
    }

    override fun getWordList(): Flow<Result<Hint>> {
        return flow {
            val hint = api.getWordList().body()
            hint?.let {
                emit(Result.success(it))
            }?: run {
                emit(Result.failure(Throwable("Empty data")))
            }
        }
    }
}