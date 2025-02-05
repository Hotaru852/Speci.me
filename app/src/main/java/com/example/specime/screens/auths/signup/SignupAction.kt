package com.example.specime.screens.auths.signup

sealed interface SignupAction {
    data class EnterEmail(val email: String) : SignupAction
    data class EnterUsername(val username: String) : SignupAction
    data class EnterPassword(val password: String) : SignupAction
    data class EnterConfirmPassword(val confirmPassword: String) : SignupAction
    data object SubmitSignup : SignupAction
}