package com.example.specime.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.specime.firebase.FireStoreController
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val fireStoreController: FireStoreController
) : ViewModel() {
    var state by mutableStateOf(HomeScreenState())
        private set

    private var userDataLoaded = false
    private var testResultsLoaded = false
    private var groupResultsLoaded = false
    private var groupResultsListener: ListenerRegistration? = null

    private fun updateLoading() {
        state = state.copy(isLoading = !(userDataLoaded && testResultsLoaded && groupResultsLoaded))
    }

    init {
        loadUserData()
        fireStoreController.fetchTestResults { results->
            testResultsLoaded = true
            state = state.copy(testResults = results)
            updateLoading()
        }
        fireStoreController.fetchGroupInformations { results ->
            groupResultsLoaded = true
            state = state.copy(groupInformations = results)
            updateLoading()
        }

        groupResultsListener = fireStoreController.listenForGroupTestResults { updatedGroups ->
            if (!groupResultsLoaded) {
                groupResultsLoaded = true
            }
            state = state.copy(groupInformations = updatedGroups)
            updateLoading()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            fireStoreController.loadUserDetails { success, _, _, _, profilePictureUrl, _ ->
                userDataLoaded = true
                if (success) {
                    state = state.copy(profilePictureUrl = profilePictureUrl)
                }
                updateLoading()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        groupResultsListener?.remove()
        groupResultsListener = null
    }

    fun handleAction(action: HomeScreenAction) {
        state = when (action) {
            HomeScreenAction.ChooseTestOption -> {
                state.copy(isChoosingTestOption = true)
            }

            HomeScreenAction.CancelChooseTestOption -> {
                state.copy(isChoosingTestOption = false)
            }

            HomeScreenAction.OpenTraitDetail -> {
                state.copy(isShowingTraitDetail = true)
            }

            HomeScreenAction.CloseTraitDetail -> {
                state.copy(isShowingTraitDetail = false)
            }
        }
    }
}