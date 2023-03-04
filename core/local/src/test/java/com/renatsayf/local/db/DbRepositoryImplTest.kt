package com.renatsayf.local.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.renatsayf.local.models.User
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DbRepositoryImplTest {

    @get:Rule
    val archRule = InstantTaskExecutorRule()

    private lateinit var dao: AppDao
    private lateinit var db: AppDataBase
    private lateinit var repository: DbRepositoryImpl

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.appDao()
        repository = DbRepositoryImpl(dao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addUser() {

        val user = User("xxxxx@yyy.com", "Tom", "Jons", "")
        runBlocking {

            val expectedPassword = "1234"
            val result = repository.addUserAsync(user).await()
            result.onSuccess { password ->
                Assert.assertEquals(expectedPassword, password)
            }

            val expectedString = "Such a record already exists"
            val result2 = repository.addUserAsync(user).await()
            result2.onFailure { err ->
                Assert.assertEquals(expectedString, err.message)
            }
        }
    }

    @Test
    fun getUser_Succes() {

        val user = User("xxxxx@yyy.com", "Tom", "Jons", "")
        runBlocking {

            val expectedPassword = "1234"
            val result = repository.addUserAsync(user).await()
            result.onSuccess { password ->
                Assert.assertEquals(expectedPassword, password)
            }

            val expectedUser = User("xxxxx@yyy.com", "Tom", "Jons", "1234")
            repository.getUser("Tom", "1234").collect { res ->
                res.onSuccess { user ->
                    Assert.assertEquals(expectedUser, user)
                }
            }
        }
    }

    @Test
    fun getUser_Failure() {

        val user = User("xxxxx@yyy.com", "Tom", "Jons", "")
        runBlocking {

            val expectedPassword = "1234"
            val result = repository.addUserAsync(user).await()
            result.onSuccess { password ->
                Assert.assertEquals(expectedPassword, password)
            }

            val expectedString = "No such record was found"
            repository.getUser("XXXXX", "1236").collect { res ->
                res.onFailure { err ->
                    Assert.assertEquals(expectedString, err.message)
                }
            }
        }
    }
}