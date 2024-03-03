package com.ysshin.cpaquiz.core.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ysshin.cpaquiz.core.network.BuildConfig
import com.ysshin.cpaquiz.core.network.api.RemoteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = "https://yoonseopshin.github.io/kicpa-wordquiz/"

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .addNetworkInterceptor(
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            },
        )
        .addInterceptor(ChuckerInterceptor(context))
        .build()

    @Singleton
    @ExperimentalSerializationApi
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, @Named("BaseUrl") url: String): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val mediaType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(json.asConverterFactory(mediaType))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideRemoteApi(retrofit: Retrofit): RemoteApi = retrofit.create(RemoteApi::class.java)
}
