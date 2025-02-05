package com.example.specime.screens.notifications.components

import androidx.compose.foundation.layout.Column
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
import com.example.specime.components.common.FlexibleButton
import com.example.specime.components.common.ProfilePicture

@Composable
fun FriendRequestNotification(
    displayName: String,
    profilePictureUrl: String,
    onAccept: () -> Unit,
    onReject: () -> Unit
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
        Column {
            Text(
                text = displayName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.surface,
                fontWeight = FontWeight.Bold
            )
            Row {
                FlexibleButton(
                    text = "Chấp nhận",
                    width = 150,
                    height = 35,
                    onClick = onAccept,
                    rounded = 7,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.surface,
                    textStyle = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                FlexibleButton(
                    text = "Từ chối",
                    width = 150,
                    height = 35,
                    onClick = onReject,
                    rounded = 7,
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}