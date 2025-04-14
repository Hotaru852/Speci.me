package com.example.specime.screens.disc.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.specime.R
import com.example.specime.components.common.FlexibleButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonePopup(
    onDismiss: () -> Unit,
    onClick: () -> Unit,
    personality: String
) {
    val traitIcons = mapOf(
        "D" to R.drawable.eagle,
        "I" to R.drawable.peacock,
        "S" to R.drawable.dove,
        "C" to R.drawable.owl
    )
    val traitLabels = mapOf(
        "D" to "Đại bàng",
        "I" to "Chim Công",
        "S" to "Bồ Câu",
        "C" to "Chim Cú"
    )
    val traitColors = mapOf(
        "D" to Color(0xFFFF4D4D),
        "I" to Color(0xFFFFD700),
        "S" to Color(0xFF4CAF50),
        "C" to Color(0xFF2196F3)
    )
    val traitIcon = traitIcons[personality]!!
    val traitLabel = traitLabels[personality]!!
    val traitColor = traitColors[personality]!!

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "XIN CHÚC MỪNG",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Bạn đã hoàn thành bài đánh giá",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Bạn thuộc nhóm tính cách",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = traitIcon),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Text(
                text = traitLabel,
                color = traitColor,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            FlexibleButton(
                text = "Trở về màn hình chính",
                width = 200,
                height = 40,
                rounded = 7,
                onClick = onClick,
                textStyle = MaterialTheme.typography.bodyMedium,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                outlined = false
            )
        }
    }
}