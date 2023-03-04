package com.renatsayf.local.db

import android.database.sqlite.SQLiteConstraintException
import com.renatsayf.local.BuildConfig
import com.renatsayf.local.models.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.sql.SQLException
import javax.inject.Inject


class DbRepositoryImpl @Inject constructor(
    private val db: AppDao
): IDbRepository {

    override suspend fun addUserAsync(user: User): Deferred<Result<String>> {
        return coroutineScope {
            async {
                val updatedUser = user.copy(password = "1234")
                try {
                    val id = db.insert(user)
                    if (id > 0) {
                        Result.success(updatedUser.password)
                    }
                    else {
                        Result.failure(Throwable("Unknown error"))
                    }
                }
                catch (e: SQLiteConstraintException) {
                    Result.failure(Throwable("Such a record already exists"))
                }
                catch (e: Exception){
                    if (BuildConfig.DEBUG) e.printStackTrace()
                    Result.failure(Throwable("Unknown error"))
                }
            }
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