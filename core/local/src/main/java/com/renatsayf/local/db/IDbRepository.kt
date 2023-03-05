package com.renatsayf.local.db

import com.renatsayf.local.models.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface IDbRepository {

    suspend fun addUserAsync(user: User): Deferred<Result<String>>

    suspend fun getUserAsync(name: String, password: String): Deferred<Result<User>>

    suspend fun getUserPasswordAsync(email: String): Deferred<Result<String?>>
}