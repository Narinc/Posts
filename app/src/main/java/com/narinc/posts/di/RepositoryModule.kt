package com.narinc.posts.di

import com.narinc.posts.core.DefaultDispatcherProvider
import com.narinc.posts.core.DispatcherProvider
import com.narinc.posts.data.repository.PostRepositoryImpl
import com.narinc.posts.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPostRepository(impl: PostRepositoryImpl): PostRepository

    @Binds
    @Singleton
    abstract fun bindDispatcherProvider(impl: DefaultDispatcherProvider): DispatcherProvider
}