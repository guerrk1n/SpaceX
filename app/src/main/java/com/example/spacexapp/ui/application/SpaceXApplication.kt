package com.example.spacexapp.ui.application

import android.app.Application
import com.example.spacexapp.BuildConfig
import com.example.spacexapp.di.*
import com.pluto.Pluto
import com.pluto.plugins.exceptions.PlutoExceptions
import com.pluto.plugins.exceptions.PlutoExceptionsPlugin
import com.pluto.plugins.network.PlutoNetworkPlugin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant


class SpaceXApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
        initTimber()
        initPluto()
    }

    private fun startKoin() {
        val modules = listOf(
            koinAppModule,
            koinRetrofitModule,
            koinNetworkModule,
            koinApiModule,
            koinViewModelModule,
            koinMapperModule,
            koinDatabaseModule,
        )
        startKoin {
            androidContext(this@SpaceXApplication)
            modules(modules)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG)
            plant(DebugTree())
    }

    private fun initPluto() {
        Pluto.Installer(this)
            .addPlugin(PlutoNetworkPlugin("network"))
            .addPlugin(PlutoExceptionsPlugin("exceptions"))
            .install()

        PlutoExceptions.setExceptionHandler { thread, throwable ->
            Timber.d("Exception", "uncaught exception handled on thread: " + thread.name, throwable)
        }
    }
}