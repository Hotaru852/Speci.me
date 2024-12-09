package com.example.specime.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter

@Composable
fun ProfilePicture(
    imageUrl: String?,
    imageSize: Int,
    iconSize: Int,
    editable: Boolean = false,
    onClick: () -> Unit,
    isUploading: Boolean,
) {
    var isLoading by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.size(imageSize.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                imageUrl != "" -> {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
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
                }
            }

            if (isUploading || isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }

        if (editable) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 7.dp,
                modifier = Modifier.size(iconSize.dp)
            ) {
                IconButton(
                    onClick = onClick,
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