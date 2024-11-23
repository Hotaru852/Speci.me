package com.example.specime.screens.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.specime.screens.auth.components.validateEmail
import com.example.specime.screens.auth.components.validateUsername
import com.example.specime.userrepository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var state by mutableStateOf(AccountState())
        private set

    init {
        viewModelScope.launch {
            userRepository.userState.collect { userState ->
                state = state.copy(
                    email = userState.email.orEmpty(),
                    name = userState.displayName.orEmpty()
                )
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
                state = state.copy(birthday = action.birthday)
            }

            is AccountAction.SubmitNameChange -> {
                val nameError = validateUsername(state.name)

                if (nameError != null) {
                    state = state.copy(nameError = nameError)
                    return
                }

                userRepository.updateUsername(state.name) { success, message ->
                    if (success) {
                        state = state.copy(name = state.name)
                        state = state.copy(isEditingName = false)
                    }
                }
            }

            is AccountAction.SubmitEmailChange -> {
                val emailError = validateEmail(state.email)

                if (emailError != null) {
                    state = state.copy(emailError = emailError)
                }

                userRepository.updateEmail(state.email) { success, message ->
                    if (success) {
                        state = state.copy(isEditingEmail = false)
                        state = state.copy(isConfirming = true)
                    }
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