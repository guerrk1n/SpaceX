package com.app.core.network

import com.app.core.network.model.QueryBody
import com.app.core.network.model.NetworkCrewMembers
import com.app.core.network.model.NetworkHistoryEvents
import com.app.core.network.model.NetworkLaunchpads
import com.app.core.network.model.NetworkRockets
import retrofit2.http.Body
import retrofit2.http.POST

interface SpaceXService {

    @POST("v4/history/query")
    suspend fun getHistoryEvents(@Body query: QueryBody): NetworkHistoryEvents

    @POST("v4/crew/query")
    suspend fun getCrewMembers(@Body query: QueryBody): NetworkCrewMembers

    @POST("v4/rockets/query")
    suspend fun getRockets(@Body query: QueryBody): NetworkRockets

    @POST("v4/launchpads/query")
    suspend fun getLaunchpads(@Body query: QueryBody): NetworkLaunchpads

}