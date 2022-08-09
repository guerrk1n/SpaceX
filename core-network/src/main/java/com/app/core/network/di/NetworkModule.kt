package com.app.core.network.di

import com.app.core.network.BuildConfig
import com.app.core.network.SpaceXService
import com.github.simonpercic.oklog3.OkLogInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pluto.plugins.network.PlutoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.spacexdata.com"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkLogInterceptor(): OkLogInterceptor =
        OkLogInterceptor.builder()
            .withRequestHeaders(BuildConfig.DEBUG)
            .withResponseHeaders(BuildConfig.DEBUG)
            .build()

    @Provides
    @Singleton
    fun providePlutoInterceptor(): PlutoInterceptor = PlutoInterceptor()

    @Provides
    @Singleton
    fun retrofitNetwork(okHttpClient: OkHttpClient, gsonFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        okLogInterceptor: OkLogInterceptor,
        plutoInterceptor: PlutoInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(okLogInterceptor)
            addInterceptor(plutoInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideSpaceXService(retrofit: Retrofit) = createApiInstance<SpaceXService>(retrofit)

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()
}

inline fun <reified T> createApiInstance(retrofit: Retrofit): T = retrofit.create(T::class.java)
