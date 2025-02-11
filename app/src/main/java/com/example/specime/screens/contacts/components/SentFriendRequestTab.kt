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
import com.example.specime.screens.contacts.subs.friendrequest.PendingRequest

@Composable
fun SentFriendRequestTab(
    pendings: List<PendingRequest>,
    onRetrieve: (String) -> Unit
) {
    if (pendings.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Bạn chưa gửi lời mời kết bạn nào",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(pendings) { pending ->
                SentFriendRequest(
                    pending = pending,
                    onRetrieve = { onRetrieve(pending.recipientId) }
                )
            }
        }
    }
}
