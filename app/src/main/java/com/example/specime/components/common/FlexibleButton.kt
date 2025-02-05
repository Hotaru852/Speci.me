package com.example.specime.components.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun FlexibleButton(
    text: String,
    width: Int,
    height: Int,
    onClick: () -> Unit,
    rounded: Int,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(rounded.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = ButtonDefaults.buttonElevation(5.dp)
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}