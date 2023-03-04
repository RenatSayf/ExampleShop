package com.renatsayf.local.db

import com.renatsayf.local.models.User
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DbRepository @Inject constructor(
    private val db: AppDao
): AppDao {

    override fun insert(user: User): Long {
        return db.insert(user)
    }

    override fun get(firstName: String, password: String): User? {
        return db.get(firstName, password)
    }

    fun addUser(user: User) = flow<Result<Long>> {
        try {
            val res = insert(user)
            emit(Result.success(res))
        } catch (e: Exception) {
            emit(Result.failure(Throwable("Such a record already exists")))
        }
    }

    fun getUser(name: String, password: String) = flow<Result<User>> {
        val user = get(name, password)
        user?.let {
            emit(Result.success(it))
        }?: run {
            emit(Result.failure(Throwable("No such record was found")))
        }
    }
}