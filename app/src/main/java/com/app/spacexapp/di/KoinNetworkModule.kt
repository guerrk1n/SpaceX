package com.app.spacexapp.di

import com.app.spacexapp.BuildConfig
import com.github.simonpercic.oklog3.OkLogInterceptor
import com.pluto.plugins.network.PlutoInterceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_OK_HTTP_CLIENT = "MainOkHttpClient"

val koinNetworkModule = module {
    single(named(MAIN_OK_HTTP_CLIENT)) {
        getHttpClient(get(), get())
    }
    single { newOkLogInterceptor() }
    single { PlutoInterceptor() }
}

private fun getHttpClient(
    okLogInterceptor: OkLogInterceptor,
    plutoInterceptor: PlutoInterceptor,
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(okLogInterceptor)
        .addInterceptor(plutoInterceptor)
        .build()
}

private fun newOkLogInterceptor(): OkLogInterceptor {
    return OkLogInterceptor.builder()
        .withRequestHeaders(BuildConfig.DEBUG)
        .withResponseHeaders(BuildConfig.DEBUG)
        .build()
}