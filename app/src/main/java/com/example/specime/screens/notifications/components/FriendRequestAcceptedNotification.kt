package com.example.specime.screens.notifications.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.specime.components.common.ProfilePicture

@Composable
fun FriendRequestAcceptedNotification(
    displayName: String,
    profilePictureUrl: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        ProfilePicture(
            imageUrl = profilePictureUrl,
            imageSize = 50
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(displayName)
                }
                append(" đã chấp nhận lời mời kết bạn của bạn")
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.surface,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}