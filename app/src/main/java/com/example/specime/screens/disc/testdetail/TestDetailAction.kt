package com.example.specime.screens.disc.testdetail

sealed interface TestDetailAction {
    data class FetchResultDetail(val resultDetailId: String) : TestDetailAction
}