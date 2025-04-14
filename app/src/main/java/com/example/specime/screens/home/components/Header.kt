package com.example.specime.screens.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.specime.components.common.ProfilePicture

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    onClick: () -> Unit,
    profilePictureUrl: String?
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(
                text = "SPECI.ME",
                color = Color.White,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            ProfilePicture(
                imageUrl = profilePictureUrl,
                imageSize = 40,
                onProfilePictureClick = onClick
            )
            Spacer(modifier = Modifier.width(15.dp))
        },
        modifier = Modifier
             .fillMaxWidth()
    )
}