package com.example.specime.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.specime.R
import com.example.specime.components.common.FlexibleButton
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

val traitIcons = mapOf(
    "D" to R.drawable.eagle,
    "I" to R.drawable.peacock,
    "S" to R.drawable.dove,
    "C" to R.drawable.owl
)
val traitLabels = mapOf(
    "D" to "Đại Bàng",
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

@Composable
fun TestResultEntry(
    data: Map<String, Int>,
    trait: String?,
    timestamp: Timestamp? = null,
    onClick: () -> Unit = {},
    onButtonClick: () -> Unit = {},
    groupName: String? = "",
    liteMode: Boolean = false
) {
    val dateString = remember(timestamp) {
        timestamp?.toDate()?.let {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
        } ?: "N/A"
    }
    val traitIcon = traitIcons[trait]!!
    val traitLabel = traitLabels[trait]!!
    val traitColor = traitColors[trait]!!

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .background(Color.White)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(15.dp)
    ) {
        Column {
            if (!liteMode) {
                if (groupName != null) {
                    Text(
                        text = "Bài test nhóm $groupName",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall,
                    )
                } else {
                    Text(
                        text = "Bài test cá nhân",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Text(
                    text = dateString,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Nhóm tính cách",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Image(
                        painter = painterResource(id = traitIcon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .clickable { onClick() }
                    )
                    Text(
                        text = traitLabel,
                        color = traitColor,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (!liteMode) {
                        FlexibleButton(
                            text = "Xem bài làm",
                            width = 100,
                            height = 30,
                            rounded = 15,
                            onClick = onButtonClick,
                            textStyle = MaterialTheme.typography.bodyMedium,
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White,
                            outlined = false
                        )
                    }
                }
                DataChart(result = data)
            }
        }
    }
}