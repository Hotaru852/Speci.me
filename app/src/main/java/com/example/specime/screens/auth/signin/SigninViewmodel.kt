package com.example.specime.screens.auth.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.screens.auth.components.validateEmail
import com.example.specime.screens.auth.components.validateSigninPassword
import com.example.specime.userrepository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SigninViewmodel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var state by mutableStateOf(SigninState())
        private set

    fun handleAction(action: SigninAction) {
        when (action) {
            is SigninAction.EnterEmail -> {
                state = state.copy(email = action.email)
                state = state.copy(emailError = null)
            }

            is SigninAction.EnterPassword -> {
                state = state.copy(password = action.password)
                state = state.copy(passwordError = null)
            }

            is SigninAction.EnterAccountEmail -> {
                state = state.copy(accountEmail = action.accountEmail)
                state = state.copy(accountEmailError = null)
            }

            is SigninAction.RememberSignin -> {
                state = state.copy(rememberSignin = action.checked)
            }

            is SigninAction.SubmitAccountEmail -> {
                val accountEmailError = validateEmail(state.accountEmail)

                if (accountEmailError != null) {
                    state = state.copy(accountEmailError = accountEmailError)
                    return
                }

                userRepository.sendResetPasswordEmail(state.accountEmail) { success, message ->
                    if (success) {
                        state = state.copy(isForgotPassword = false)
                        state = state.copy(isConfirming = true)
                    }
                }
            }

            SigninAction.SubmitLogin -> {
                val emailError = validateEmail(state.email)
                val passwordError = validateSigninPassword(state.password)

                if (emailError != null || passwordError != null) {
                    state = state.copy(
                        emailError = emailError,
                        passwordError = passwordError
                    )
                    return
                }

                userRepository.signInWithEmailAndPassword(
                    email = state.email,
                    password = state.password
                ) { success, message ->
                    if (success) {
                        state = state.copy(isLoggedIn = true)
                    } else {
                        if (message == "Email chưa được xác minh") {
                            state = state.copy(emailError = message)
                        } else {
                            state = state.copy(emailError = message)
                            state = state.copy(passwordError = message)
                        }
                    }
                }
            }

            is SigninAction.SubmitForgotPassword -> {
                state = state.copy(isForgotPassword = true)
            }

            SigninAction.CancelForgotPassword -> {
                state = state.copy(isForgotPassword = false)
            }

            SigninAction.CloseConfirmation -> {
                state = state.copy(isConfirming = false)
            }
        }
    }
}