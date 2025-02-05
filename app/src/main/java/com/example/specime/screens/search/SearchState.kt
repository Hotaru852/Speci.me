package com.example.specime.screens.search

data class SearchState(
    val query: String = "",
    val searchResults: List<Map<String, Any>> = emptyList(),
    val isSearching: Boolean = false,
    val currentRelationships: Map<String, Relationship> = emptyMap()
)

enum class Relationship {
    NONE,
    FRIEND,
    PENDING,
    REQUEST_SENT
}