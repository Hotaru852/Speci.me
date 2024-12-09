package com.example.specime.screens.auth.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.screens.auth.components.FlexibleButton
import com.example.specime.components.common.PopupConfirmation
import com.example.specime.components.common.PopupEdit
import com.example.specime.components.common.FlexibleTextField
import com.example.specime.screens.auth.components.CheckBox

@Composable
fun SigninScreen(
    navController: NavController,
    viewModel: SigninViewmodel = hiltViewModel(),
) {
    val state = viewModel.state

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            navController.navigate("disc")
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Đăng Nhập",
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(30.dp))
        FlexibleTextField(
            label = "Email",
            value = state.email,
            width = 320,
            height = 50,
            rounded = 7,
            onValueChange = { email ->
                viewModel.handleAction(SigninAction.EnterEmail(email))
            },
            leadingIcon = Icons.Filled.Email,
            errorMessage = state.emailError
        )
        FlexibleTextField(
            label = "Mật khẩu",
            value = state.password,
            width = 320,
            height = 50,
            rounded = 7,
            onValueChange = { password ->
                viewModel.handleAction(SigninAction.EnterPassword(password))
            },
            errorMessage = state.passwordError,
            leadingIcon = Icons.Filled.Lock,
            isPassword = true,
            isLogin = true,
            forgotPassword = {
                viewModel.handleAction(SigninAction.SubmitForgotPassword)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        CheckBox(
            label = "Ghi nhớ đăng nhập",
            checked = state.rememberSignin,
            onCheckedChange = { isChecked ->
                viewModel.handleAction(SigninAction.RememberSignin(isChecked))
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        FlexibleButton(
            text = "ĐĂNG NHẬP",
            width = 320,
            height = 45,
            onClick = {
                viewModel.handleAction(SigninAction.SubmitLogin)
            },
            rounded = 40
        )
//        Spacer(modifier = Modifier.height(20.dp))
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            HorizontalDivider(
//                color = MaterialTheme.colorScheme.surface,
//                thickness = 2.dp,
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(start = 170.dp)
//            )
//            Text(
//                "HOẶC",
//                color = MaterialTheme.colorScheme.surface,
//                style = MaterialTheme.typography.titleSmall,
//                modifier = Modifier.padding(start = 5.dp, end = 5.dp)
//            )
//            HorizontalDivider(
//                color = MaterialTheme.colorScheme.surface,
//                thickness = 2.dp,
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(end = 170.dp)
//            )
//        }
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            "Đăng nhập với",
//            color = MaterialTheme.colorScheme.surface,
//            style = MaterialTheme.typography.titleSmall
//        )
//        Spacer(modifier = Modifier.height(20.dp))
//        GoogleButton(
//            onClick = {}
//        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 30.dp)
        ) {
            Text(
                "Bạn chưa có tài khoản? ",
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "Đăng ký",
                color = MaterialTheme.colorScheme.inversePrimary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable {
                    navController.navigate("signup")
                }
            )
        }
    }

    if (state.isLoggingIn) {
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

    if (state.isForgotPassword) {
        PopupEdit(
            errorMessage = state.accountEmailError,
            value = state.accountEmail,
            label = "Email của bạn",
            leadingIcon = Icons.Filled.Mail,
            onDismiss = {
                viewModel.handleAction(SigninAction.CancelForgotPassword)
            },
            onConfirm = {
                viewModel.handleAction(SigninAction.SubmitAccountEmail)
            },
            onValueChange = { accountEmail ->
                viewModel.handleAction(SigninAction.EnterAccountEmail(accountEmail))
            },
            isUploading = state.isSendingEmail
        )
    }

    if (state.isConfirming) {
        PopupConfirmation(
            message = "Kiểm tra hộp thư đến của bạn",
            onDismiss = {
                viewModel.handleAction(SigninAction.CloseConfirmation)
            }
        )
    }
}