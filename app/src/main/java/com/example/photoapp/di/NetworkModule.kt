package com.example.photoapp.di

import android.content.Context
import com.example.photoapp.remote.PixabayApiService
import com.example.photoapp.remote.RemoteDataSource
import com.example.photoapp.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class NetworkModule {

    companion object {
        const val PIXABAY_API_HTTPS = "https://pixabay.com/api/"
    }
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun providePixabayApiRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PIXABAY_API_HTTPS)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherApiService(retrofit: Retrofit): PixabayApiService =
        retrofit.create(PixabayApiService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(pixabayApiService:PixabayApiService): RemoteDataSource =
        RemoteDataSourceImpl(pixabayApiService)


}