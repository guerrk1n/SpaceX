package com.example.spacexapp.di

import com.example.spacexapp.ui.screens.details.rocket.RocketDetailMapper
import com.example.spacexapp.ui.screens.maintabs.crew.member.CrewMemberEntityMapper
import com.example.spacexapp.ui.screens.maintabs.crew.member.CrewMemberResponseMapper
import com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEventMapper
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.RocketEntityMapper
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.RocketResponseMapper
import org.koin.dsl.module

val koinMapperModule = module {
    single { HistoryEventMapper() }
    single { CrewMemberResponseMapper() }
    single { CrewMemberEntityMapper() }
    single { RocketResponseMapper() }
    single { RocketEntityMapper() }
    single { RocketDetailMapper() }
}