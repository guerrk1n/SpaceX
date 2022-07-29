package com.example.spacexapp.data.historyEvents

import com.example.spacexapp.util.ResponseField
import com.google.gson.annotations.SerializedName

data class HistoryEventsResponse(
    @SerializedName("docs")
    val historyEvents: List<HistoryEventResponse>,
    val totalPages: Int,
    val page: Int,
)

data class HistoryEventResponse(
    val links: Links,
    val title: String,
    @SerializedName(ResponseField.eventDateUnix)
    val date: Long,
    val details: String,
    val id: String,
)

data class Links(
    val article: String? = null,
    val reddit: String? = null,
    val wikipedia: String? = null,
)