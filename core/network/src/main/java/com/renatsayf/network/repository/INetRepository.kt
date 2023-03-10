package com.renatsayf.network.repository

import com.renatsayf.network.models.Category
import com.renatsayf.network.models.details.ProductDetails
import com.renatsayf.network.models.product.Brands
import com.renatsayf.network.models.product.FlashSales
import com.renatsayf.network.models.product.LatestDeals
import com.renatsayf.network.models.words.Hint
import kotlinx.coroutines.flow.Flow

interface INetRepository {

    fun getLatestDeals(): Flow<Result<LatestDeals>>

    fun getFlashSale(): Flow<Result<FlashSales>>

    fun getBrands(): Flow<Result<Brands>>

    fun getCategories(): Flow<List<Category>>

    fun getWordList(): Flow<Result<Hint>>

    fun getProductDetails(): Flow<Result<ProductDetails>>
}