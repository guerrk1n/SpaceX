package com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spacexapp.R
import com.example.spacexapp.ui.common.text.HyperlinkText
import com.example.spacexapp.util.TimeUtils

@Composable
fun HistoryEventCard(historyEvent: HistoryEvent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 25.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(5)
    ) {
        Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp)) {
            HistoryEventHeader(historyEvent)
            HistoryEventTitle(historyEvent)
            HistoryEventDetails(historyEvent)
            HistoryEventSourceLink(historyEvent)
        }
    }
}

@Composable
private fun HistoryEventHeader(historyEvent: HistoryEvent) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.spacex_app_history_events_overline),
            style = MaterialTheme.typography.overline,
        )
        Text(
            text = TimeUtils.formatDate(historyEvent.date),
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
private fun HistoryEventTitle(historyEvent: HistoryEvent) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        text = historyEvent.title, style = MaterialTheme.typography.h3)
}

@Composable
private fun HistoryEventDetails(historyEvent: HistoryEvent) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        text = historyEvent.details,
        style = MaterialTheme.typography.body2,
    )
}

@Composable
private fun HistoryEventSourceLink(historyEvent: HistoryEvent) {
    historyEvent.link?.let {
        val text = stringResource(id = R.string.spacex_app_source)
        HyperlinkText(
            fullText = text,
            linkText = listOf(text),
            hyperlinks = listOf(it))
    }
}

@Preview
@Composable
private fun Preview() {
    HistoryEventCard(HistoryEvent("http://www.spacex.com/news/2013/02/11/flight-4-launch-update-0",
        "Falcon reaches Earth orbit",
        1222643700,
        "Falcon 1 becomes the first privately developed liquid-fuel rocket to reach Earth orbit."))
}