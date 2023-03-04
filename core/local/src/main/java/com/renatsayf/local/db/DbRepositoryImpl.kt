package com.renatsayf.local.db

import com.renatsayf.local.models.User
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DbRepositoryImpl @Inject constructor(
    private val db: AppDao
): IDbRepository {

    override fun addUser(user: User) = flow<Result<String>> {
        try {
            val res = db.insert(user)
            if (res > 0) emit(Result.success("1234"))
            else emit(Result.failure(Throwable("Unknown error")))
        } catch (e: Exception) {
            emit(Result.failure(Throwable("Such a record already exists")))
        }
    }

    override fun getUser(name: String, password: String) = flow<Result<User>> {
        val user = db.get(name, password)
        user?.let {
            emit(Result.success(it))
        }?: run {
            emit(Result.failure(Throwable("No such record was found")))
        }
    }
}