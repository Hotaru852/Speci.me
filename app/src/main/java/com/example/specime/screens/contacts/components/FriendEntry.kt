package com.example.specime.screens.contacts.components

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
import com.example.specime.screens.contacts.main.Friend

@Composable
fun FriendEntry(friend: Friend) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
    ) {
        ProfilePicture(imageUrl = friend.profilePictureUrl, imageSize = 50)
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = friend.displayName,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}