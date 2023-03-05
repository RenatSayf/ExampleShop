package com.renatsayf.network.repository

import com.renatsayf.network.models.Category
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import kotlinx.coroutines.flow.Flow

interface INetRepository {

    fun getLatestDeals(): Flow<Result<LatestDeals>>

    fun getFlashSale(): Flow<Result<FlashSales>>

    fun getCategories(): Flow<List<Category>>
}