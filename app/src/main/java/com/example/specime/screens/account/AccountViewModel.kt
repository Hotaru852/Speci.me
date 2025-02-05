package com.example.specime.screens.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.specime.firestore.FireStoreController
import com.example.specime.screens.auths.components.validateEmail
import com.example.specime.screens.auths.components.validateUsername
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
            fireStoreController.loadUserDetails { success, email, displayName, birthday, profilePictureUrl, isGoogleAccount ->
                state = if (success) {
                    state.copy(
                        currentEmail = email.orEmpty(),
                        oldEmail = email.orEmpty(),
                        currentDisplayName = displayName.orEmpty(),
                        oldDisplayName = displayName.orEmpty(),
                        birthday = birthday ?: "1/1/2000",
                        profilePictureUrl = profilePictureUrl,
                        isLoading = false,
                        isGoogleAccount = isGoogleAccount ?: false
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
            is AccountAction.EnterDisplayName -> {
                state = state.copy(currentDisplayName = action.displayName)
                state = state.copy(nameError = null)
            }

            is AccountAction.EnterEmail -> {
                state = state.copy(currentEmail = action.email)
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

            is AccountAction.SubmitDisplayNameChange -> {
                if (state.currentDisplayName == state.oldDisplayName) {
                    state = state.copy(isEditingName = false)
                    return
                }

                val nameError = validateUsername(state.currentDisplayName)

                if (nameError != null) {
                    state = state.copy(nameError = nameError)
                    return
                }

                state = state.copy(isUploading = true)

                userRepository.updateUserDisplayName(state.currentDisplayName) { success ->
                    if (success) {
                        state = state.copy(
                            oldDisplayName = state.currentDisplayName,
                            isEditingName = false
                        )
                    }
                    state = state.copy(isUploading = false)
                }
            }

            is AccountAction.SubmitEmailChange -> {
                if (state.currentEmail == state.oldEmail) {
                    state = state.copy(isEditingEmail = false)
                    return
                }

                val emailError = validateEmail(state.currentEmail)

                if (emailError != null) {
                    state = state.copy(emailError = emailError)
                    return
                }

                state = state.copy(isUploading = true)

                userRepository.updateEmail(state.currentEmail) { success ->
                    if (success) {
                        state = state.copy(
                            isEditingEmail = false,
                            isConfirming = true
                        )
                    }
                    state = state.copy(isUploading = false)
                }
            }

            is AccountAction.SubmitEditDisplayName -> {
                state = state.copy(isEditingName = true)
            }

            is AccountAction.SubmitEditEmail -> {
                state = state.copy(isEditingEmail = true)
            }

            is AccountAction.CancelEditDisplayName -> {
                state = state.copy(
                    currentDisplayName = state.oldDisplayName,
                    isEditingName = false
                )
            }

            is AccountAction.CancelEditEmail -> {
                state = state.copy(
                    currentEmail = state.oldEmail,
                    isEditingEmail = false,
                    isConfirming = false
                )
            }

            is AccountAction.SubmitPasswordChange -> {
                state = state.copy(isChangingPassword = true)
            }

            is AccountAction.SubmitSignout -> {
                state = state.copy(isSignedOut = true)
                userRepository.signOut()
            }
        }
    }
}