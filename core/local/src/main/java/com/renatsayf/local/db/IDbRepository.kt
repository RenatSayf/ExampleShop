package com.renatsayf.local.db

import com.renatsayf.local.models.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface IDbRepository {

    suspend fun addUserAsync(user: User): Deferred<Result<String>>

    fun getUser(name: String, password: String): Flow<Result<User>>
}