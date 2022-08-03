package com.example.spacexapp.api

import com.example.spacexapp.data.network.QueryBody
import com.example.spacexapp.data.network.CrewMembersResponse
import com.example.spacexapp.data.network.HistoryEventsResponse
import com.example.spacexapp.data.network.RocketDetailsResponse
import com.example.spacexapp.data.network.RocketsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SpaceXService {

    @POST("v4/history/query")
    suspend fun getHistoryEvents(@Body query: QueryBody): HistoryEventsResponse

    @POST("v4/crew/query")
    suspend fun getCrewMembers(@Body query: QueryBody): CrewMembersResponse

    @POST("v4/rockets/query")
    suspend fun getRockets(@Body query: QueryBody): RocketsResponse

    @GET("v4/rockets/{rocket_id}")
    suspend fun getRocketDetail(@Path("rocket_id") rocketId: String): RocketDetailsResponse

}