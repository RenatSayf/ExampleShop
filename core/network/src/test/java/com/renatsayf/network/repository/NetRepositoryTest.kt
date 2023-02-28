@file:Suppress("DEPRECATION")

package com.renatsayf.network.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.renatsayf.network.data.IApi
import com.renatsayf.network.models.product.Product
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



@RunWith(AndroidJUnit4::class)
class NetRepositoryTest {

    @get:Rule
    val archRule = InstantTaskExecutorRule()

    private val server = MockWebServer()

    private fun createRetrofit(): IApi {
        return Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IApi::class.java)
    }

    private val repository = NetRepository(createRetrofit())

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getLatestDealsAsync() {

        val body = """{
  "latest": [
    {
      "category": "Phones",
      "name": "Samsung S10",
      "price": 1000,
      "image_url": "https://www.dhresource.com/0x0/f2/albu/g8/M01/9D/19/rBVaV1079WeAEW-AAARn9m_Dmh0487.jpg"
    },
    {
      "category": "Games",
      "name": "Sony Playstation 5",
      "price": 700,
      "image_url": "https://avatars.mds.yandex.net/get-mpic/6251774/img_id4273297770790914968.jpeg/orig"
    },
    {
      "category": "Games",
      "name": "Xbox ONE",
      "price": 500,
      "image_url": "https://www.tradeinn.com/f/13754/137546834/microsoft-xbox-xbox-one-s-1tb-console-additional-controller.jpg"
    },
    {
      "category": "Cars",
      "name": "BMW X6M",
      "price": 35000,
      "image_url": "https://mirbmw.ru/wp-content/uploads/2022/01/manhart-mhx6-700-01.jpg"
    }
  ]
}"""

        val response = MockResponse().apply {
            setResponseCode(200)
            setBody(body)
        }
        server.enqueue(response)

        runBlocking {

            val actualResponse = repository.getLatestDealsAsync().await()
            val actualResult = actualResponse.body()

            val expectedSize = 4
            Assert.assertEquals(expectedSize, actualResult?.latest?.size)

            val actualProduct = actualResult?.latest?.get(0)
            Assert.assertTrue(actualProduct is Product)

            val actualListName = actualResult?.getListTitle()
            Assert.assertEquals("Latest", actualListName)
        }
    }

    @Test
    fun getFlashSalesAsync() {

        val body = """{
  "flash_sale": [
    {
      "category": "Kids",
      "name": "New Balance Sneakers",
      "price": 22.5,
      "discount": 30,
      "image_url": "https://newbalance.ru/upload/iblock/697/iz997hht_nb_02_i.jpg"
    },
    {
      "category": "Kids",
      "name": "Reebok Classic",
      "price": 24,
      "discount": 30,
      "image_url": "https://assets.reebok.com/images/h_2000,f_auto,q_auto,fl_lossy,c_fill,g_auto/3613ebaf6ed24a609818ac63000250a3_9366/Classic_Leather_Shoes_-_Toddler_White_FZ2093_01_standard.jpg"
    }
  ]
}"""
        val response = MockResponse().apply {
            setResponseCode(200)
            setBody(body)
        }
        server.enqueue(response)

        runBlocking {

            val actualResponse = repository.getFlashSalesAsync().await()
            val actualResult = actualResponse.body()

            val expectedSize = 2
            Assert.assertEquals(expectedSize, actualResult?.flash_sale?.size)

            val actualProduct = actualResult?.flash_sale?.get(0)
            Assert.assertTrue(actualProduct is Product)

            val actualListName = actualResult?.getListTitle()
            Assert.assertEquals("Flash sale", actualListName)
        }
    }
}