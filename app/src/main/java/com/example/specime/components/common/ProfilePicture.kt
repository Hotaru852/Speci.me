package com.example.specime.components.common

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ProfilePicture(
    imageSize: Int,
    iconSize: Int,
    editable: Boolean = false
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    LaunchedEffect(imageUri) {
        imageUri?.let { uri ->
            bitmapImage = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.size(imageSize.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxSize()
        ) {
            bitmapImage?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                )
            } ?: Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Image",
            )
        }
        if (editable) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 7.dp,
                modifier = Modifier
                    .size(iconSize.dp)
            ) {
                IconButton(
                    onClick = { launcher.launch("image/*") },
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