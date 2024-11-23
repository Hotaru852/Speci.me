package com.example.specime.screens.auth.changepassword

sealed interface ChangePasswordAction {
    data class EnterOldPassword(val oldPassword: String) : ChangePasswordAction
    data class EnterNewPassword(val newPassword: String) : ChangePasswordAction
    data class EnterConfirmPassword(val confirmPassword: String) : ChangePasswordAction
    data object SubmitChange : ChangePasswordAction
}