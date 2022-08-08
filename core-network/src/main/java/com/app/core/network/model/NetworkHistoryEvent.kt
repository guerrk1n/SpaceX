package com.app.core.network.model

import com.app.core.common.ResponseField
import com.google.gson.annotations.SerializedName

data class NetworkHistoryEvents(
    @SerializedName("docs")
    val historyEvents: List<NetworkHistoryEvent>,
    val totalPages: Int,
    val page: Int,
)

data class NetworkHistoryEvent(
    val id: String,
    val links: Links,
    val title: String,
    @SerializedName(ResponseField.eventDateUnix)
    val date: Long,
    val details: String,
)

data class Links(
    val article: String? = null,
    val reddit: String? = null,
    val wikipedia: String? = null,
)