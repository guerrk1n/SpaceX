package com.app.spacexapp

import android.app.Application
import com.pluto.Pluto
import com.pluto.plugins.exceptions.PlutoExceptionsPlugin
import com.pluto.plugins.network.PlutoNetworkPlugin
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant

@HiltAndroidApp
class SpaceXApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initPluto()
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
    }
}