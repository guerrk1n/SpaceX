package com.example.spacexapp.ui.screens.maintabs.crew.member

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.spacexapp.R
import com.example.spacexapp.ui.common.text.HyperlinkText
import com.example.spacexapp.ui.theme.colorGreen
import com.example.spacexapp.ui.theme.googleSansFamily

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
        CrewMemberPhoto(crewMember)
        CrewMemberStatus(crewMember)
    }
}

@Composable
private fun CrewMemberPhoto(crewMember: CrewMember) {
    val contentDescription = stringResource(
        R.string.spacex_app_content_description_photo_crew_member,
        crewMember.name
    )
    AsyncImage(
        modifier = Modifier
            .height(85.dp),
        model = crewMember.image,
        contentDescription = contentDescription,
        placeholder = painterResource(id = R.drawable.ic_profile_photo_placeholder)
    )
}

@Composable
private fun CrewMemberStatus(crewMember: CrewMember) {
    val backgroundColor = when (crewMember.status) {
        CrewMemberStatus.ACTIVE.value -> colorGreen
        CrewMemberStatus.INACTIVE.value -> Color.Red
        CrewMemberStatus.RETIRED.value -> Color.Yellow
        else -> Color.Gray
    }
    Card(
        modifier = Modifier.padding(top = 20.dp),
        shape = RoundedCornerShape(6.dp),
        backgroundColor = backgroundColor,

        ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            text = crewMember.status,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = googleSansFamily
        )
    }
}

@Composable
private fun CrewMemberInfo(crewMember: CrewMember) {
    Column(modifier = Modifier.padding(start = 50.dp)) {
        CrewMemberHeader()
        CrewMemberTitle(crewMember)
        CrewMemberAgency(crewMember)
        CrewMemberWikiLink(crewMember)
    }
}

@Composable
private fun CrewMemberWikiLink(crewMember: CrewMember) {
    val wikipediaText = stringResource(id = R.string.spacex_app_wikipedia)
    HyperlinkText(
        fullText = wikipediaText,
        linkText = listOf(wikipediaText),
        hyperlinks = listOf(crewMember.wikipedia))
}

@Composable
private fun CrewMemberHeader() {
    Text(
        text = stringResource(id = R.string.spacex_app_crew_overline),
        style = MaterialTheme.typography.overline,
    )
}

@Composable
private fun CrewMemberTitle(crewMember: CrewMember) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        text = crewMember.name, style = MaterialTheme.typography.h3)
}

@Composable
private fun CrewMemberAgency(crewMember: CrewMember) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        text = crewMember.agency,
        style = MaterialTheme.typography.body2,
    )
}

@Preview
@Composable
private fun PreviewCrewMemberCard() {
    val crewMember = CrewMember(
        "Robert Behnken",
        "NASA",
        "https://imgur.com/0smMgMH.png",
        "https://en.wikipedia.org/wiki/Robert_L._Behnken",
        CrewMemberStatus.ACTIVE.name,
        "123"
    )
    CrewMemberCard(crewMember)
}