package com.example.spacexapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module

val koinAppModule = module {
    single { getGson() }
}

private fun getGson(): Gson {
    return GsonBuilder().create()
}