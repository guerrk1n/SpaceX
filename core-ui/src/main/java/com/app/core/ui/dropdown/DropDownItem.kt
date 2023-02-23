package com.app.core.ui.dropdown

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.core.ui.R

@Composable
fun SpaceXDropdownMenuItemWithCheckedIcon(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int,
    @DrawableRes checkedIconRes: Int = R.drawable.ic_done,
    onClick: () -> Unit,
    showCheckedIcon: () -> Boolean
) {
    DropdownMenuItem(
        modifier = modifier,
        onClick = { onClick.invoke() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(textRes))
            if (showCheckedIcon.invoke()) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(checkedIconRes),
                    contentDescription = stringResource(
                        R.string.spacex_app_rocket_sort_type_selected, textRes
                    ),
                )
            }
        }
    }
}