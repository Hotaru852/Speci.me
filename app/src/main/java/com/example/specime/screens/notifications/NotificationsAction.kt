package com.example.specime.screens.notifications

sealed interface NotificationsAction {
    data class AcceptFriendRequest(val senderId: String) : NotificationsAction
    data class RejectFriendRequest(val senderId: String) : NotificationsAction
}