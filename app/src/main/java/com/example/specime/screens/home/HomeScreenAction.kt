package com.example.specime.screens.home

sealed interface HomeScreenAction {
    data object ChooseTestOption: HomeScreenAction
    data object CancelChooseTestOption: HomeScreenAction
    data object OpenTraitDetail : HomeScreenAction
    data object CloseTraitDetail : HomeScreenAction
}