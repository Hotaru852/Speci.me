package com.example.specime.screens.group.groupcreation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.components.common.FlexibleButton
import com.example.specime.screens.group.components.EmailInputField
import com.example.specime.screens.group.components.GroupNameField
import com.example.specime.screens.group.components.ValidatedEmailField
import com.example.specime.screens.group.components.SuccessPopup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCreationScreen(
    navController: NavController,
    viewModel: GroupCreationViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = "TẠO NHÓM",
                        color = Color.White,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("home") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Trở về",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innderPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(innderPadding)
        ) {
            GroupNameField(
                value = state.groupName,
                onValueChange = { groupName ->
                    viewModel.handleAction(GroupCreationAction.EnterGroupCreationName(groupName))
                },
                isEmpty = state.groupNameEmpty
            )
            EmailInputField(
                value = state.emails,
                onValueChange = { emails ->
                    viewModel.handleAction(GroupCreationAction.EnterEmails(emails))
                },
                isEmpty = state.emailsEmpty
            )
            ValidatedEmailField(
                validatedEmails = state.validatedEmails,
                visible = state.validatingEmails
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                FlexibleButton(
                    text = if (state.allEmailsValid) "Tạo Nhóm" else "Kểm Tra Email",
                    width = 150,
                    height = 35,
                    onClick = {
                        if (state.allEmailsValid) {
                            viewModel.handleAction(GroupCreationAction.CreateGroupCreation(state.validEmails))
                        } else {
                            viewModel.handleAction(GroupCreationAction.ValidateEmails)
                        }
                    },
                    rounded = 15,
                    containerColor = Color.White,
                    textStyle = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (state.isCreating) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        if (state.isConfirming) {
            SuccessPopup(
                groupName = state.groupName,
                onDismiss = {},
                onClick = { navController.navigate("home") }
            )
        }
    }
}