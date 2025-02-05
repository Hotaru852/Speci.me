package com.example.specime.screens.notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.firestore.FireStoreController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val fireStoreController: FireStoreController
) : ViewModel() {
    var state by mutableStateOf(NotificationsState())
        private set

    init {
        state = state.copy(isLoading = true)
        fireStoreController.fetchNotifications { success, notifications ->
            if (success) {
                state = state.copy(notifications = notifications)
            }
            state = state.copy(isLoading = false)
        }
    }

    fun handleAction(action: NotificationsAction) {
        when (action) {
            is NotificationsAction.AcceptFriendRequest -> {
                state = state.copy(isLoading = true)

                fireStoreController.acceptFriendRequest(action.senderId) { success ->
                    if (success) {
                        val updatedNotifications = state.notifications.map { notification ->
                            if (notification.relatedUserId == action.senderId && notification.type == NotificationType.FRIEND_REQUEST) {
                                notification.copy(type = NotificationType.ACCEPTED_FRIEND_REQUEST)
                            } else {
                                notification
                            }
                        }
                        state = state.copy(notifications = updatedNotifications)
                    }

                    state = state.copy(isLoading = false)
                }
            }

            is NotificationsAction.RejectFriendRequest -> {
                state = state.copy(isLoading = true)

                fireStoreController.rejectFriendRequest(action.senderId) { success ->
                    if (success) {
                        val updatedNotifications =
                            state.notifications.filterNot { it.relatedUserId == action.senderId && it.type == NotificationType.FRIEND_REQUEST }
                        state = state.copy(notifications = updatedNotifications)
                    }

                    state = state.copy(isLoading = false)
                }
            }
        }
    }
}