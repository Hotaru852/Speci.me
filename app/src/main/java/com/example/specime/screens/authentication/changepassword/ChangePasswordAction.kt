package com.example.specime.screens.authentication.changepassword

sealed interface ChangePasswordAction {
    data class EnterOldPassword(val oldPassword: String) : ChangePasswordAction
    data class EnterNewPassword(val newPassword: String) : ChangePasswordAction
    data class EnterConfirmPassword(val confirmPassword: String) : ChangePasswordAction
    data object SubmitChange : ChangePasswordAction
    data object ConfirmChange : ChangePasswordAction
}