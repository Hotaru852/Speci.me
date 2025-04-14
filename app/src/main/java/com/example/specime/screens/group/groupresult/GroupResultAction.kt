package com.example.specime.screens.group.groupresult

sealed interface GroupResultAction {
    data class Init(val groupId: String) : GroupResultAction
    data class OpenTraitCompatibility(val compatibility: Compatibility) : GroupResultAction
    data object CloseTraitCompatibility : GroupResultAction
}