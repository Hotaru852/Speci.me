package com.example.specime.screens.disc.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.firebase.FireStoreController
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

    private fun determineDISCtrait(result: Map<String, Int>): String {
        val sortedTraits = result.entries.sortedByDescending { it.value }
        val primaryTrait = sortedTraits.getOrNull(0)?.key?.first()?.toString() ?: "N/A"
        return primaryTrait
    }

    private fun fetchQuestions() {
        state = state.copy(isLoading = true)
        fireStoreController.fetchQuestions { questions, error ->
            state = if (error != null) {
                state.copy(isLoading = false)
            } else {
                state.copy(
                    questions = (questions ?: emptyList()).shuffled().take(24),
                    isLoading = false
                )
            }
        }
    }

    private fun uploadTestResult(
        groupId: String?
    ) {
        val userId = userRepository.getUserId() ?: return
        val result: Map<String, Int> = state.questionsAnswer
            .filterValues { it }
            .map { it.key.label }
            .groupingBy { it }
            .eachCount()
        val trait = determineDISCtrait(result)
        val detailData: List<Map<String, Any>> = state.questionsAnswer.map { (question, answer) ->
            mapOf(
                "question" to question.question,
                "answer" to answer
            )
        }

        fireStoreController.uploadTestResult(userId, groupId, result, trait, detailData) {
            state = state.copy(
                isUploading = false,
                result = trait,
                isDone = true
            )
        }
    }


    fun handleAction(action: TestAction) {
        when (action) {
            is TestAction.SelectAnswer -> {
                val questionsAnswer = state.questionsAnswer.toMutableMap()
                val question = state.questions.getOrNull(action.index)
                if (question != null) {
                    questionsAnswer[question] = action.answer
                    state = state.copy(questionsAnswer = questionsAnswer)
                }
            }

            is TestAction.Submit -> {
                state = state.copy(isUploading = true)
                uploadTestResult(action.groudId)
            }

            TestAction.Quit -> {
                state = state.copy(
                    isQuitting = true
                )
            }

            TestAction.CancelQuit -> {
                state = state.copy(isQuitting = false)
            }
        }
    }
}