package com.example.specime.screens.contacts.subs.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.screens.contacts.components.SearchBar
import com.example.specime.screens.contacts.components.SearchEntry

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        SearchBar(
            value = state.query,
            onValueChange = { query ->
                viewModel.handleAction(SearchAction.EnterQuery(query))
            },
            onBackClick = { navController.popBackStack() }
        )
        if (state.query.isEmpty()) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "Hãy nhập ít nhất 1 ký tự",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
        } else {
            if (state.isSearching) {
                Spacer(modifier = Modifier.weight(1f))
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                if (state.searchResults.isEmpty()) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "Không tìm thấy người dùng",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    state.searchResults.forEach { user ->
                        val displayName = user["displayName"] as? String ?: ""
                        val email = user["email"] as? String ?: ""
                        val profilePictureUrl = user["profilePictureUrl"] as? String ?: ""

                        SearchEntry(
                            displayName = displayName,
                            email = email,
                            profilePictureUrl = profilePictureUrl,
                            relationship = state.currentRelationships[user["userId"]]
                                ?: Relationship.NONE,
                            onButtonClick = {
                                viewModel.handleAction(
                                    SearchAction.ChangeRelationship(
                                        user["userId"].toString()
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}