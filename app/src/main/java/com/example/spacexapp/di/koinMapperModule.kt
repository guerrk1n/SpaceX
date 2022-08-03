package com.example.spacexapp.di

import com.example.spacexapp.ui.screens.details.rocket.RocketDetailMapper
import com.example.spacexapp.model.local.mappers.CrewMemberEntityMapper
import com.example.spacexapp.model.remote.mappers.CrewMemberResponseMapper
import com.example.spacexapp.model.local.mappers.HistoryEventEntityMapper
import com.example.spacexapp.model.remote.mappers.HistoryEventResponseMapper
import com.example.spacexapp.model.local.mappers.RocketEntityMapper
import com.example.spacexapp.model.remote.mappers.RocketResponseMapper
import org.koin.dsl.module

val koinMapperModule = module {
    single { HistoryEventResponseMapper() }
    single { HistoryEventEntityMapper() }
    single { CrewMemberResponseMapper() }
    single { CrewMemberEntityMapper() }
    single { RocketResponseMapper() }
    single { RocketEntityMapper() }
    single { RocketDetailMapper() }
}