package com.postmate.di

import com.postmate.data.local.LocalDatabase
import com.postmate.data.remote.GorestAPI
import com.postmate.data.repository.PostRepositoryImpl
import com.postmate.data.repository.UserRepositoryImpl
import com.postmate.domain.repository.IPostRepository
import com.postmate.domain.repository.IUserRepository
import com.postmate.domain.use_cases.FetchActiveUsersUseCase
import com.postmate.domain.use_cases.FetchPostsByUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        api: GorestAPI,
        db: LocalDatabase,
    ): IUserRepository {
        return UserRepositoryImpl(api, db.userDao)
    }

    @Provides
    @Singleton
    fun provideFetchActiveUsersUseCase(repository: IUserRepository): FetchActiveUsersUseCase {
        return FetchActiveUsersUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePostRepository(
        api: GorestAPI,
        db: LocalDatabase,
    ): IPostRepository {
        return PostRepositoryImpl(api, db.postDao)
    }

    @Provides
    @Singleton
    fun provideFetchPostsByUserUseCase(repository: IPostRepository): FetchPostsByUserUseCase {
        return FetchPostsByUserUseCase(repository)
    }
}
