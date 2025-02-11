package com.example.specime.screens.contacts.subs.friendrequest

data class FriendRequestState (
    val requests: List<FriendRequest> = emptyList(),
    val acceptedRequests: List<String> = emptyList(),
    val pendings: List<PendingRequest> = emptyList(),
    val isLoading: Boolean = false
)

data class FriendRequest(
    val senderId: String,
    val displayName: String,
    val profilePictureUrl: String,
    val timestamp: Long,
    val isAccepted: Boolean = false
)

data class PendingRequest(
    val recipientId: String,
    val displayName: String,
    val profilePictureUrl: String,
    val timestamp: Long
)