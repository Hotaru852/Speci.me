package com.example.specime.screens.contacts.main

data class ContactsState(
    val friends: List<Friend> = emptyList(),
    val friendCount: Int = 0,
    val friendRequestCount: Int = 0,
    val isLoading: Boolean = false
)

data class Friend(
    val userId: String,
    val displayName: String,
    val profilePictureUrl: String
)