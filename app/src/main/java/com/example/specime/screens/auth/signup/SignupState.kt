package com.example.specime.screens.auth.signup

data class SignupState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailError: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordEroor: String? = null,
    val isSignedUp: Boolean = false,
    val isSigningUp: Boolean = false
)