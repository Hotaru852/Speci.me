package com.example.specime.screens.group.groupcreation

sealed interface GroupCreationAction {
    data class EnterGroupCreationName(val groupName: String) : GroupCreationAction
    data class EnterEmails(val emails: String) : GroupCreationAction
    data class CreateGroupCreation(val emails: List<String>) : GroupCreationAction
    data object ValidateEmails : GroupCreationAction
}