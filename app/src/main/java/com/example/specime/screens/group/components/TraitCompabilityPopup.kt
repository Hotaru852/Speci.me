package com.example.specime.screens.group.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.window.DialogProperties
import com.example.specime.screens.group.groupresult.Compatibility

private val traitColors = mapOf(
    "Đại Bàng" to Color(0xFFFF4D4D),
    "Chim Công" to Color(0xFFFFD700),
    "Bồ Câu" to Color(0xFF4CAF50),
    "Chim Cú" to Color(0xFF2196F3)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TraitCompabilityPopup(
    compatibility: Compatibility,
    onDismiss: () -> Unit
) {
    val title = buildAnnotatedString {
        val color1 = traitColors[compatibility.trait1]!!
        val color2 = traitColors[compatibility.trait2]!!

        withStyle(style = SpanStyle(color = color1, fontWeight = FontWeight.Bold)) {
            append(compatibility.trait1)
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(" và ")
        }
        withStyle(style = SpanStyle(color = color2, fontWeight = FontWeight.Bold)) {
            append(compatibility.trait2)
        }
    }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onDismiss() }
                )
            }
            Spacer(Modifier.height(15.dp))
            Text(
                title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(15.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                fun LazyListScope.section(header: String, body: String) {
                    item {
                        Text(
                            header,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            body,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify
                        )
                        Spacer(Modifier.height(10.dp))
                    }
                }

                section("Mô tả", compatibility.description)
                section("Mối quan hệ trong công việc", compatibility.workRelationship)
                section("Thách thức", compatibility.challenges)
                section("Chiến lược thành công", compatibility.successStrategies)
            }
        }
    }
}