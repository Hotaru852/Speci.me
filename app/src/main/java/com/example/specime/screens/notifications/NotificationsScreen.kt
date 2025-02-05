package com.example.specime.screens.notifications

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.specime.screens.notifications.components.AcceptedFriendRequestNotification
import com.example.specime.screens.notifications.components.FriendRequestAcceptedNotification
import com.example.specime.screens.notifications.components.FriendRequestNotification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel()
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
                    "Thông báo",
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {

                        navController.navigate("connections")
                    }
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
            color = MaterialTheme.colorScheme.surface
        )
        if (state.isLoading) {
            Spacer(modifier = Modifier.weight(1f))
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        } else {
            if (state.notifications.isEmpty()) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "Bạn không có thông báo nào",
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                state.notifications.forEach { notification ->
                    if (notification.type == NotificationType.FRIEND_REQUEST) {
                        FriendRequestNotification(
                            displayName = notification.displayName,
                            profilePictureUrl = notification.profilePictureUrl,
                            onAccept = {
                                viewModel.handleAction(
                                    NotificationsAction.AcceptFriendRequest(
                                        notification.relatedUserId
                                    )
                                )
                            },
                            onReject = {
                                viewModel.handleAction(
                                    NotificationsAction.RejectFriendRequest(
                                        notification.relatedUserId
                                    )
                                )
                            }
                        )
                    } else if (notification.type == NotificationType.ACCEPTED_FRIEND_REQUEST) {
                        AcceptedFriendRequestNotification(
                            displayName = notification.displayName,
                            profilePictureUrl = notification.profilePictureUrl
                        )
                    } else if (notification.type == NotificationType.FRIEND_REQUEST_ACCEPTED) {
                        FriendRequestAcceptedNotification(
                            displayName = notification.displayName,
                            profilePictureUrl = notification.profilePictureUrl
                        )
                    }
                }
            }
        }
    }
}