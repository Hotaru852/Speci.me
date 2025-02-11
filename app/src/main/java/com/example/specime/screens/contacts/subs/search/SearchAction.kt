package com.example.specime.screens.contacts.subs.search

sealed interface SearchAction {
    data class EnterQuery(val query: String) : SearchAction
    data class ChangeRelationship(val userId: String) : SearchAction
}