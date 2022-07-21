package com.example.spacexapp.api

import com.example.spacexapp.data.QueryBody
import com.example.spacexapp.data.historyEvents.HistoryEventsResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface HistoryEventsService {

    @POST("v4/history/query")
    suspend fun getHistoryEvents(@Body query: QueryBody): HistoryEventsResponse

}