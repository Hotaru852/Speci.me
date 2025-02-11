package com.example.specime.screens.disc.sub

data class TestState (
    val questions: List<Question> = emptyList(),
    val currentQuestion: Int = 0,
    val selectedAnswers: Map<Int, String> = mutableMapOf(),
    val isLoading: Boolean = true,
    val showingHelpDialog: Boolean = false,
    var canGoBack: Boolean = false,
    var canGoForward: Boolean = false,
    val isQuit: Boolean = false,
    val isRetake: Boolean = false,
    val isDone: Boolean = false
)

data class Question (
    val question: String,
    val answers: List<Answer>
)

data class Answer (
    val label: String,
    val description: String
)