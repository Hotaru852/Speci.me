package com.example.specime.screens.disc.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun QuestionEntry(
    questionNumber: Int,
    question: String,
    isChecked: Boolean?,
    onAnswerSelected: (Boolean) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = "Câu $questionNumber: $question",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = isChecked == true,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.primary
                ),
                onClick = { onAnswerSelected(true) }
            )
            Text(
                text = "Đồng ý",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { onAnswerSelected(true) }
                    .padding(start = 10.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = isChecked == false,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.primary
                ),
                onClick = { onAnswerSelected(false) }
            )
            Text(
                text = "Không đồng ý",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { onAnswerSelected(false) }
                    .padding(start = 10.dp)
            )
        }
    }
}
