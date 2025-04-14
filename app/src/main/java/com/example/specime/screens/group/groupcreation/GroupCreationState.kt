package com.example.specime.screens.group.groupcreation

data class GroupCreationState (
    val groupName: String= "",
    val emails: String = "",
    val validatedEmails: List<ValidatedEmail> = emptyList(),
    val validEmails: List<String> = emptyList(),
    val allEmailsValid: Boolean = false,
    val groupNameEmpty: Boolean = false,
    val emailsEmpty: Boolean = false,
    val validatingEmails: Boolean = false,
    val isCreating: Boolean = false,
    val isConfirming: Boolean = false
)

data class ValidatedEmail (
    val email: String,
    val valid: Boolean
)