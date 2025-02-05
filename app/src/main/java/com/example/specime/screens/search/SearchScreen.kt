package com.example.specime.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.screens.search.components.SearchBar
import com.example.specime.screens.search.components.UserEntry

@OptIn(ExperimentalMaterial3Api::class)
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
            .background(MaterialTheme.colorScheme.primary)
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Text(
                    "Tìm kiếm",
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { navController.navigate("connections") }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.graphicsLayer(rotationZ = -90f)
                    )
                }
            }
        )
        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.surface)
        Spacer(modifier = Modifier.height(15.dp))
        SearchBar(
            value = state.query,
            onValueChange = { query ->
                viewModel.handleAction(SearchAction.EnterQuery(query))
            }
        )
        if (state.query.isEmpty()) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "Hãy nhập ít nhất 1 ký tự",
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
        } else {
            if (state.isSearching) {
                Spacer(modifier = Modifier.weight(1f))
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                if (state.searchResults.isEmpty()) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "Không tìm thấy người dùng",
                        color = MaterialTheme.colorScheme.surface,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Người dùng",
                            color = MaterialTheme.colorScheme.surface,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 15.dp, top = 15.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    state.searchResults.forEach { user ->
                        val displayName = user["displayName"] as? String ?: ""
                        val email = user["email"] as? String ?: ""
                        val profilePictureUrl = user["profilePictureUrl"] as? String ?: ""

                        UserEntry(
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