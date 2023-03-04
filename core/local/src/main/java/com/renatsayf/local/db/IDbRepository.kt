package com.renatsayf.local.db

import com.renatsayf.local.models.User
import kotlinx.coroutines.flow.Flow

interface IDbRepository {

    fun addUser(user: User): Flow<Result<String>>

    fun getUser(name: String, password: String): Flow<Result<User>>
}