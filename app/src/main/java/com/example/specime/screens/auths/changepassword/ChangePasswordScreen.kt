package com.example.specime.screens.auths.changepassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.components.common.FlexibleButton
import com.example.specime.components.common.FlexibleTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordSceen(
    navController: NavController,
    viewModel: ChangePasswordViewmodel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(state.isChanged) {
        if (state.isChanged) {
            val title = "Thành công"
            val message = "Mật khẩu của bản đã được thay đổi"
            val buttonText = "XÁC NHẬN"
            val route = "account"
            navController.navigate("confirmation/$title/$message/$buttonText/$route")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("account") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Trở về",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Đổi mật khẩu",
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(30.dp))
            FlexibleTextField(
                label = "Mật khẩu hiện tại",
                value = state.currentPassword,
                width = 320,
                height = 50,
                rounded = 7,
                onValueChange = { oldPassword ->
                    viewModel.handleAction(ChangePasswordAction.EnterOldPassword(oldPassword))
                },
                leadingIcon = Icons.Filled.Lock,
                errorMessage = state.currentPasswordError,
                isPassword = true
            )
            FlexibleTextField(
                label = "Mật khẩu mới",
                value = state.newPassword,
                width = 320,
                height = 50,
                rounded = 7,
                onValueChange = { newPassword ->
                    viewModel.handleAction(ChangePasswordAction.EnterNewPassword(newPassword))
                },
                leadingIcon = Icons.Filled.Lock,
                errorMessage = state.newPasswordError,
                isPassword = true
            )
            FlexibleTextField(
                label = "Nhập lại mật khẩu mới",
                value = state.confirmPassword,
                width = 320,
                height = 50,
                rounded = 7,
                onValueChange = { confirmPassword ->
                    viewModel.handleAction(ChangePasswordAction.EnterConfirmPassword(confirmPassword))
                },
                leadingIcon = Icons.Filled.Lock,
                errorMessage = state.confirmPasswordError,
                isPassword = true
            )
            Spacer(modifier = Modifier.height(20.dp))
            FlexibleButton(
                text = "Đổi mật khẩu",
                width = 320,
                height = 45,
                onClick = {
                    viewModel.handleAction(ChangePasswordAction.SubmitChange)
                },
                rounded = 40
            )
        }

        if (state.isChangingPassword) {
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
    }
}