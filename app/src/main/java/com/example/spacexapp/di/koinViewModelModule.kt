package com.example.spacexapp.di

import com.example.spacexapp.ui.screens.maintabs.crew.CrewMembersViewModel
import com.example.spacexapp.ui.screens.maintabs.historyevents.HistoryEventsViewModel
import com.example.spacexapp.ui.screens.maintabs.rockets.RocketsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinViewModelModule = module {
    viewModel { HistoryEventsViewModel(get(), get()) }
    viewModel { CrewMembersViewModel(get(), get()) }
    viewModel { RocketsViewModel(get(), get()) }
}