package com.example.specime.components.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FlexibleButton(
    text: String,
    width: Int,
    height: Int,
    onClick: () -> Unit,
    rounded: Int,
    outlined: Boolean = true,
    containerColor: Color = Color.White,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    outlineColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .width(width.dp)
            .height(height.dp)
            .then(
                if (outlined) {
                    Modifier.border(
                        width = 1.dp,
                        color = outlineColor,
                        shape = RoundedCornerShape(rounded.dp)
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(rounded.dp),
        color = containerColor,
        shadowElevation = 5.dp
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
            elevation = null,
            enabled = enabled
        ) {
            Text(
                text = text,
                style = textStyle,
                fontWeight = FontWeight.Bold
            )
        }
    }
}