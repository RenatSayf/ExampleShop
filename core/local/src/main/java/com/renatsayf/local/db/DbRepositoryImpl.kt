package com.renatsayf.local.db

import com.renatsayf.local.models.User
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DbRepositoryImpl @Inject constructor(
    private val db: AppDao
): IDbRepository {

    override fun addUser(user: User) = flow<Result<String>> {

        val updatedUser = user.copy(password = "1234")
        try {
            val res = db.insert(updatedUser)
            if (res > 0) {
                emit(Result.success(updatedUser.password))
            }
            else {
                emit(Result.failure(Throwable("Unknown error")))
            }
        } catch (e: Exception) {
            e.printStackTrace()
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