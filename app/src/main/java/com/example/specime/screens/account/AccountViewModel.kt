package com.example.specime.screens.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.specime.firestore.FireStoreController
import com.example.specime.screens.auth.components.validateEmail
import com.example.specime.screens.auth.components.validateUsername
import com.example.specime.userrepository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val fireStoreController: FireStoreController
) : ViewModel() {
    var state by mutableStateOf(AccountState())
        private set

    init {
        loadUserData()
    }
    private fun loadUserData() {
        viewModelScope.launch {
            fireStoreController.loadUserDetails { success, email, displayName, birthday, profilePictureUrl ->
                state = if (success) {
                    state.copy(
                        email = email.orEmpty(),
                        name = displayName.orEmpty(),
                        birthday = birthday ?: "1/1/2000",
                        profilePictureUrl = profilePictureUrl,
                        isLoading = false
                    )
                } else {
                    state.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun handleAction(action: AccountAction) {
        when (action) {
            is AccountAction.EnterName -> {
                state = state.copy(name = action.name)
                state = state.copy(nameError = null)
            }

            is AccountAction.EnterEmail -> {
                state = state.copy(email = action.email)
                state = state.copy(emailError = null)
            }

            is AccountAction.EnterBirthday -> {
                state = state.copy(isUploading = true)
                fireStoreController.uploadUserBirthday(action.birthday) { success ->
                    if (success) {
                        state = state.copy(birthday = action.birthday)
                    }
                    state = state.copy(isUploading = false)
                }
            }

            is AccountAction.UploadProfilePicture -> {
                state = state.copy(isUploadingProfilePicture = true)
                fireStoreController.uploadProfilePicture(action.uri) { success, url ->
                    if (success) {
                        state = state.copy(
                            profilePictureUrl = url
                        )
                    }
                    state = state.copy(isUploadingProfilePicture = false)
                }
            }

            is AccountAction.SubmitNameChange -> {
                state = state.copy(isUploading = true)

                val nameError = validateUsername(state.name)

                if (nameError != null) {
                    state = state.copy(nameError = nameError)
                    return
                }

                userRepository.updateUsername(state.name) { success, _ ->
                    if (success) {
                        state = state.copy(name = state.name)
                        state = state.copy(isEditingName = false)
                    }
                    state = state.copy(isUploading = false)
                }
            }

            is AccountAction.SubmitEmailChange -> {
                state = state.copy(isUploading = true)

                val emailError = validateEmail(state.email)

                if (emailError != null) {
                    state = state.copy(emailError = emailError)
                }

                userRepository.updateEmail(state.email) { success, _ ->
                    if (success) {
                        state = state.copy(isEditingEmail = false)
                        state = state.copy(isConfirming = true)
                    }
                    state = state.copy(isUploading = false)
                }
            }

            is AccountAction.SubmitEditName -> {
                state = state.copy(isEditingName = true)
            }

            is AccountAction.SubmitEditEmail -> {
                state = state.copy(isEditingEmail = true)
            }

            is AccountAction.CancelEditName -> {
                state = state.copy(isEditingName = false)
            }

            is AccountAction.CancelEditEmail -> {
                state = state.copy(isEditingEmail = false)
            }

            is AccountAction.SubmitPasswordChange -> {
                state = state.copy(isChangingPassword = true)
            }

            is AccountAction.SubmitSignout -> {
                state = state.copy(isSignedOut = true)
                userRepository.signOut()
            }

            is AccountAction.CloseConfirmation -> {
                state = state.copy(isConfirming = false)
            }
        }
    }
}