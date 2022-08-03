package com.example.spacexapp.di

import com.example.spacexapp.data.repository.RocketsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module

val koinAppModule = module {
    single { getGson() }
    single { RocketsRepository(get(), get(), get()) }
}

private fun getGson(): Gson {
    return GsonBuilder().create()
}