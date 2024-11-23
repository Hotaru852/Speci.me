package com.example.specime.screens.account

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.R
import com.example.specime.components.common.PopupConfirmation
import com.example.specime.components.common.ProfilePicture
import com.example.specime.screens.account.components.AccountField
import com.example.specime.components.common.PopupEdit
import java.util.Calendar

@Composable
fun AccountScreen(
    navController: NavController,
    viewmodel: AccountViewModel = hiltViewModel(),
) {
    val state = viewmodel.state

    val context = LocalContext.current

    LaunchedEffect(state.isSignedOut) {
        if (state.isSignedOut) {
            navController.navigate("login")
        }
    }

    LaunchedEffect(state.isChangingPassword) {
        if (state.isChangingPassword) {
            navController.navigate("changePassword")
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        ProfilePicture(
            imageSize = 200,
            iconSize = 50,
            editable = true
        )
        Spacer(modifier = Modifier.weight(1f))
        Surface(
            tonalElevation = 7.dp
        ) {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                AccountField(
                    label = "Tên người dùng",
                    value = state.name,
                    leadingIcon = Icons.Filled.Person,
                    onClick = {
                        viewmodel.handleAction(AccountAction.SubmitEditName)
                    }
                )
                AccountField(
                    label = "Email",
                    value = state.email,
                    leadingIcon = Icons.Filled.Email,
                    onClick = {
                        viewmodel.handleAction(AccountAction.SubmitEditEmail)
                    }
                )
                AccountField(
                    label = "Sinh nhật",
                    value = state.birthday,
                    leadingIcon = Icons.Filled.DateRange,
                    onClick = {
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            context,
                            R.style.CustomDatePickerDialog,
                            { _, selectedYear, selectedMonth, selectedDay ->
                                viewmodel.handleAction(AccountAction.EnterBirthday("${selectedDay}/${selectedMonth + 1}/$selectedYear"))
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                )
                AccountField(
                    label = "Đổi mật khẩu",
                    leadingIcon = Icons.Filled.Lock,
                    onClick = {
                        viewmodel.handleAction(AccountAction.SubmitPasswordChange)
                    }
                )
                AccountField(
                    label = "Đăng xuất",
                    leadingIcon = Icons.AutoMirrored.Filled.ExitToApp,
                    onClick = {
                        viewmodel.handleAction(AccountAction.SubmitSignout)
                    }
                )
            }
        }
    }

    if (state.isEditingName) {
        PopupEdit(
            errorMessage = state.nameError,
            value = state.name,
            label = "Thay đổi Tên người dùng",
            leadingIcon = Icons.Filled.Person,
            onDismiss = {
                viewmodel.handleAction(AccountAction.CancelEditName)
            },
            onConfirm = {
                viewmodel.handleAction(AccountAction.SubmitNameChange)
            },
            onValueChange = { name ->
                viewmodel.handleAction(AccountAction.EnterName(name))
            }
        )
    }

    if (state.isEditingEmail) {
        PopupEdit(
            errorMessage = state.emailError,
            value = state.email,
            label = "Thay đổi Email",
            leadingIcon = Icons.Filled.Mail,
            onDismiss = {
                viewmodel.handleAction(AccountAction.CancelEditEmail)
            },
            onConfirm = {
                viewmodel.handleAction(AccountAction.SubmitEmailChange)
            },
            onValueChange = { email ->
                viewmodel.handleAction(AccountAction.EnterEmail(email))
            }
        )
    }

    if (state.isConfirming) {
        PopupConfirmation(
            message = "Kiểm tra hộp thư đến của bạn",
            onDismiss = {
                viewmodel.handleAction(AccountAction.CloseConfirmation)
            }
        )
    }
}