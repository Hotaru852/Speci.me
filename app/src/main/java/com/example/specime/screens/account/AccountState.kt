package com.example.specime.screens.account

data class AccountState (
    val name: String = "",
    val email: String = "",
    val birthday: String = "1/1/2000",
    val nameError: String? = null,
    val emailError: String? = null,
    val isSignedOut: Boolean = false,
    val isChangingPassword: Boolean = false,
    val isEditingName: Boolean = false,
    val isEditingEmail: Boolean = false,
    val isConfirming: Boolean = false
)