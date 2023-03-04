package com.renatsayf.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renatsayf.local.models.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE first_name = :firstName AND password = :password")
    fun get(firstName: String, password: String): User?
}