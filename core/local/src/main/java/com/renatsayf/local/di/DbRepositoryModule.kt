package com.renatsayf.local.di

import android.content.Context
import com.renatsayf.local.db.AppDataBase
import com.renatsayf.local.db.IDbRepository
import com.renatsayf.local.db.DbRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DbRepositoryModule {

    @Provides
    fun provideDbRepository(@ApplicationContext context: Context): IDbRepository {
        val appDao = AppDataBase.getInstance(context).appDao()
        return DbRepositoryImpl(appDao)
    }
}