package com.example.specime.screens.disc.sub

sealed interface TestAction {
    data class SelectAnswer(val label: String) : TestAction
    data object Forward: TestAction
    data object Previous: TestAction
    data object OpenHelpDialog: TestAction
    data object CloseHelpDialog: TestAction
    data object Quit: TestAction
    data object Retake: TestAction
    data object Reset: TestAction
}