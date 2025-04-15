package com.example.specime.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.specime.components.common.FlexibleButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestOptionPopup(
    groups: Map<String, String>,
    onDismiss: () -> Unit,
    onConfirm: (groupId: String?) -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        var selectedType by remember { mutableStateOf("Cá nhân") }
        var selectedGroup by remember {
            mutableStateOf(if (groups.isNotEmpty()) groups.keys.first() else "Nhóm")
        }
        var expanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CHỌN LOẠI BÀI TEST",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedType == "Cá nhân",
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = { selectedType = "Cá nhân" }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Cá nhân",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    enabled = groups.isNotEmpty(),
                    selected = selectedType == "Nhóm",
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = { selectedType = "Nhóm" }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Nhóm",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            if (selectedType == "Nhóm") {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedGroup,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text(
                                "Nhóm",
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(Color.White)
                            .heightIn(max = 150.dp)
                    ) {
                        groups.forEach { (name, _) ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = name,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                },
                                onClick = {
                                    selectedGroup = name
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                FlexibleButton(
                    text = "Hủy bỏ",
                    width = 100,
                    height = 40,
                    rounded = 7,
                    onClick = onDismiss,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = Color.White,
                    outlined = false,
                    modifier = Modifier.weight(1f)
                )
                FlexibleButton(
                    text = "Đồng ý",
                    width = 100,
                    height = 40,
                    rounded = 7,
                    onClick = {
                        if (selectedType == "Cá nhân") {
                            onConfirm(null)
                        } else {
                            onConfirm(groups[selectedGroup])
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    outlined = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}