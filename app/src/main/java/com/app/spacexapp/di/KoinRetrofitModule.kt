package com.app.spacexapp.di

import com.app.spacexapp.util.Constants
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val koinRetrofitModule = module {
    single { getRetrofit(get(named(MAIN_OK_HTTP_CLIENT)), get()) }
    factory { GsonConverterFactory.create(get()) }
}

private fun getRetrofit(client: OkHttpClient, gsonFactory: GsonConverterFactory): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.Network.BASE_URL)
        .client(client)
        .addConverterFactory(gsonFactory).build()
}