package com.example.specime.screens.contacts.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.screens.contacts.components.CustomTabLayout
import com.example.specime.screens.contacts.components.FakeSearchBar
import com.example.specime.screens.contacts.components.FriendsTab
import com.example.specime.screens.contacts.components.GroupsTab


@Composable
fun ContactsScreen(
    navController: NavController,
    viewModel: ContactsViewModel = hiltViewModel()
) {
    val state = viewModel.state

    BackHandler(enabled = true) {}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(50.dp)
            )
        } else {
            FakeSearchBar(onClick = { navController.navigate("search") })
            CustomTabLayout(
                tabTitles = listOf("Bạn bè", "Nhóm"),
                tabContents = listOf(
                    { FriendsTab(
                        navController = navController,
                        friendRequestCount = state.friendRequestCount,
                        friends = state.friends
                    ) },
                    { GroupsTab() }
                ),
                indicatorColor = MaterialTheme.colorScheme.primary,
                indicatorHeight = 3
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}