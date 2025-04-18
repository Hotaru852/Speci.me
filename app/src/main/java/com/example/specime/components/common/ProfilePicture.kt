package com.example.specime.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter

@Composable
fun ProfilePicture(
    imageUrl: String?,
    imageSize: Int,
    editable: Boolean = false,
    onButtonClick: () -> Unit = {},
    onProfilePictureClick: () -> Unit = {},
    isUploading: Boolean = false,
) {
    var isLoading by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.size(imageSize.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = Color.White,
            modifier = Modifier.fillMaxSize()
                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable(onClick = onProfilePictureClick)
        ) {
            when {
                imageUrl != "" -> {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop ,
                        onState = {
                            if (it is AsyncImagePainter.State.Success) {
                                isLoading = false
                            }
                        }
                    )
                }
                else -> {
                    Image(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Default Profile Picture",
                        modifier = Modifier.fillMaxSize()
                    )
                    isLoading = false
                }
            }

            if (isUploading || isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size((imageSize / 5).dp)
                    )
                }
            }
        }

        if (editable) {
            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 5.dp,
                modifier = Modifier.size(50.dp)
            ) {
                IconButton(
                    onClick = onButtonClick,
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = "Edit Profile Picture",
                    )
                }
            }
        }
    }
}