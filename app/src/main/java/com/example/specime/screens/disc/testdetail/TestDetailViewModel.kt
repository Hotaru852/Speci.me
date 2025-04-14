package com.example.specime.screens.disc.testdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.firebase.FireStoreController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestDetailViewModel @Inject constructor(
    private val fireStoreController: FireStoreController
) : ViewModel() {
    var state by mutableStateOf(TestDetailState())
        private set

    fun handleAction(action: TestDetailAction) {
        when (action) {
            is TestDetailAction.FetchResultDetail -> {
                fireStoreController.fetchResultDetail(action.resultDetailId) { success, details ->
                    if (success) {
                        state = state.copy(
                            isLoading = false,
                            answeredQuestions = details
                        )
                    }
                }
            }
        }
    }
}
