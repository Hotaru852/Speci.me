package com.example.specime.screens.group.groupresult

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.firebase.FireStoreController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupResultViewModel @Inject constructor(
    private val fireStoreController: FireStoreController
) : ViewModel() {
    var state by mutableStateOf(GroupResultState())
        private set

    init {
        state = state.copy(isLoading = true)
    }

    fun handleAction(action: GroupResultAction) {
        when (action) {
            is GroupResultAction.Init -> {
                fireStoreController.fetchGroupTestResults(action.groupId) {
                    groupName, currentUserLatestTestResult, memberTestResults ->
                    state = state.copy(
                        groupName = groupName,
                        currentUserLatestTestResult = currentUserLatestTestResult,
                        memberTestResults = memberTestResults,
                        isLoading = false
                    )
                }
            }

            is GroupResultAction.OpenTraitCompatibility -> {
                state = state.copy(
                    isShowingCompatibility = true,
                    selectedCompatibility = action.compatibility
                )
            }

            GroupResultAction.CloseTraitCompatibility -> {
                state = state.copy(
                    isShowingCompatibility = false,
                )
            }
        }
    }
}