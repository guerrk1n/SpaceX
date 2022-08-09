package com.app.feature.crew

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.core.designsystem.theme.colorGreen
import com.app.core.model.CrewMember
import com.app.core.ui.card.*
import com.app.core.ui.text.HyperlinkText

@Composable
fun CrewMemberCard(crewMember: CrewMember) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 25.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(5)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CrewMemberPhotoWithStatus(crewMember)
            CrewMemberInfo(crewMember)
        }
    }
}

@Composable
private fun CrewMemberPhotoWithStatus(crewMember: CrewMember) {
    Column(
        modifier = Modifier.width(85.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        SpaceXCardPhoto(
            modifier = Modifier.height(85.dp),
            model = crewMember.image,
            contentDescriptionName = crewMember.name,
            placeholderRes = R.drawable.ic_profile_photo_placeholder
        )
        CrewMemberStatus(crewMember)
    }
}


@Composable
private fun CrewMemberStatus(crewMember: CrewMember) {
    val backgroundColor = when (crewMember.status) {
        CrewMemberStatus.ACTIVE.value -> colorGreen
        CrewMemberStatus.INACTIVE.value -> Color.Red
        CrewMemberStatus.RETIRED.value -> Color.Yellow
        else -> Color.Gray
    }
    SpaceXCardStatus(
        modifier = Modifier.padding(top = 20.dp),
        status = crewMember.status,
        backgroundColor = backgroundColor
    )

}

@Composable
private fun CrewMemberInfo(crewMember: CrewMember) {
    Column(modifier = Modifier.padding(start = 50.dp)) {
        SpaceXCardHeader(header = stringResource(R.string.spacex_app_crew_overline))
        SpaceXCardTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            title = crewMember.name,
        )
        SpaceXCardDetails(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            details = crewMember.agency,
        )
        CrewMemberWikiLink(crewMember)
    }
}

@Composable
private fun CrewMemberWikiLink(crewMember: CrewMember) {
    val wikipediaText = stringResource(R.string.spacex_app_wikipedia)
    HyperlinkText(
        fullText = wikipediaText,
        linkText = listOf(wikipediaText),
        hyperlinks = listOf(crewMember.wikipedia))
}

@Preview
@Composable
private fun PreviewCrewMemberCard() {
    val crewMember = CrewMember(
        "123",
        "Robert Behnken",
        "NASA",
        "https://imgur.com/0smMgMH.png",
        "https://en.wikipedia.org/wiki/Robert_L._Behnken",
        CrewMemberStatus.ACTIVE.name,
    )
    CrewMemberCard(crewMember)
}