package com.example.specime.screens.contacts.subs.friendrequest

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.firestore.FireStoreController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendRequestViewModel @Inject constructor(
    private val fireStoreController: FireStoreController
) : ViewModel() {
    var state by mutableStateOf(FriendRequestState())
        private set

    init {
        state = state.copy(isLoading = true)
        var networkRequests = 2

        fun onNetworkRequestCompleted() {
            networkRequests--
            if (networkRequests == 0) {
                state = state.copy(isLoading = false)
            }
        }

        fireStoreController.fetchFriendRequests { success, requests ->
            if (success) {
                state = state.copy(requests = requests)
            }
            onNetworkRequestCompleted()
        }

        fireStoreController.fetchPendings { success, pendings ->
            if (success) {
                state = state.copy(pendings = pendings)
            }
            onNetworkRequestCompleted()
        }
    }

    fun handleAction(action: FriendRequestAction) {
        when (action) {
            is FriendRequestAction.AcceptFriendRequest -> {
                fireStoreController.acceptFriendRequest(action.senderId) { success ->
                    if (success) {
                        state = state.copy(
                            acceptedRequests = state.acceptedRequests + action.senderId,
                            requests = state.requests.map { request ->
                                if (request.senderId == action.senderId) request.copy(isAccepted = true) else request
                            }
                        )
                    }
                }
            }

            is FriendRequestAction.DeclineFriendRequest -> {
                fireStoreController.declineFriendRequest(action.senderId) { success ->
                    if (success) {
                        state = state.copy(
                            requests = state.requests.filterNot { it.senderId == action.senderId }
                        )
                    }
                }
            }

            is FriendRequestAction.RetrieveFriendRequest -> {
                Log.e("Test", "Test")
                fireStoreController.retrieveFriendRequest(action.recipientId) { success ->
                    if (success) {
                        state = state.copy(
                            pendings = state.pendings.filterNot { it.recipientId == action.recipientId }
                        )
                    }
                }
            }
        }
    }

    fun removeAcceptedRequests() {
        state = state.copy(
            requests = state.requests.filterNot { it.senderId in state.acceptedRequests },
            acceptedRequests = emptyList()
        )
    }
}