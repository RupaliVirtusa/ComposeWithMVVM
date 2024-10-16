package com.assignment.composehiltmvvm.di

import com.assignment.composehiltmvvm.data.UserApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://5e510330f2c0d300147c034c.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
    }

    @Provides
    @Singleton
    fun getUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}