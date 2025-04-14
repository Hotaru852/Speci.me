package com.example.specime.screens.disc.testdetail

data class TestDetailState (
    val answeredQuestions: List<AnsweredQuestion> = emptyList(),
    val isLoading: Boolean = true
)

data class AnsweredQuestion (
    val question: String,
    val answer: Boolean
)

