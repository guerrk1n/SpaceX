package com.app.spacexapp.di

import com.app.spacexapp.model.local.mappers.CrewMemberEntityToCrewMemberMapper
import com.app.spacexapp.model.local.mappers.HistoryEventEntityToHistoryEventMapper
import com.app.spacexapp.model.local.mappers.RocketEntityToRocketDetailMapper
import com.app.spacexapp.model.local.mappers.RocketEntityToRocketMapper
import com.app.spacexapp.model.remote.mappers.CrewMemberResponseToCrewMemberEntityMapper
import com.app.spacexapp.model.remote.mappers.HistoryEventResponseToHistoryEventEntityMapper
import com.app.spacexapp.model.remote.mappers.RocketResponseToRocketEntityMapper
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