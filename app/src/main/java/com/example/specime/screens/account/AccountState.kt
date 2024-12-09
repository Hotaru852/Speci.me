package com.example.specime.screens.account

data class AccountState (
    val name: String = "",
    val email: String = "",
    val birthday: String = "",
    val profilePictureUrl: String? = null,
    val nameError: String? = null,
    val emailError: String? = null,
    val isSignedOut: Boolean = false,
    val isChangingPassword: Boolean = false,
    val isEditingName: Boolean = false,
    val isEditingEmail: Boolean = false,
    val isConfirming: Boolean = false,
    val isUploadingProfilePicture: Boolean = false,
    val isUploading: Boolean = false,
    val isLoading: Boolean = true
)