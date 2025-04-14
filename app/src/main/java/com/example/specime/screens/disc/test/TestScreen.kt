package com.example.specime.screens.disc.test

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.components.common.FlexibleButton
import com.example.specime.screens.disc.components.DonePopup
import com.example.specime.screens.disc.components.PagerController
import com.example.specime.screens.disc.components.QuestionEntry
import com.example.specime.screens.disc.components.QuitPopup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    navController: NavController,
    groupName: String?,
    groupId: String?,
    viewModel: TestViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val pagerState = rememberPagerState(pageCount = { (state.questions.size + 2) / 3 })

    BackHandler(enabled = true) {
        viewModel.handleAction(TestAction.Quit)
    }

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
                            text = "DISC",
                            color = Color.White,
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { viewModel.handleAction(TestAction.Quit) }
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
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(innerPadding)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                        for (index in 0 until 3) {
                            val questionIndex = page * 3 + index
                            if (questionIndex < state.questions.size) {
                                QuestionEntry(
                                    questionNumber = questionIndex + 1,
                                    question = state.questions[questionIndex].question,
                                    isChecked = state.questionsAnswer[state.questions[questionIndex]],
                                    onAnswerSelected = { answer ->
                                        viewModel.handleAction(
                                            TestAction.SelectAnswer(
                                                answer,
                                                questionIndex
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                Text(
                    buildAnnotatedString {
                        append("Số câu hỏi đã hoàn thành: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("${state.questionsAnswer.size}/${state.questions.size}")
                        }
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                FlexibleButton(
                    text = "Hoàn Thành",
                    width = 150,
                    height = 40,
                    onClick = { viewModel.handleAction(TestAction.Submit(groupName, groupId)) },
                    rounded = 7,
                    enabled = state.questionsAnswer.size == state.questions.size,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                PagerController(
                    pagerState = pagerState,
                    totalTabs = (state.questions.size + 2) / 3
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        if (state.isDone) {
            DonePopup(
                onDismiss = {},
                onClick = { navController.navigate("home") },
                personality = state.result
            )
        }

        if (state.isUploading) {
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

        if (state.isQuitting) {
            QuitPopup(
                onDismiss = { viewModel.handleAction(TestAction.CancelQuit) },
                onConfirm = {
                    viewModel.handleAction(TestAction.CancelQuit)
                    navController.navigate("home")
                }
            )
        }
    }
}