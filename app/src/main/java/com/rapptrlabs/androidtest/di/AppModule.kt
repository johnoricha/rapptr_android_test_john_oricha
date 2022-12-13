package com.rapptrlabs.androidtest.di

import com.rapptrlabs.androidtest.repository.Repository
import com.rapptrlabs.androidtest.repository.RepositoryImpl
import com.rapptrlabs.androidtest.api.ApiService
import com.rapptrlabs.androidtest.api.ApiService.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideChatService(): ApiService {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideChatRepository(chatService: ApiService): Repository {
        return RepositoryImpl(chatService)
    }

}