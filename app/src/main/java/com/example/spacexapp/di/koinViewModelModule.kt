package com.example.spacexapp.di

import com.example.spacexapp.ui.screens.maintabs.historyevents.HistoryEventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinViewModelModule = module {
    viewModel { HistoryEventsViewModel(get(), get()) }
}