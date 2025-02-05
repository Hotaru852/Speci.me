package com.example.specime.screens.connections

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectionsViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(ConnectionsState())
        private set

    fun handleAction(action: ConnectionsAction) {
    }
}