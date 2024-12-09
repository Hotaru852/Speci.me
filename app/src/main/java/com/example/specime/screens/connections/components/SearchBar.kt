package com.example.specime.screens.connections.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Tìm bạn bè",
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    results: List<String>,
    onResultClick: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    var offset by remember { mutableFloatStateOf(0f) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isFocused) Color.Black.copy(alpha = 0.5f) else Color.Transparent)
            .clickable(
                enabled = isFocused,
                onClick = {
                    isFocused = false
                    focusManager.clearFocus()
                }
            )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.height(50.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    BasicTextField(
                        value = value,
                        onValueChange = {
                            onValueChange(it)
                            if (it.isNotEmpty()) isFocused = true
                        },
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search,
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                onSearch()
                            }
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .scrollable(
                                orientation = Orientation.Horizontal,
                                state = rememberScrollableState { delta ->
                                    offset += delta
                                    delta
                                }
                            )
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                isFocused = focusState.isFocused
                            }.padding(start = 10.dp, end = 15.dp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (value.isEmpty()) {
                                    Text(
                                        text = hint,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            }
            AnimatedVisibility(visible = isFocused && value.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .fillMaxWidth()
                ) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    results.forEach { result ->
                        Text(
                            text = result,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .clickable {
                                    onResultClick(result)
                                    isFocused = false
                                    focusManager.clearFocus()
                                },
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}