package com.example.specime.screens.account

import android.app.DatePickerDialog
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.R
import com.example.specime.components.common.ProfilePicture
import com.example.specime.screens.account.components.AccountField
import com.example.specime.components.common.PopupDialog
import java.util.Calendar

@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state = viewModel.state

    BackHandler(enabled = true) {}

    if (state.isLoading) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.size(50.dp)
            )
        }
    } else {
        val context = LocalContext.current

        var imageUri by remember { mutableStateOf<Uri?>(null) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                imageUri = it
            }
        }

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

        LaunchedEffect(imageUri) {
            imageUri?.let { uri ->
                viewModel.handleAction(AccountAction.UploadProfilePicture(uri))
            }
        }

        BackHandler(enabled = true) {}

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            ProfilePicture(
                imageUrl = state.profilePictureUrl,
                imageSize = 200,
                editable = true,
                onButtonClick = { launcher.launch("image/*") },
                isUploading = state.isUploadingProfilePicture
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
                        value = state.oldDisplayName,
                        leadingIcon = Icons.Filled.Person,
                        onClick = {
                            viewModel.handleAction(AccountAction.SubmitEditDisplayName)
                        }
                    )
                    AccountField(
                        label = "Email",
                        value = state.oldEmail,
                        leadingIcon = Icons.Filled.Email,
                        onClick = {
                            viewModel.handleAction(AccountAction.SubmitEditEmail)
                        },
                        editable = !state.isGoogleAccount
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
                                    viewModel.handleAction(AccountAction.EnterBirthday("${selectedDay}/${selectedMonth + 1}/$selectedYear"))
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
                            viewModel.handleAction(AccountAction.SubmitPasswordChange)
                        },
                        editable = !state.isGoogleAccount
                    )
                    AccountField(
                        label = "Đăng xuất",
                        leadingIcon = Icons.AutoMirrored.Filled.ExitToApp,
                        onClick = {
                            viewModel.handleAction(AccountAction.SubmitSignout)
                        }
                    )
                }
            }
        }

        if (state.isEditingName) {
            PopupDialog(
                errorMessage = state.nameError,
                value = state.currentDisplayName,
                label = "Tên người dùng",
                leadingIcon = Icons.Filled.Person,
                onDismiss = {
                    viewModel.handleAction(AccountAction.CancelEditDisplayName)
                },
                onConfirm = {
                    viewModel.handleAction(AccountAction.SubmitDisplayNameChange)
                },
                onValueChange = { name ->
                    viewModel.handleAction(AccountAction.EnterDisplayName(name))
                },
                isUploading = state.isUploading
            )
        }

        if (state.isEditingEmail || state.isConfirming) {
            PopupDialog(
                errorMessage = state.emailError,
                confirmationMessage = "Kiểm tra hộp thư đến của bạn",
                value = state.currentEmail,
                label = "Email",
                leadingIcon = Icons.Filled.Mail,
                onDismiss = {
                    viewModel.handleAction(AccountAction.CancelEditEmail)
                },
                onConfirm = {
                    viewModel.handleAction(AccountAction.SubmitEmailChange)
                },
                onValueChange = { email ->
                    viewModel.handleAction(AccountAction.EnterEmail(email))
                },
                isUploading = state.isUploading,
                isConfirming = state.isConfirming
            )
        }
    }
}