package com.example.specime.screens.contacts.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.firestore.FireStoreController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val fireStoreController: FireStoreController
) : ViewModel() {
    var state by mutableStateOf(ContactsState())
        private set

    init {
        state = state.copy(isLoading = true)
        var pendingRequests = 2

        fun onNetworkRequestCompleted() {
            pendingRequests--
            if (pendingRequests == 0) {
                state = state.copy(isLoading = false)
            }
        }

        fireStoreController.fetchFriendRequestCount { success, count ->
            if (success) {
                state = state.copy(friendRequestCount = count)
            }
            onNetworkRequestCompleted()
        }

        fireStoreController.listenForFriendRequests { count ->
            state = state.copy(friendRequestCount = count)
        }

        fireStoreController.fetchFriends { success, friends ->
            if (success) {
                state = state.copy(friends = friends)
            }
            onNetworkRequestCompleted()
        }

        fireStoreController.listenForFriendUpdates { friends ->
            state = state.copy(friends = friends)
        }
    }


    fun handleAction(action: ContactsAction) {
    }

    override fun onCleared() {
        super.onCleared()
        fireStoreController.removeFriendRequestListener()
    }
}