package com.example.specime.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.specime.screens.auth.components.FlexibleButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupEdit(
    errorMessage: String?,
    value: String,
    label: String,
    leadingIcon: ImageVector,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    onValueChange: (String) -> Unit,
    isUploading: Boolean
) {
    BasicAlertDialog(
        onDismissRequest = { onDismiss() },
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Box(
            modifier = Modifier.height(250.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
            ) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.titleLarge
                )
                FlexibleTextField(
                    value = value,
                    width = 320,
                    height = 50,
                    errorMessage = errorMessage,
                    onValueChange = onValueChange,
                    leadingIcon = leadingIcon,
                    rounded = 0
                )
                Spacer(modifier = Modifier.height(10.dp))
                FlexibleButton(
                    text = "XÁC NHẬN",
                    width = 320,
                    height = 45,
                    onClick = {
                        if (errorMessage == null) {
                            onConfirm(value)
                        }
                    },
                    rounded = 0
                )
            }

            if (isUploading) {
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
    }
}