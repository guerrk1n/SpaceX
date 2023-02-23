package com.app.core.ui.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.core.ui.R

@Composable
fun DropDownMenuWithTitle(
    modifier: Modifier = Modifier,
    selectedSortType: String,
    content: @Composable ColumnScope.(onItemClick: () -> Unit) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.spacex_app_sort_by, selectedSortType),
            style = MaterialTheme.typography.overline,
        )
        var expanded by remember { mutableStateOf(false) }
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(R.string.spacex_app_select_sort_button_content_description),
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                content {
                    expanded = false
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDropDownMenuWithTitle() {
    DropDownMenuWithTitle(selectedSortType = "Newest") {
        SpaceXDropdownMenuItemWithCheckedIcon(
            titleRes = R.string.spacex_app_back,
            onClick = {},
            showCheckedIcon = { true }
        )
        SpaceXDropdownMenuItemWithCheckedIcon(
            titleRes = R.string.spacex_app_back,
            onClick = {},
            showCheckedIcon = { false }
        )
        SpaceXDropdownMenuItemWithCheckedIcon(
            titleRes = R.string.spacex_app_back,
            onClick = {},
            showCheckedIcon = { true }
        )
    }
}