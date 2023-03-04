package com.renatsayf.local.di

import android.content.Context
import com.renatsayf.local.db.AppDao
import com.renatsayf.local.db.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDao {
        return AppDataBase.getInstance(context).appDao()
    }
}