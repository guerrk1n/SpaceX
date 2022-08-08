package com.app.core.data.model

import com.app.core.common.Constants
import com.app.core.database.model.HistoryEventEntity
import com.app.core.network.model.NetworkHistoryEvent

fun NetworkHistoryEvent.asEntity() =HistoryEventEntity(
    id = id,
    link = links.article,
    title = title,
    date = date * Constants.MILLISECONDS_IN_SECONDS,
    details = details,
    createdAt = System.currentTimeMillis()
)