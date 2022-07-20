package com.example.spacexapp.di

import com.example.spacexapp.api.HistoryEventsService
import org.koin.dsl.module
import retrofit2.Retrofit

val koinApiModule = module {
    factory { createApiInstance<HistoryEventsService>(get()) }
}

inline fun <reified T> createApiInstance(retrofit: Retrofit): T = retrofit.create(T::class.java)