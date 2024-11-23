package com.example.specime.components.buttons

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun FlexibleButton(
    text: String,
    width: Int,
    height: Int,
    onClick: () -> Unit,
    rounded: Int
) {
    Surface(
        shape = RoundedCornerShape(rounded.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(rounded.dp),
            )
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(rounded.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}