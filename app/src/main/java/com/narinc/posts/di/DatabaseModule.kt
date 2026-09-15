package com.narinc.posts.di

import android.content.Context
import androidx.room.Room
import com.narinc.posts.data.local.AppDatabase
import com.narinc.posts.data.local.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "posts.db").build()

    @Provides
    @Singleton
    fun providePostDao(database: AppDatabase): PostDao = database.postDao()
}