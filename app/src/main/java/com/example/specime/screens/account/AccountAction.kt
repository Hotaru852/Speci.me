package com.example.specime.screens.account

import android.net.Uri

sealed interface AccountAction {
    data class EnterName(val name: String) : AccountAction
    data class EnterEmail(val email: String) : AccountAction
    data class EnterBirthday(val birthday: String) : AccountAction
    data class UploadProfilePicture(val uri: Uri) : AccountAction
    data object SubmitNameChange : AccountAction
    data object SubmitEmailChange : AccountAction
    data object SubmitEditName : AccountAction
    data object SubmitEditEmail : AccountAction
    data object CancelEditName : AccountAction
    data object CancelEditEmail : AccountAction
    data object SubmitPasswordChange : AccountAction
    data object SubmitSignout : AccountAction
    data object CloseConfirmation : AccountAction
}