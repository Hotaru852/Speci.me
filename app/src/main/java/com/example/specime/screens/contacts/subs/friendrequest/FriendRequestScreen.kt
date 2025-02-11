package com.example.specime.screens.contacts.subs.friendrequest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.screens.contacts.components.CustomTabLayout
import com.example.specime.screens.contacts.components.ReceivedFriendRequestTab
import com.example.specime.screens.contacts.components.SentFriendRequestTab
import com.example.specime.screens.contacts.components.TopBar

@Composable
fun FriendRequestScreen(
    navController: NavController,
    viewModel: FriendRequestViewModel = hiltViewModel()
) {
    val state = viewModel.state

    DisposableEffect(true) {
        onDispose {
            viewModel.removeAcceptedRequests()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(50.dp)
            )
        } else {
            TopBar(
                title = "Lời mời kết bạn",
                onClick = { navController.popBackStack() }
            )
            CustomTabLayout(
                tabTitles = listOf(
                    "Đã nhận" + if (state.requests.isNotEmpty()) " ${state.requests.size}" else "",
                    "Đã gửi" + if (state.pendings.isNotEmpty()) " ${state.pendings.size}" else ""
                ),
                tabContents = listOf(
                    {
                        ReceivedFriendRequestTab(
                            requests = state.requests,
                            onAccept = { senderId ->
                                viewModel.handleAction(FriendRequestAction.AcceptFriendRequest(senderId))
                            },
                            onDecline = { senderId ->
                                viewModel.handleAction(FriendRequestAction.DeclineFriendRequest(senderId))
                            }
                        )
                    },
                    {
                        SentFriendRequestTab(
                            pendings = state.pendings,
                            onRetrieve = { recipientId ->
                                viewModel.handleAction(FriendRequestAction.RetrieveFriendRequest(recipientId))
                            }
                        )
                    }),
                indicatorColor = MaterialTheme.colorScheme.onSurface,
                indicatorHeight = 2
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}