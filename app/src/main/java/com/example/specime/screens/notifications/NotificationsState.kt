package com.example.specime.screens.notifications

data class NotificationsState (
    val notifications: List<Notification> = emptyList(),
    val isLoading: Boolean = false
)

data class Notification (
    val relatedUserId: String,
    val displayName: String,
    val profilePictureUrl: String,
    val type: NotificationType,
    val timestamp: Long
)

enum class NotificationType {
    FRIEND_REQUEST,
    ACCEPTED_FRIEND_REQUEST,
    FRIEND_REQUEST_ACCEPTED
}