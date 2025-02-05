package com.example.specime.screens.auths.signin

import androidx.credentials.CustomCredential

sealed interface SigninAction {
    data class EnterEmail(val email: String) : SigninAction
    data class EnterPassword(val password: String) : SigninAction
    data class EnterAccountEmail(val accountEmail: String) : SigninAction
    data class RememberSignin(val checked: Boolean) : SigninAction
    data class AutoFill(val email: String, val password: String) : SigninAction
    data class GoogleSignin(val credential: CustomCredential) : SigninAction
    data object SubmitAccountEmail : SigninAction
    data object SubmitLogin : SigninAction
    data object CancelForgotPassword : SigninAction
    data object SubmitForgotPassword : SigninAction
    data object StartAuthStateListener : SigninAction
}