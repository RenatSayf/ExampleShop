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

        val user = User(
            email = "xxxxx@yyy.com",
            firstName = "Tom",
            lastName = "Jons",
            password = ""
        )
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
    fun getUser_Success() {

        val user = User(
            email = "xxxxx@yyy.com",
            firstName = "Tom",
            lastName = "Jons",
            password = ""
        )
        runBlocking {

            val expectedPassword = "1234"
            val result = repository.addUserAsync(user).await()
            result.onSuccess { password ->
                Assert.assertEquals(expectedPassword, password)
            }

            val expectedUser = User("xxxxx@yyy.com", "Tom", "Jons", "1234")
            val actualResult = repository.getUserAsync("Tom", "1234").await()
            actualResult.onSuccess { user ->
                Assert.assertEquals(expectedUser, user)
            }

        }
    }

    @Test
    fun getUser_Failure() {

        val user = User(
            email = "xxxxx@yyy.com",
            firstName = "Tom",
            lastName = "Jons",
            password = ""
        )
        runBlocking {

            val expectedPassword = "1234"
            val result = repository.addUserAsync(user).await()
            result.onSuccess { password ->
                Assert.assertEquals(expectedPassword, password)
            }

            val expectedString = "No such record was found"
            val actualResult = repository.getUserAsync("XXXXX", "1236").await()
            actualResult.onFailure { err ->
                Assert.assertEquals(expectedString, err.message)
            }

        }
    }

    @Test
    fun updateUser_Success() {

        val user = User(
            email = "xxxxx@yyy.com",
            firstName = "Tom",
            lastName = "Jons",
            password = ""
        )

        runBlocking {

            val expectedPassword = "1234"
            val result = repository.addUserAsync(user).await()
            result.onSuccess { password ->
                Assert.assertEquals(expectedPassword, password)
            }

            val updatedUser = user.copy(photoPath = "Tom.png")
            val result2 = repository.updateUserAsync(updatedUser).await()
            result2.onSuccess {
                val result3 = repository.getUserAsync(updatedUser.firstName, updatedUser.password).await()
                result3.onSuccess {
                    Assert.assertEquals("Tom.png", it.photoPath)
                }
                result3.onFailure {
                    Assert.assertTrue(false)
                }
            }
            result2.onFailure {
                Assert.assertTrue(false)
            }
        }
    }
}