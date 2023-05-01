package com.app.core.ui.text

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.app.core.ui.R

@Composable
fun SpaceXSearchField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    @StringRes titleRes: Int? = null,
    @StringRes placeholderRes: Int? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onSearchClicked: ((String) -> Unit)? = null,
    onCloseClicked: (() -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = modifier,
        value = text.value,
        onValueChange = { newValue ->
            text.value = newValue
            onValueChanged?.let { it(newValue) }
        },
        placeholder = {
            Text(
                modifier = Modifier
                    .alpha(ContentAlpha.medium),
                text = placeholderRes?.let { stringResource(it) } ?: "",
                color = Color.Black
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClicked?.let { it(text.value) }
            }
        ),
        trailingIcon = {
            IconButton(
                onClick = {
                    if (text.value.isNotEmpty()) {
                        text.value = ""
                        onValueChanged?.let { it("") }
                    } else {
                        focusManager.clearFocus()
                        onCloseClicked?.let { it() }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.spacex_app_close_icon_description),
                    tint = Color.Black
                )
            }
        },
        leadingIcon = {
            IconButton(
                modifier = Modifier.alpha(ContentAlpha.medium),
                onClick = {
                    onSearchClicked?.let { it(text.value) }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.spacex_app_search_icon_description),
                    tint = Color.Black
                )
            }
        },
        label = {
            val title = titleRes?.let { stringResource(id = it) } ?: ""
            Text(title)
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        )
    )
}