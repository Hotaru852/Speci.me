package com.example.specime.screens.auths.signin

data class SigninState(
    val email: String = "",
    val password: String = "",
    val accountEmail: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val accountEmailError: String? = null,
    val isLoggedIn: Boolean = false,
    val rememberSignin: Boolean = false,
    val isForgotPassword: Boolean = false,
    val isConfirming: Boolean = false,
    val isLoggingIn: Boolean = false,
    val isSendingEmail: Boolean = false
)