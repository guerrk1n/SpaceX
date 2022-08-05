package com.example.spacexapp.di

import com.example.spacexapp.model.local.mappers.CrewMemberEntityToCrewMemberMapper
import com.example.spacexapp.model.local.mappers.HistoryEventEntityToHistoryEventMapper
import com.example.spacexapp.model.local.mappers.RocketEntityToRocketDetailMapper
import com.example.spacexapp.model.local.mappers.RocketEntityToRocketMapper
import com.example.spacexapp.model.remote.mappers.CrewMemberResponseToCrewMemberEntityMapper
import com.example.spacexapp.model.remote.mappers.HistoryEventResponseToHistoryEventEntityMapper
import com.example.spacexapp.model.remote.mappers.RocketResponseToRocketEntityMapper
import org.koin.dsl.module

val koinMapperModule = module {
    single { HistoryEventResponseToHistoryEventEntityMapper() }
    single { HistoryEventEntityToHistoryEventMapper() }
    single { CrewMemberResponseToCrewMemberEntityMapper() }
    single { CrewMemberEntityToCrewMemberMapper() }
    single { RocketResponseToRocketEntityMapper() }
    single { RocketEntityToRocketMapper() }
    single { RocketEntityToRocketDetailMapper() }
}