package com.example.payten_windowsxp_userapp.db.di

import com.example.payten_windowsxp_userapp.db.AppDatabase
import com.example.payten_windowsxp_userapp.db.AppDatabaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(builder: AppDatabaseBuilder): AppDatabase = builder.build()
}