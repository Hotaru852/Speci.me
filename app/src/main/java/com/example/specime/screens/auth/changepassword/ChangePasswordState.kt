package com.example.specime.screens.auth.changepassword

data class ChangePasswordState (
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val currentPasswordError: String? = null,
    val newPasswordError: String? = null,
    val confirmPasswordError: String? = null,
    val isChanged: Boolean = false,
    val isChangingPassword: Boolean = false
)