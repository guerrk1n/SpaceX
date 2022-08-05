package com.example.spacexapp.di

import com.example.spacexapp.data.remote.SpaceXService
import org.koin.dsl.module
import retrofit2.Retrofit

val koinApiModule = module {
    single { createApiInstance<SpaceXService>(get()) }
}

inline fun <reified T> createApiInstance(retrofit: Retrofit): T = retrofit.create(T::class.java)