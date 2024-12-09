package com.example.specime.screens.disc

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.specime.screens.auth.components.FlexibleButton
import com.example.specime.screens.disc.components.InfoCard

@Composable
fun DISCScreen(navController: NavController) {
    val text = buildAnnotatedString {
        append("khi làm bài đánh giá ")
        append(
            AnnotatedString(
                text = "D",
                spanStyle = SpanStyle(
                    color = Color(0xFFFF4D4D)
                )
            )
        )
        append(
            AnnotatedString(
                text = "I",
                spanStyle = SpanStyle(
                    color = Color(0xFFFFD700)
                )
            )
        )
        append(
            AnnotatedString(
                text = "S",
                spanStyle = SpanStyle(
                    color = Color(0xFF4CAF50)
                )
            )
        )
        append(
            AnnotatedString(
                text = "C",
                spanStyle = SpanStyle(
                    color = Color(0xFF2196F3)
                )
            )
        )
    }

    BackHandler(enabled = true) {}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            "Những lợi ích bạn sẽ nhận được",
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text,
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        InfoCard(
            title = "Đối với doanh nghiệp",
            bulletPoints = listOf(
                "Tăng tỷ lệ tuyển dụng nhân sự thành công",
                "Xây dựng kế hoạch đào tạo, phát triển năng lực cho từng cá nhân",
                "Phát triển đội nhóm vững mạnh gồm các cá nhân có tính cách phù hợp"
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        InfoCard(
            title = "Đối với cá nhân",
            bulletPoints = listOf(
                "Hiểu rõ điểm mạnh, điểm yếu của bản thân",
                "Nâng cao khả năng giao tiếp và ứng xử",
                "Xây dựng mối quan hệ lành mạnh và tích cực",
                "Tìm thấy ngành nghề phù hợp với khả năng và sở thích"
            )
        )
        Spacer(modifier = Modifier.height(30.dp))
        FlexibleButton(
            text = "Làm bài đánh giá DISC ngay",
            width = 300,
            height = 45,
            onClick = {
                navController.navigate("test")
            },
            rounded = 10
        )
    }

}