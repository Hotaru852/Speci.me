package com.example.specime.screens.group.groupcreation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.firebase.FireStoreController
import com.example.specime.firebase.sendCustomEmail
import com.example.specime.screens.authentication.components.validateEmail
import com.example.specime.userrepository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupCreationViewModel @Inject constructor(
    private val fireStoreController: FireStoreController,
    private val userRepository: UserRepository
) : ViewModel() {
    var state by mutableStateOf(GroupCreationState())
        private set

    fun handleAction(action: GroupCreationAction) {
        when (action) {
            is GroupCreationAction.EnterGroupCreationName -> {
                state = state.copy(groupName = action.groupName)
            }

            is GroupCreationAction.EnterEmails -> {
                state = state.copy(emails = action.emails)
            }

            is GroupCreationAction.ValidateEmails -> {
                state = state.copy(emailsEmpty = state.emails.isEmpty())

                if (state.emailsEmpty) return

                if (!state.validatingEmails) {
                    state = state.copy(validatingEmails = true)
                }

                val emailsList = state.emails
                    .split(Regex("[,;\\s]+"))
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }

                val validatedEmails = emailsList.map { email ->
                    ValidatedEmail(
                        email = email,
                        valid = validateEmail(email) == null
                    )
                }

                val allValid = validatedEmails.all { it.valid }

                if (allValid) {
                    state = state.copy(validEmails = emailsList)
                }

                state = state.copy(
                    validatedEmails = validatedEmails,
                    allEmailsValid = allValid
                )
            }

            is GroupCreationAction.CreateGroupCreation -> {
                state = state.copy(groupNameEmpty = state.groupName.isEmpty())

                if (state.groupNameEmpty) return

                state = state.copy(isCreating = true)

                val userDisplayName = userRepository.getUserDisplayName()

                for (email in state.validEmails) {
                    sendCustomEmail(
                        recipient = email,
                        subject = "Speci.me",
                        text = "$userDisplayName đã thêm bạn vào nhóm ${state.groupName}. Hãy tải ứng dụng Speci.me về máy để tham gia làm bài đánh giá tính cách DISC: 👉 https://github.com/Hotaru852/Speci.me/releases"
                    )
                }

                fireStoreController.createGroup(
                    emails = state.validEmails,
                    groupName = state.groupName
                ) { success ->
                    if (success) {
                        state = state.copy(isConfirming = true)
                    }

                    state = state.copy(isCreating = false)
                }
            }
        }
    }
}