package com.example.appdemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
import com.example.appdemo.roomDb.LocalDb
import com.example.appdemo.roomDb.UserInfoDao

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserInfoDao(localDb: LocalDb): UserInfoDao {
        return localDb.dao
    }

    @Provides
    @Singleton
    fun provideLocalDb(@ApplicationContext context: Context): LocalDb {
        return LocalDb.getDatabase(context)
    }
}
