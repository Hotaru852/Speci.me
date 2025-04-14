package com.example.specime.screens.authentication.signin

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.screens.authentication.components.validateEmail
import com.example.specime.screens.authentication.components.validateSigninPassword
import com.example.specime.userrepository.UserRepository
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SigninViewmodel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences,
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

            is SigninAction.AutoFill -> {
                state = state.copy(
                    email = action.email,
                    password = action.password
                )
            }

            is SigninAction.GoogleSignin -> {
                state = state.copy(isLoggingIn = true)

                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(action.credential.data)

                userRepository.signInWithGoogle(googleIdTokenCredential.idToken) { success->
                    if (success) {
                        state = state.copy(isLoggedIn = true)
                    }
                }
            }

            is SigninAction.SubmitAccountEmail -> {
                state = state.copy(isSendingEmail = true)

                val accountEmailError = validateEmail(state.accountEmail)

                if (accountEmailError != null) {
                    state = state.copy(accountEmailError = accountEmailError)
                    return
                }

                userRepository.sendResetPasswordEmail(state.accountEmail) { success ->
                    if (success) {
                        state = state.copy(isForgotPassword = false)
                        state = state.copy(isConfirming = true)
                    }
                    state = state.copy(isSendingEmail = false)
                }
            }

            is SigninAction.SubmitLogin -> {
                val emailError = validateEmail(state.email)
                val passwordError = validateSigninPassword(state.password)

                if (emailError != null || passwordError != null) {
                    state = state.copy(
                        emailError = emailError,
                        passwordError = passwordError
                    )
                    return
                }

                state = state.copy(isLoggingIn = true)

                userRepository.signInWithEmailAndPassword(
                    email = state.email,
                    password = state.password
                ) { success, message ->
                    if (success) {
                        sharedPreferences.edit()
                            .putBoolean("rememberLogin", state.rememberSignin)
                            .apply()
                        state = state.copy(isLoggedIn = true)
                    } else {
                        if (message == "Email chưa được xác minh") {
                            state = state.copy(emailError = message)
                        } else {
                            state = state.copy(emailError = message)
                            state = state.copy(passwordError = message)
                        }
                    }
                    state = state.copy(isLoggingIn = false)
                }
            }

            is SigninAction.SubmitForgotPassword -> {
                state = state.copy(isForgotPassword = true)
            }

            is SigninAction.CancelForgotPassword -> {
                state = state.copy(
                    isForgotPassword = false,
                    isConfirming = false
                )
            }

            is SigninAction.StartAuthStateListener -> {
                userRepository.startAuthStateListener()
            }
        }
    }
}