package com.example.specime.screens.group.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.specime.screens.group.groupresult.Compatibility
import com.example.specime.screens.group.groupresult.MemberTestResult
import com.example.specime.screens.group.groupresult.TraitCompatibilityMatrix

val traitLabels = mapOf(
    "D" to "Đại Bàng",
    "I" to "Chim Công",
    "S" to "Bồ Câu",
    "C" to "Chim Cú"
)
private val traitColors = mapOf(
    "D" to Color(0xFFFF4D4D),
    "I" to Color(0xFFFFD700),
    "S" to Color(0xFF4CAF50),
    "C" to Color(0xFF2196F3)
)

@Composable
fun MemberResultsTable(
    currentUserTrait: String?,
    data: List<MemberTestResult>,
    onClick: (Compatibility) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            TableCell(
                weight = 1f,
                text = "STT",
                textColor = Color.White,
            )
            TableCell(
                weight = 4f,
                text = "Thông tin cá nhân",
                textColor = Color.White,
            )
            TableCell(
                weight = 2f,
                text = "Nhóm      tính cách",
                textColor = Color.White,
            )
            TableCell(
                weight = 2f,
                text = "Mức độ tương thích",
                textColor = Color.White,
                hasRightBorder = false
            )
        }
        LazyColumn {
            itemsIndexed(data) { index, member ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    TableCell(
                        weight = 1f,
                        text = "${index + 1}",
                        textColor = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Normal,
                        isHeader = false
                    )
                    TableCell(
                        weight = 4f,
                        text = "${member.username}\n${member.email}",
                        textColor = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Normal,
                        isHeader = false
                    )
                    TableCell(
                        weight = 2f,
                        text = traitLabels[member.trait]!!,
                        textColor = traitColors[member.trait]!!,
                        isHeader = false
                    )
                    TableCell(
                        weight = 2f,
                        text = if (currentUserTrait != null) TraitCompatibilityMatrix.getCompatibility(
                            traitLabels[member.trait]!!, traitLabels[currentUserTrait]!!
                        )!!.compatibilityLevel else "",
                        textColor = MaterialTheme.colorScheme.primary,
                        hasRightBorder = false,
                        isHeader = false,
                        isCompatibility = true,
                        onClick = {
                            if (currentUserTrait != null) {
                                onClick(
                                    TraitCompatibilityMatrix.getCompatibility(
                                        traitLabels[member.trait]!!, traitLabels[currentUserTrait]!!
                                    )!!
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    weight: Float,
    text: String,
    textColor: Color,
    fontWeight: FontWeight = FontWeight.Bold,
    hasRightBorder: Boolean = true,
    isHeader: Boolean = true,
    isCompatibility: Boolean = false,
    onClick: () -> Unit = {}
) {
    val borderColor = if (isHeader) MaterialTheme.colorScheme.surface else Color.Black

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(weight)
            .fillMaxWidth()
            .height(45.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                if (hasRightBorder) {
                    drawLine(
                        color = borderColor,
                        start = Offset(size.width - strokeWidth / 2, 0f),
                        end = Offset(size.width - strokeWidth / 2, size.height),
                        strokeWidth = strokeWidth
                    )
                }
                if (!isHeader) {
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, size.height - strokeWidth / 2),
                        end = Offset(size.width, size.height - strokeWidth / 2),
                        strokeWidth = strokeWidth
                    )
                }
            }
            .clickable { if (isCompatibility) onClick() }
    ) {
        if (isCompatibility) {
            val annotated = buildAnnotatedString {
                val parts = text.split(" ")
                parts.forEachIndexed { idx, part ->
                    val (color, annotatedWeight) = when (part) {
                        "Cao" -> Color(0xFF4CAF50) to FontWeight.Bold
                        "Thấp" -> Color(0xFFFF4D4D) to FontWeight.Bold
                        "Trung", "bình" -> Color(0xFFFFD700) to FontWeight.Bold
                        else -> MaterialTheme.colorScheme.primary to FontWeight.Normal
                    }
                    withStyle(SpanStyle(color = color, fontWeight = annotatedWeight)) {
                        append(part)
                    }
                    if (idx < parts.lastIndex) append(" ")
                }
            }

            Text(
                text = annotated,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = fontWeight,
                textAlign = TextAlign.Center
            )
        }
    }
}