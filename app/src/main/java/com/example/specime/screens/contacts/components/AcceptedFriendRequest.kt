package com.example.specime.screens.contacts.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.specime.components.common.ProfilePicture
import com.example.specime.screens.contacts.subs.friendrequest.FriendRequest

@Composable
fun AcceptedFriendRequest(
    request: FriendRequest
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 15.dp, top = 15.dp)
    ) {
        ProfilePicture(imageUrl = request.profilePictureUrl, imageSize = 50)
        Spacer(modifier = Modifier.width(15.dp))
        Box {
            Text(
                text = request.displayName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 30.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Đã là bạn bè",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 25.dp)
            )
        }
    }
}