package com.ysshin.cpaquiz.data.network.di

import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ysshin.cpaquiz.data.network.api.QuizService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
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
    fun provideNetworkFlipperPlugin() = NetworkFlipperPlugin()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()

    @Singleton
    @ExperimentalSerializationApi
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("BaseUrl") url: String,
    ): Retrofit {
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
    fun provideQuizService(retrofit: Retrofit): QuizService =
        retrofit.create(QuizService::class.java)
}