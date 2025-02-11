package com.example.specime.screens.contacts.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.specime.screens.contacts.subs.friendrequest.FriendRequest

@Composable
fun ReceivedFriendRequestTab(
    requests: List<FriendRequest>,
    onAccept: (String) -> Unit,
    onDecline: (String) -> Unit
) {
    if (requests.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Bạn không có lời mời kết bạn nào",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(requests) { request ->
                if (request.isAccepted) {
                    AcceptedFriendRequest(request)
                } else {
                    ReceivedFriendRequest(
                        request = request,
                        onAccept = { onAccept(request.senderId) },
                        onDecline = { onDecline(request.senderId) }
                    )
                }
            }
        }
    }
}