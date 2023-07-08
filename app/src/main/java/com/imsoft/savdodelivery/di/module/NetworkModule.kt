package com.imsoft.savdodelivery.di.module

import com.imsoft.savdodelivery.data.network.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun getRetrofit(): ApiService {
        return Retrofit.Builder()
            .baseUrl("http://192.168.30.79:5000/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}