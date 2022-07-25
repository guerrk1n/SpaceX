package com.example.spacexapp.di

import com.example.spacexapp.ui.screens.maintabs.crew.member.CrewMemberMapper
import com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEventMapper
import org.koin.dsl.module

val koinMapperModule = module {
    single { HistoryEventMapper() }
    single { CrewMemberMapper() }
}