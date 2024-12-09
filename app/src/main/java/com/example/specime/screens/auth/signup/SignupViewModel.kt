package com.example.specime.screens.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.screens.auth.components.validateConfirmPassword
import com.example.specime.screens.auth.components.validateEmail
import com.example.specime.screens.auth.components.validateSignupPassword
import com.example.specime.screens.auth.components.validateUsername
import com.example.specime.userrepository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewmodel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var state by mutableStateOf(SignupState())
        private set

    fun handleAction(action: SignupAction) {
        when (action) {
            is SignupAction.EnterEmail -> {
                state = state.copy(email = action.email)
                state = state.copy(emailError = null)
            }

            is SignupAction.EnterUsername -> {
                state = state.copy(username = action.username)
                state = state.copy(usernameError = null)
            }

            is SignupAction.EnterPassword -> {
                state = state.copy(password = action.password)
                state = state.copy(passwordError = null)
            }

            is SignupAction.EnterConfirmPassword -> {
                state = state.copy(confirmPassword = action.confirmPassword)
                state = state.copy(confirmPasswordEroor = null)
            }

            SignupAction.SubmitSignup -> {
                state = state.copy(isSigningUp = true)

                val emailError = validateEmail(state.email)
                val usernameError = validateUsername(state.username)
                val passwordError = validateSignupPassword(state.password)
                val confirmPasswordError =
                    validateConfirmPassword(state.password, state.confirmPassword)

                if (emailError != null || usernameError != null || passwordError != null || confirmPasswordError != null) {
                    state = state.copy(
                        emailError = emailError,
                        usernameError = usernameError,
                        passwordError = passwordError,
                        confirmPasswordEroor = confirmPasswordError
                    )
                    return
                }

                userRepository.signUpWithEmailAndPassword(
                    email = state.email,
                    password = state.password,
                    username = state.username
                ) { success, message ->
                    state = if (success) state.copy(isSignedUp = true)
                    else state.copy(
                        emailError = if (message == "Tài khoản đã tồn tại" || message == "Email không tồn tại") message else null,
                    )
                    state = state.copy(isSigningUp = false)
                }
            }
        }
    }
}