package com.example.specime.screens.disc.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpDialog(
    onDimiss: () -> Unit,
    onQuit: () -> Unit,
    onRetake: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { onDimiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.clickable { onDimiss() }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "Hướng dẫn làm bài:",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                "Thời gian",
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = buildAnnotatedString {
                    append("Có tất cả ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("28 câu hỏi")
                    }
                    append(" trong bài kiểm tra và không giới hạn thời gian. Bài kiểm tra chỉ mất khoảng ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("10 phút")
                    }
                    append(" để hoàn thành")
                },
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Trả lời",
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = buildAnnotatedString {
                    append("Mỗi câu hỏi có ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("4 câu trả lời.")
                    }
                    append(" Hãy chọn câu trả lời mà bạn cảm thấy phù hợp với bản thân nhất. Lưu ý, ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("không có câu trả lời nào là đúng hoặc sai")
                    }
                },
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Tư duy",
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = buildAnnotatedString {
                    append("Hãy trả lời một cách trung thực và chân thật, dựa trên ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("con người thật của bạn")
                    }
                    append(" chứ không phải cách mà bạn muốn những người khác nhìn nhận mình")
                },
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Thời điểm",
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = buildAnnotatedString {
                    append("Hãy làm bài kiểm tra khi bạn đang có tâm trạng ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("bình thường")
                    }
                    append(" và có thể tập trung mà ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("không bị phân tâm")
                    }
                },
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(25.dp))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "Thoát",
                    color = MaterialTheme.colorScheme.primaryContainer,
                    style = MaterialTheme.typography.titleMedium.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable { onQuit() }
                )
                Text(
                    " hoặc ",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Làm lại",
                    color = MaterialTheme.colorScheme.primaryContainer,
                    style = MaterialTheme.typography.titleMedium.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable { onRetake() }
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}