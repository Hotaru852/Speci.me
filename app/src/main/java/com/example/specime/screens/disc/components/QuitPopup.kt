package com.example.specime.screens.disc.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.specime.components.common.FlexibleButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuitPopup(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
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
                text = "KẾT QUẢ BÀI TEST SẼ MẤT !!!",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Bạn vẫn muốn tiếp tục chứ?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                FlexibleButton(
                    text = "Hủy bỏ",
                    width = 100,
                    height = 40,
                    rounded = 7,
                    onClick = onDismiss,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    outlined = false,
                    modifier = Modifier.weight(1f)
                )
                FlexibleButton(
                    text = "Đồng ý",
                    width = 100,
                    height = 40,
                    rounded = 7,
                    onClick = onConfirm,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = Color.White,
                    outlined = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}