package com.example.specime.screens.group.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.specime.screens.group.groupcreation.ValidatedEmail

@Composable
fun ValidatedEmailField(
    validatedEmails: List<ValidatedEmail>,
    visible: Boolean
) {
    val validEmailCount = validatedEmails.count { it.valid }

    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
                .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(7.dp)
                )
                .background(Color.White)
        ) {
            Column {
                Text(
                    text = "Email thành viên:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.primary,
                    thickness = 2.dp,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 15.dp, vertical = 5.dp)
                        .fillMaxWidth()
                ) {
                    itemsIndexed(validatedEmails) { index, email ->
                        Text(
                            text = "${index + 1}. ${email.email}",
                            color = if (email.valid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.primary,
                    thickness = 2.dp,
                )
                Text(
                    text = buildAnnotatedString {
                        append("Số Email hợp lệ: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("$validEmailCount / ${validatedEmails.size}")
                        }
                    },
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                )
            }
        }
    } else {
        Box(modifier = Modifier.height(300.dp))
    }
}