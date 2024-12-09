package com.example.specime.screens.result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.firestore.FireStoreController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val fireStoreController: FireStoreController
) : ViewModel() {
    var state by mutableStateOf(ResultState())
        private set

    init {
        fetchResult()
    }

    private fun fetchResult() {
        fireStoreController.fetchUserResult { result, personality, error ->
            state = if (error != null) {
                state.copy(isLoading = false)
            } else {
                state.copy(
                    result = result ?: emptyMap(),
                    personality = personality.orEmpty(),
                    isLoading = false,
                    testTaken = !result.isNullOrEmpty()
                )
            }
        }
    }
}