package com.example.specime.screens.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.firestore.FireStoreController
import com.example.specime.userrepository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val fireStoreController: FireStoreController,
    private val userRepository: UserRepository
) : ViewModel() {
    var state by mutableStateOf(TestState())
        private set

    init {
        fetchQuestions()
    }

    private fun resetState() {
        state = TestState()
    }

    private fun fetchQuestions() {
        state = state.copy(isLoading = true) // Set loading state before fetching data
        fireStoreController.fetchQuestions { questions, error ->
            state = if (error != null) {
                state.copy(isLoading = false)
            } else {
                state.copy(
                    questions = questions?.shuffled()?.take(28) ?: emptyList(),
                    isLoading = false
                )
            }
        }
    }

    private fun uploadTestResult() {
        val userId = userRepository.getUserId()

        if (userId != null) {
            val result = state.selectedAnswers.values.groupingBy { it }.eachCount()
            val data: Map<String, Any> = mapOf(
                "userId" to userId,
                "result" to result
            )

            fireStoreController.uploadTestResult(userId, data) { _ -> }
        }
    }

    fun handleAction(action: TestAction) {
        when (action) {
            is TestAction.SelectAnswer -> {
                val selectedAnswers = state.selectedAnswers.toMutableMap()
                selectedAnswers[state.currentQuestion] = action.label
                state = state.copy(
                    selectedAnswers = selectedAnswers,
                    canGoForward = true
                )
            }

            is TestAction.Forward -> {
                if (state.currentQuestion == state.questions.size - 1) {
                    uploadTestResult()
                    state = state.copy(isDone = true)
                    return
                }
                if (state.currentQuestion < state.questions.size - 1) {
                    state = state.copy(
                        currentQuestion = state.currentQuestion + 1,
                        canGoForward = state.selectedAnswers.containsKey(state.currentQuestion + 1),
                        canGoBack = true,
                    )
                }
            }

            is TestAction.Previous -> {
                if (state.currentQuestion > 0) {
                    state = state.copy(
                        currentQuestion = state.currentQuestion - 1,
                        canGoForward = state.selectedAnswers.containsKey(state.currentQuestion - 1),
                        canGoBack = state.currentQuestion - 1 > 0
                    )
                }
            }

            TestAction.OpenHelpDialog -> {
                state = state.copy(
                    showingHelpDialog = true
                )
            }

            TestAction.CloseHelpDialog -> {
                state = state.copy(
                    showingHelpDialog = false
                )
            }
            TestAction.Quit -> {
                state = state.copy(
                    isQuit = true
                )
            }
            TestAction.Retake -> {
                state = state.copy(
                    isRetake = true
                )
            }

            TestAction.Reset -> {
                resetState()
            }
        }
    }
}
