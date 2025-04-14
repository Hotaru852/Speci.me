package com.example.specime.screens.disc.test

data class TestState (
    val questions: List<Question> = emptyList(),
    val currentQuestion: Int = 0,
    val questionsAnswer: Map<Question, Boolean> = emptyMap(),
    val isLoading: Boolean = true,
    val isQuitting: Boolean = false,
    val isDone: Boolean = false,
    val isUploading: Boolean = false,
    val result: String = ""
)

data class Question (
    val question: String,
    val label: String
)