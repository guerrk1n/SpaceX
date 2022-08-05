package com.app.spacexapp.data.remote

import com.app.spacexapp.model.remote.QueryBody
import com.app.spacexapp.model.remote.responses.CrewMembersResponse
import com.app.spacexapp.model.remote.responses.HistoryEventsResponse
import com.app.spacexapp.model.remote.responses.RocketsResponse
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