package com.example.specime.screens.disc.test

sealed interface TestAction {
    data class SelectAnswer(val answer: Boolean, val index: Int): TestAction
    data class Submit(val groupName: String?, val groudId: String?): TestAction
    data object Quit: TestAction
    data object CancelQuit: TestAction
}