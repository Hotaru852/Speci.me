package com.example.specime.screens.disc.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LabelImportant
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InfoCard(
    title: String,
    bulletPoints: List<String>
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            title,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, start = 15.dp)
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 5.dp,
            modifier = Modifier.padding(start = 15.dp, end = 325.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        bulletPoints.forEach { bulletPoint ->
            BullletPoint(text = bulletPoint)
        }
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
    }
}

@Composable
fun BullletPoint(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.LabelImportant,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp).padding(top = 4.dp)
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
fun Preview() {
    val bulletPoints = listOf(
        "Bullet point 1",
        "Bullet point 2",
    )
    InfoCard("Test", bulletPoints)
}