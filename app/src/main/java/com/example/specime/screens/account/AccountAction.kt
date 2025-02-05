package com.example.specime.screens.account

import android.net.Uri

sealed interface AccountAction {
    data class EnterDisplayName(val displayName: String) : AccountAction
    data class EnterEmail(val email: String) : AccountAction
    data class EnterBirthday(val birthday: String) : AccountAction
    data class UploadProfilePicture(val uri: Uri) : AccountAction
    data object SubmitDisplayNameChange : AccountAction
    data object SubmitEmailChange : AccountAction
    data object SubmitEditDisplayName : AccountAction
    data object SubmitEditEmail : AccountAction
    data object CancelEditDisplayName : AccountAction
    data object CancelEditEmail : AccountAction
    data object SubmitPasswordChange : AccountAction
    data object SubmitSignout : AccountAction
}