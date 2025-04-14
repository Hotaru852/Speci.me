package com.example.specime.screens.group.groupresult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.example.specime.screens.group.components.MemberResultsTable
import com.example.specime.screens.group.components.TraitCompabilityPopup
import com.example.specime.screens.home.components.TestResultEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupResultScreen(
    navController: NavController,
    groupId: String,
    viewModel: GroupResultViewModel = hiltViewModel()
) {
    val state = viewModel.state

    viewModel.handleAction(GroupResultAction.Init(groupId = groupId))

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
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Text(
                            text = state.groupName!!,
                            color = Color.White,
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }
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
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(vertical = 10.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    "Kết quả đánh giá của bạn",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
                if (state.currentUserLatestTestResult != null) {
                    TestResultEntry(
                        data = state.currentUserLatestTestResult.result,
                        trait = state.currentUserLatestTestResult.trait,
                        liteMode = true
                    )
                } else {
                    Text(
                        "Không có dữ liệu",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
                Text(
                    "Kết quả đánh giá của các thành viên",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (state.memberTestResults!!.isNotEmpty()) {
                    MemberResultsTable(
                        currentUserTrait = state.currentUserLatestTestResult?.trait,
                        data = state.memberTestResults,
                        onClick = { compatibility ->
                            viewModel.handleAction(GroupResultAction.OpenTraitCompatibility(compatibility))
                        }
                    )
                    Text(
                        "Chạm vào mức độ tương thích bất kì để tìm hiểu thêm",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    Text(
                        "Không có dữ liệu",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    if (state.isShowingCompatibility) {
        TraitCompabilityPopup(
            compatibility = state.selectedCompatibility!!,
            onDismiss = { viewModel.handleAction(GroupResultAction.CloseTraitCompatibility) }
        )
    }
}