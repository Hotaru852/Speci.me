package com.example.specime.screens.connections

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.specime.screens.connections.components.SearchBar

@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel = hiltViewModel()
) {
    val state = viewModel.state

    BackHandler(enabled = true) {}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        SearchBar(
            value = state.search,
            onValueChange = { search ->
                viewModel.handleAction(FriendsAction.EnterSearch(search))
            },
            onSearch = {},
            results = emptyList(),
            onResultClick = {}
        )
    }
}