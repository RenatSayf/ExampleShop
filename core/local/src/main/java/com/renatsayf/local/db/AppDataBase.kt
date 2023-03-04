package com.renatsayf.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.renatsayf.local.models.User


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {

        private const val DATABASE = "shop.db"

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {

            return INSTANCE?: synchronized(this) {
                Room.databaseBuilder(
                    context = context.applicationContext,
                    AppDataBase::class.java,
                    name = null
                ).apply {
                    createFromAsset(DATABASE)
                }.build()
            }
        }
    }
}