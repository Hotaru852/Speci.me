package com.example.specime.screens.contacts.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.specime.screens.contacts.main.Friend

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendsTab(
    navController: NavController,
    friendRequestCount: Int,
    friends: List<Friend>
) {
    val groupedFriends = friends.groupBy { it.displayName.firstOrNull()?.uppercaseChar() ?: '#' }
    val groupedEntries = groupedFriends.entries.toList()
    val listState = rememberLazyListState()
    val headerIndices = remember(groupedFriends) {
        val indices = mutableListOf<Int>()
        var currentIndex = 5
        groupedFriends.forEach { (_, friends) ->
            indices.add(currentIndex)
            currentIndex += 1 + friends.size
        }
        indices
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Section(
                icon = Icons.Filled.People,
                title = "Lời mời kết bạn",
                count = friendRequestCount,
                onClick = { navController.navigate("requests") }
            )
        }
        item {
            Section(
                icon = Icons.Filled.Cake,
                title = "Sinh nhật",
                onClick = {}
            )
        }
        item {
            HorizontalDivider(thickness = 10.dp, color = MaterialTheme.colorScheme.surfaceDim)
        }
        item {
            Surface(
                shape = RoundedCornerShape(30.dp),
                color = MaterialTheme.colorScheme.surfaceDim,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "Tất cả",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    if (friends.isNotEmpty()) {
                        Text(
                            text = " ${friends.size}",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
        item {
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceDim)
        }

        if (friends.isNotEmpty()) {
            groupedEntries.forEachIndexed { index,  (initial, friendList) ->
                val headerIndex = headerIndices[index]

                stickyHeader {
                    val isStuck by remember {
                        derivedStateOf {
                            val firstVisible = listState.firstVisibleItemIndex
                            val stuckHeaderIndex = headerIndices.lastOrNull { it <= firstVisible } ?: -1
                            stuckHeaderIndex == headerIndex
                        }
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shadowElevation = if (isStuck) 5.dp else 0.dp
                    ) {
                        Text(
                            text = initial.toString(),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 15.dp, top = if (isStuck) 5.dp else 15.dp, bottom = 5.dp)
                        )
                    }
                }
                items(friendList) { friend ->
                    FriendEntry(friend = friend)
                }
            }
        }
    }
}