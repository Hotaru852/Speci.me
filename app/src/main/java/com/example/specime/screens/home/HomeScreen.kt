package com.example.specime.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.components.common.FlexibleButton
import com.example.specime.screens.disc.components.PagerController
import com.example.specime.screens.home.components.GroupResultEntry
import com.example.specime.screens.home.components.Header
import com.example.specime.screens.home.components.TestResultEntry
import com.example.specime.screens.home.components.TabsRow
import com.example.specime.screens.home.components.TestOptionPopup
import com.example.specime.screens.home.components.TraitDetailPopup

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val testResultsPagerState = rememberPagerState(pageCount = { (state.testResults!!.size + 1) / 2 })
    val groupResultsPagerState = rememberPagerState(pageCount = { (state.groupInformations!!.size + 4) / 5})
    var selectedTraitDetail by remember { mutableStateOf<Trait?>(null) }
    val traitDetails = mapOf(
        "D" to Trait.Eagle,
        "I" to Trait.Peacock,
        "S" to Trait.Dove,
        "C" to Trait.Owl
    )

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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Header(
                onClick = { navController.navigate("account") },
                profilePictureUrl = state.profilePictureUrl
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                FlexibleButton(
                    text = "LÀM BÀI TEST",
                    width = 150,
                    height = 40,
                    onClick = { viewModel.handleAction(HomeScreenAction.ChooseTestOption) },
                    rounded = 15,
                    outlined = false,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                FlexibleButton(
                    text = "TẠO NHÓM",
                    width = 150,
                    height = 40,
                    onClick = { navController.navigate("groupCreation") },
                    rounded = 15,
                    outlined = false,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                    textStyle = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "KẾT QUẢ CỦA BẠN",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            )
            TabsRow { selectedIndex ->
                when (selectedIndex) {
                    0 -> {
                        state.testResults?.let { results ->
                            if (results.isEmpty()) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Bạn chưa có kết quả nào",
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else {
                                HorizontalPager(
                                    state = testResultsPagerState,
                                    modifier = Modifier.weight(1f)
                                ) { page ->
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Top
                                    ) {
                                        for (index in 0 until 2) {
                                            val resultIndex = page * 2 + index
                                            if (resultIndex < state.testResults.size) {
                                                val result = state.testResults[resultIndex]
                                                TestResultEntry(
                                                    data = result.result,
                                                    trait = result.trait,
                                                    timestamp = result.timestamp,
                                                    groupName = result.groupName,
                                                    onClick = {
                                                        selectedTraitDetail = traitDetails[result.trait]
                                                        viewModel.handleAction(HomeScreenAction.OpenTraitDetail)
                                                    },
                                                    onButtonClick = {
                                                        navController.navigate("testDetail/${result.detailId}")
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                PagerController(
                                    pagerState = testResultsPagerState,
                                    totalTabs = (state.testResults.size + 1) / 2
                                )
                            }
                        }

                    }
                    1 -> {
                        state.groupInformations?.let { results ->
                            if (results.isEmpty()) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Bạn chưa có nhóm nào",
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else {
                                HorizontalPager(
                                    state = groupResultsPagerState,
                                    modifier = Modifier.weight(1f)
                                ) { page ->
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Top
                                    ) {
                                        for (index in 0 until 5) {
                                            val resultIndex = page * 5 + index
                                            if (resultIndex < state.groupInformations.size) {
                                                val result = state.groupInformations[resultIndex]
                                                GroupResultEntry(
                                                    groupName = result.groupName,
                                                    timestamp = result.timestamp,
                                                    testsDone = result.testsDone,
                                                    groupSize = result.groupSize,
                                                    onClick = {
                                                        navController.navigate("groupResult/${result.groupId}")
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                PagerController(
                                    pagerState = groupResultsPagerState,
                                    totalTabs = (state.groupInformations.size + 4) / 5
                                )
                            }
                        }
                    }
                }
            }
        }

        if (state.isChoosingTestOption) {
            TestOptionPopup(
                groups = state.groupInformations
                    ?.filter { it.groupName != null && it.groupId != null }
                    ?.associate { it.groupName!! to it.groupId!! } ?: emptyMap(),
                onDismiss = { viewModel.handleAction(HomeScreenAction.CancelChooseTestOption) },
                onConfirm = { groupName, groupId ->
                    navController.navigate("test?groupName=${groupName}&groupId=${groupId}")
                }
            )
        }

        if (state.isShowingTraitDetail) {
            TraitDetailPopup(
                trait = selectedTraitDetail!!,
                onDismiss = { viewModel.handleAction(HomeScreenAction.CloseTraitDetail) }
            )
        }
    }
}