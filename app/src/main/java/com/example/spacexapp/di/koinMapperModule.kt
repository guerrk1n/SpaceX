package com.example.spacexapp.di

import com.example.spacexapp.ui.screens.details.rocket.RocketDetailMapper
import com.example.spacexapp.ui.screens.maintabs.crew.member.CrewMemberMapper
import com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEventMapper
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.RocketDboMapper
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.RocketResponseMapper
import org.koin.dsl.module

val koinMapperModule = module {
    single { HistoryEventMapper() }
    single { CrewMemberMapper() }
    single { RocketResponseMapper() }
    single { RocketDboMapper() }
    single { RocketDetailMapper() }
}