package com.example.spacexapp.di

import android.content.Context
import androidx.room.Room
import com.example.spacexapp.data.database.RocketDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val koinDatabaseModule = module {
    single { getRocketDatabase(androidContext()) }
}

private fun getRocketDatabase(context: Context): RocketDatabase {
    return Room.databaseBuilder(context, RocketDatabase::class.java, "rocket_db").build()
}