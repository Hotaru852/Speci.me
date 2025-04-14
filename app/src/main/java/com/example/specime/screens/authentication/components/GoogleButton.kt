package com.example.specime.screens.authentication.components

import androidx.compose.foundation.border
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
        color = Color.White,
        shape = CircleShape,
        shadowElevation = 5.dp,
        modifier = Modifier
            .size(48.dp)
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
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

