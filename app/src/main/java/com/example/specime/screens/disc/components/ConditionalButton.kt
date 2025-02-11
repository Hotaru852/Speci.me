package com.example.specime.screens.disc.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConditionalButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    text: String
) {
    Surface(
        shape = RoundedCornerShape(7.dp),
        shadowElevation = 5.dp,
        modifier = Modifier.width(100.dp).height(50.dp)
    ) {
        Button(
            onClick = { if (isEnabled) onClick() },
            shape = RoundedCornerShape(7.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isEnabled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                contentColor = if (isEnabled) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}