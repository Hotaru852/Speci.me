package com.example.specime.screens.authentication.changepassword

data class ChangePasswordState (
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val currentPasswordError: String? = null,
    val newPasswordError: String? = null,
    val confirmPasswordError: String? = null,
    val isConfirming: Boolean = false,
    val isChangingPassword: Boolean = false
)