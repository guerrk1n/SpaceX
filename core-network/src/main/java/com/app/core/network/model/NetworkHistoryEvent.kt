package com.app.core.network.model

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
    @SerializedName(TITLE_FIELD)
    val title: String,
    @SerializedName(DATE_FIELD)
    val date: Long,
    val details: String,
) {
    companion object {
        const val DATE_FIELD = "event_date_unix"
        const val TITLE_FIELD = "title"
    }
}

data class Links(
    val article: String? = null,
    val reddit: String? = null,
    val wikipedia: String? = null,
)