package com.app.spacexapp.di

import com.app.spacexapp.ui.screens.details.rocket.RocketDetailViewModel
import com.app.spacexapp.ui.screens.maintabs.crew.CrewMembersViewModel
import com.app.spacexapp.ui.screens.maintabs.historyevents.HistoryEventsViewModel
import com.app.spacexapp.ui.screens.maintabs.rockets.RocketsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinViewModelModule = module {
    viewModel { HistoryEventsViewModel(get(), get()) }
    viewModel { CrewMembersViewModel(get(), get()) }
    viewModel { RocketsViewModel(get(), get()) }
    viewModel { (rocketId: String) -> RocketDetailViewModel(get(), get(), rocketId) }
}