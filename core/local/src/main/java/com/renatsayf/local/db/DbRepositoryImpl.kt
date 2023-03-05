package com.renatsayf.local.db

import android.database.sqlite.SQLiteConstraintException
import com.renatsayf.local.BuildConfig
import com.renatsayf.local.models.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject


class DbRepositoryImpl @Inject constructor(
    private val db: AppDao
): IDbRepository {

    override suspend fun addUserAsync(user: User): Deferred<Result<String>> {
        return coroutineScope {
            async {
                val updatedUser = user.copy(password = "1234")
                try {
                    val id = db.insert(updatedUser)
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

    override suspend fun getUserAsync(name: String, password: String): Deferred<Result<User>> {
        return coroutineScope {
            async {
                val user = db.get(name, password)
                user?.let {
                    Result.success(it)
                }?: run {
                    Result.failure(Throwable("No such record was found"))
                }
            }
        }
    }

}