package com.example.specime.screens.contacts.subs.friendrequest

sealed interface FriendRequestAction {
    data class AcceptFriendRequest(val senderId: String) : FriendRequestAction
    data class DeclineFriendRequest(val senderId: String) : FriendRequestAction
    data class RetrieveFriendRequest(val recipientId: String) : FriendRequestAction
}