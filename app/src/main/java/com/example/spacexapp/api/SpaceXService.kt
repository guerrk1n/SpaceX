package com.example.spacexapp.api

import com.example.spacexapp.data.QueryBody
import com.example.spacexapp.data.crew.CrewMembersResponse
import com.example.spacexapp.data.historyEvents.HistoryEventsResponse
import com.example.spacexapp.data.rockets.RocketsResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SpaceXService {

    @POST("v4/history/query")
    suspend fun getHistoryEvents(@Body query: QueryBody): HistoryEventsResponse

    @POST("v4/crew/query")
    suspend fun getCrewMembers(@Body query: QueryBody): CrewMembersResponse

    @POST("v4/rockets/query")
    suspend fun getRockets(@Body query: QueryBody): RocketsResponse

}