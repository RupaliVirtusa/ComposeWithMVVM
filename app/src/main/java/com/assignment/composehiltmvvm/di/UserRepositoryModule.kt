package com.assignment.composehiltmvvm.di

import com.assignment.composehiltmvvm.data.UserApi
import com.assignment.composehiltmvvm.data.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserRepositoryModule {

    @Provides
    fun provideUserRepo(api: UserApi): UserRepository {
        return UserRepository(api)
    }
}