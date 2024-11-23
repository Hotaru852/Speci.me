package com.example.specime.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.specime.R

@Composable
fun GoogleButton(
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = CircleShape,
        shadowElevation = 4.dp,
        modifier = Modifier
            .size(48.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_google),
            contentDescription = "Google Icon",
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

