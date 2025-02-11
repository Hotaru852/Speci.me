package com.example.specime.screens.disc.sub

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.screens.disc.components.AnswerBox
import com.example.specime.screens.disc.components.ConditionalButton
import com.example.specime.screens.disc.components.HelpDialog

@Composable
fun TestScreen(
    navController: NavController,
    viewModel: TestViewModel = hiltViewModel()
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
        LaunchedEffect(state.isQuit) {
            if (state.isQuit) {
                viewModel.handleAction(TestAction.Reset)
                navController.navigate("disc")
            }
        }

        LaunchedEffect(state.isRetake) {
            if (state.isRetake) {
                viewModel.handleAction(TestAction.Reset)
                navController.navigate("test")
            }
        }

        LaunchedEffect(state.isDone) {
            if (state.isDone) {
                val title = "Chúc mừng"
                val message = "Bạn đã hoàn thành bài đánh giá"
                val buttonText = "XEM KẾT QUẢ"
                val route = "result"

                viewModel.handleAction(TestAction.Reset)
                navController.navigate("confirmation/$title/$message/$buttonText/$route")
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 30.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (state.currentQuestion + 1 < 10) "0${state.currentQuestion + 1}" else "${state.currentQuestion + 1}",
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    " / ",
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${state.questions.size}",
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Help,
                    contentDescription = "Help",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.clickable { viewModel.handleAction(TestAction.OpenHelpDialog) }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = state.questions[state.currentQuestion].question,
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(40.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                state.questions[state.currentQuestion].answers.forEach { answer ->
                    AnswerBox(
                        answer = answer,
                        isSelected = state.selectedAnswers[state.currentQuestion] == answer.label,
                        onClick = {
                            viewModel.handleAction(TestAction.SelectAnswer(answer.label))
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ConditionalButton(
                        isEnabled = state.canGoBack,
                        onClick = { viewModel.handleAction(TestAction.Previous) },
                        text = "Quay lại"
                    )
                    ConditionalButton(
                        isEnabled = state.canGoForward,
                        onClick = { viewModel.handleAction(TestAction.Forward) },
                        text = if (state.currentQuestion == state.questions.size - 1) "Hoàn thành" else "Tiếp theo"
                    )
                }
            }
        }

        if (state.showingHelpDialog) {
            HelpDialog(
                onDimiss = { viewModel.handleAction(TestAction.CloseHelpDialog) },
                onQuit = { viewModel.handleAction(TestAction.Quit) },
                onRetake = { viewModel.handleAction(TestAction.Retake) }
            )
        }
    }
}