package com.example.specime.screens.connections

sealed interface FriendsAction {
    data class EnterSearch(val search: String) : FriendsAction
    data object SubmitSearch : FriendsAction
}