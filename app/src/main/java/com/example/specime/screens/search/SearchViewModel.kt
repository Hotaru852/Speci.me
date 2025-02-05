package com.example.specime.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.specime.firestore.FireStoreController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val fireStoreController: FireStoreController
) : ViewModel() {
    var state by mutableStateOf(SearchState())
        private set

    private var searchJob: Job? = null

    init {
        fetchRelationships()
    }

    private fun fetchRelationships() {
        fireStoreController.fetchUserRelationships{ success, friends, pendings, requests ->
            if (success) {
                val currentRelationships = mutableMapOf<String, Relationship>()

                friends?.forEach { userId ->
                    currentRelationships[userId] = Relationship.FRIEND
                }

                pendings?.forEach { userId ->
                    currentRelationships[userId] = Relationship.PENDING
                }

                requests?.forEach { userId ->
                    currentRelationships[userId] = Relationship.REQUEST_SENT
                }

                state = state.copy(currentRelationships = currentRelationships)
            }
        }
    }

    private fun onQueryChanged(query: String, onSearchTriggered: (String) -> Unit) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            state = state.copy(isSearching = true)
            delay(500)
            onSearchTriggered(query)
        }
    }

    fun handleAction(action: SearchAction) {
        when (action) {
            is SearchAction.EnterQuery -> {
                state = state.copy(query = action.query)
                onQueryChanged(action.query) { currentQuery ->
                    fireStoreController.searchUsersByDisplayName(currentQuery) { success, users ->
                        if (success) {
                            state = state.copy(
                                searchResults = users ?: emptyList()
                            )
                        }
                        state = state.copy(isSearching = false)
                    }
                }
            }

            is SearchAction.ChangeRelationship -> {
                val targetUserId = action.userId
                val currentRelationship = state.currentRelationships[targetUserId] ?: Relationship.NONE

                when (currentRelationship) {
                    Relationship.NONE -> {
                        fireStoreController.sendFriendRequest(targetUserId) { success ->
                            if (success) {
                                state = state.copy(
                                    currentRelationships = state.currentRelationships.toMutableMap().apply {
                                        put(targetUserId, Relationship.REQUEST_SENT)
                                    }
                                )
                            }
                        }
                    }
                    Relationship.REQUEST_SENT -> {
                        fireStoreController.cancelFriendRequest(targetUserId) { success ->
                            if (success) {
                                state = state.copy(
                                    currentRelationships = state.currentRelationships.toMutableMap().apply {
                                        put(targetUserId, Relationship.NONE)
                                    }
                                )
                            }
                        }
                    }
                    Relationship.PENDING -> {
                        fireStoreController.acceptFriendRequest(targetUserId) { success ->
                            if (success) {
                                state = state.copy(
                                    currentRelationships = state.currentRelationships.toMutableMap().apply {
                                        put(targetUserId, Relationship.FRIEND)
                                    }
                                )
                            }
                        }
                    }
                    Relationship.FRIEND -> {
                        fireStoreController.removeFriend(targetUserId) { success ->
                            if (success) {
                                state = state.copy(
                                    currentRelationships = state.currentRelationships.toMutableMap().apply {
                                        put(targetUserId, Relationship.NONE)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}