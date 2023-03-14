package com.renatsayf.local.db

import androidx.room.*
import com.renatsayf.local.models.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    @Update(onConflict = OnConflictStrategy.REPLACE, entity = User::class)
    suspend fun update(user: User): Int

    @Query("SELECT * FROM users WHERE first_name = :firstName AND password = :password")
    suspend fun get(firstName: String, password: String): User?

    @Query("SELECT password FROM users WHERE email = :email")
    suspend fun getUserPassword(email: String): String?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>?
}