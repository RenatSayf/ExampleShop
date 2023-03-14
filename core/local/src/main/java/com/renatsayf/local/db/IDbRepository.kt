package com.renatsayf.local.db

import com.renatsayf.local.models.User
import kotlinx.coroutines.Deferred

interface IDbRepository {

    suspend fun addUserAsync(user: User): Deferred<Result<String>>

    suspend fun updateUserAsync(user: User): Deferred<Result<Int>>

    suspend fun getUserAsync(name: String, password: String): Deferred<Result<User>>

    suspend fun getUserPasswordAsync(email: String): Deferred<Result<String?>>

    suspend fun getAllUsersAsync(): Deferred<List<User>?>
}