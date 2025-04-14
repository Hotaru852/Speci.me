package com.example.specime.screens.account

import android.app.DatePickerDialog
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.R
import com.example.specime.components.common.FlexibleButton
import com.example.specime.components.common.PopupDialog
import com.example.specime.components.common.ProfilePicture
import com.example.specime.screens.account.components.PersonalInformationEntry
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state = viewModel.state

    if (state.isLoading) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
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

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    title = { },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.navigate("home") }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBackIosNew,
                                contentDescription = "Trở về",
                                tint = MaterialTheme.colorScheme.primary
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
                    .padding(innerPadding)
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                ProfilePicture(
                    imageUrl = state.profilePictureUrl,
                    imageSize = 200,
                    editable = true,
                    onButtonClick = { launcher.launch("image/*") },
                    isUploading = state.isUploadingProfilePicture
                )
                Spacer(modifier = Modifier.height(50.dp))
                PersonalInformationEntry(
                    label = "Tên của bạn",
                    value = state.currentDisplayName,
                    error = state.nameError,
                    onValueChange = { displayName ->
                        viewModel.handleAction(AccountAction.EnterDisplayName(displayName))
                    },
                    onDone = { viewModel.handleAction(AccountAction.SubmitDisplayNameChange) }
                )
                Spacer(modifier = Modifier.height(30.dp))
                PersonalInformationEntry(
                    label = "Email của bạn",
                    value = state.currentEmail,
                    error = state.emailError,
                    onValueChange = { email ->
                        viewModel.handleAction(AccountAction.EnterEmail(email))
                    },
                    onDone = { viewModel.handleAction(AccountAction.SubmitEmailChange) }
                )
                Spacer(modifier = Modifier.height(30.dp))
                PersonalInformationEntry(
                    label = "Ngày sinh",
                    value = state.birthday,
                    isBirthday = true,
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
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 30.dp)
                ) {
                    FlexibleButton(
                        text = "Đổi mật khẩu",
                        width = 150,
                        height = 40,
                        onClick = { navController.navigate("changePassword") },
                        rounded = 7,
                        outlined = true,
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.primary,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    FlexibleButton(
                        text = "Đăng xuất",
                        width = 150,
                        height = 40,
                        onClick = { navController.navigate("login") },
                        rounded = 7,
                        outlined = true,
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.error,
                        outlineColor = MaterialTheme.colorScheme.error,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            if (state.isConfirming) {
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
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.primary)
//        ) {
//            Spacer(modifier = Modifier.weight(1f))
//            ProfilePicture(
//                imageUrl = state.profilePictureUrl,
//                imageSize = 200,
//                editable = true,
//                onButtonClick = { launcher.launch("image/*") },
//                isUploading = state.isUploadingProfilePicture
//            )
//            Spacer(modifier = Modifier.weight(1f))
//            Surface(
//                tonalElevation = 7.dp
//            ) {
//                Column(
//                    modifier = Modifier
//                        .background(color = MaterialTheme.colorScheme.surface)
//                ) {
//                    AccountField(
//                        label = "Tên người dùng",
//                        value = state.oldDisplayName,
//                        leadingIcon = Icons.Filled.Person,
//                        onClick = {
//                            viewModel.handleAction(AccountAction.SubmitEditDisplayName)
//                        }
//                    )
//                    AccountField(
//                        label = "Email",
//                        value = state.oldEmail,
//                        leadingIcon = Icons.Filled.Email,
//                        onClick = {
//                            viewModel.handleAction(AccountAction.SubmitEditEmail)
//                        },
//                        editable = !state.isGoogleAccount
//                    )
//                    AccountField(
//                        label = "Sinh nhật",
//                        value = state.birthday,
//                        leadingIcon = Icons.Filled.DateRange,
//                        onClick = {
//                            val calendar = Calendar.getInstance()
//                            DatePickerDialog(
//                                context,
//                                R.style.CustomDatePickerDialog,
//                                { _, selectedYear, selectedMonth, selectedDay ->
//                                    viewModel.handleAction(AccountAction.EnterBirthday("${selectedDay}/${selectedMonth + 1}/$selectedYear"))
//                                },
//                                calendar.get(Calendar.YEAR),
//                                calendar.get(Calendar.MONTH),
//                                calendar.get(Calendar.DAY_OF_MONTH)
//                            ).show()
//                        }
//                    )
//                    AccountField(
//                        label = "Đổi mật khẩu",
//                        leadingIcon = Icons.Filled.Lock,
//                        onClick = {
//                            viewModel.handleAction(AccountAction.SubmitPasswordChange)
//                        },
//                        editable = !state.isGoogleAccount
//                    )
//                    AccountField(
//                        label = "Đăng xuất",
//                        leadingIcon = Icons.AutoMirrored.Filled.ExitToApp,
//                        onClick = {
//                            viewModel.handleAction(AccountAction.SubmitSignout)
//                        }
//                    )
//                }
//            }
//        }
//
//        if (state.isEditingName) {
//            PopupDialog(
//                errorMessage = state.nameError,
//                value = state.currentDisplayName,
//                label = "Tên người dùng",
//                leadingIcon = Icons.Filled.Person,
//                onDismiss = {
//                    viewModel.handleAction(AccountAction.CancelEditDisplayName)
//                },
//                onConfirm = {
//                    viewModel.handleAction(AccountAction.SubmitDisplayNameChange)
//                },
//                onValueChange = { name ->
//                    viewModel.handleAction(AccountAction.EnterDisplayName(name))
//                },
//                isUploading = state.isUploading
//            )
//        }
//
//        if (state.isEditingEmail || state.isConfirming) {
//            PopupDialog(
//                errorMessage = state.emailError,
//                confirmationMessage = "Kiểm tra hộp thư đến của bạn",
//                value = state.currentEmail,
//                label = "Email",
//                leadingIcon = Icons.Filled.Mail,
//                onDismiss = {
//                    viewModel.handleAction(AccountAction.CancelEditEmail)
//                },
//                onConfirm = {
//                    viewModel.handleAction(AccountAction.SubmitEmailChange)
//                },
//                onValueChange = { email ->
//                    viewModel.handleAction(AccountAction.EnterEmail(email))
//                },
//                isUploading = state.isUploading,
//                isConfirming = state.isConfirming
//            )
//        }
    }
}