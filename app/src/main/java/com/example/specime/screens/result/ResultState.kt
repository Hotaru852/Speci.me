package com.example.specime.screens.result

data class ResultState (
    val result: Map<String, Int>? = null,
    val isLoading: Boolean = true,
    val testTaken: Boolean = false,
    val personality: String? = null
)